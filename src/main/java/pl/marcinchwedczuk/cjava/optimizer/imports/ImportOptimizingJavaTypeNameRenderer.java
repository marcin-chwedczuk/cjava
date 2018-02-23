package pl.marcinchwedczuk.cjava.optimizer.imports;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.*;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs.BoundedWildcardTypeArgument;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs.ConcreteTypeTypeArgument;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs.TypeArgument;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs.WildcardTypeArgument;

import java.util.*;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class ImportOptimizingJavaTypeNameRenderer implements JavaTypeNameRenderer {
	private final ImmutableSet<ClassType> explicitImports;
	private final ImmutableSet<ClassType> implicitImports;

	public ImportOptimizingJavaTypeNameRenderer(
			ImmutableSet<ClassType> explicitImports, ImmutableSet<ClassType> implicitImports) {
		this.implicitImports = requireNonNull(implicitImports);
		this.explicitImports = requireNonNull(explicitImports);
	}

	@Override
	public ImmutableList<ImportStatement> getImports() {
		return explicitImports.stream()
				.map(ImportStatement::new)
				.collect(toImmutableList());
	}

	@Override
	public String renderTypeName(JavaType type) {
		if (type instanceof PrimitiveType) {
			return renderPrimitiveType((PrimitiveType)type);
		}

		if (type instanceof ClassType) {
			return renderClassType((ClassType)type);
		}

		if (type instanceof ArrayType) {
			return renderArrayType((ArrayType)type);
		}

		if (type instanceof TypeVariable) {
			return renderTypeVariable((TypeVariable)type);
		}

		throw new AssertionError(
				"JavaType " + type.getClass().getSimpleName() + " is not supported!");

	}

	private String renderPrimitiveType(PrimitiveType type) {
		return type.name().toLowerCase();
	}

	private String renderClassType(ClassType type) {
		if (isImported(type)) {
			SimpleClassType simpleClassType = type.computeSimpleClassType();
			return renderSimpleClassType(simpleClassType);
		}

		// full type name
		return renderFullyQualifiedClassType(type);
	}

	private boolean isImported(ClassType type) {
		ClassType rawType = type.toRawType();

		return implicitImports.contains(rawType)
			|| explicitImports.contains(rawType);
	}

	public String renderFullyQualifiedClassType(ClassType type) {
		String javaPackage = type.getPackageName().asJavaSouceCode();
		if (!javaPackage.isEmpty()) {
			javaPackage += ".";
		}

		String javaClasses = type.getClasses().stream()
				.map(this::renderSimpleClassType)
				.collect(joining("."));

		return javaPackage.concat(javaClasses);
	}

	public String renderSimpleClassType(SimpleClassType type) {
		StringBuilder javaType = new StringBuilder();

		javaType.append(type.getClassName());

		if (!type.getTypeArguments().isEmpty()) {
			javaType.append("<");

			javaType.append(
					type.getTypeArguments().stream()
							.map(this::renderTypeArgument)
							.collect(joining(", ")));

			javaType.append(">");
		}

		return javaType.toString();
	}

	private String renderTypeArgument(TypeArgument typeArg) {
		if (typeArg instanceof WildcardTypeArgument) {
			return "?";
		}

		if (typeArg instanceof ConcreteTypeTypeArgument) {
			ConcreteTypeTypeArgument concreteType =
					(ConcreteTypeTypeArgument) typeArg;

			return renderTypeName(concreteType.getType());
		}

		BoundedWildcardTypeArgument boundedWildcard =
				(BoundedWildcardTypeArgument) typeArg;

		StringBuilder javaString = new StringBuilder();

		javaString.append("? ");

		switch (boundedWildcard.getBoundType()) {
			case EXTENDS:
				javaString.append("extends ");
				break;
			case SUPER:
				javaString.append("super ");
				break;
		}

		javaString.append(renderTypeName(boundedWildcard.getType()));

		return javaString.toString();

	}

	private String renderTypeVariable(TypeVariable typeVariable) {
		return typeVariable.asSourceCodeString();
	}

	private String renderArrayType(ArrayType arrayType) {
		String suffix = Strings.repeat("[]", arrayType.getDimensions());
		String elementType = renderTypeName(arrayType.getElementType());

		return elementType.concat(suffix);
	}

}
