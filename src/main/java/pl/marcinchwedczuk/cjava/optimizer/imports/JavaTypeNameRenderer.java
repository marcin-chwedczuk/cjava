package pl.marcinchwedczuk.cjava.optimizer.imports;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.*;

import java.util.*;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class JavaTypeNameRenderer {
	private final ImmutableSet<ClassType> explicitImports;
	private final ImmutableSet<ClassType> implicitImports;

	public JavaTypeNameRenderer(
			ImmutableSet<ClassType> explicitImports, ImmutableSet<ClassType> implicitImports) {
		this.implicitImports = requireNonNull(implicitImports);
		this.explicitImports = requireNonNull(explicitImports);
	}

	public List<ImportStatement> getImports() {
		return explicitImports.stream()
				.map(ImportStatement::new)
				.collect(toList());
	}

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
			return type.computeSimpleClassName();
		}

		// full type name
		return type.asSourceCodeString();
	}

	private boolean isImported(ClassType type) {
		ClassType rawType = type.toRawType();

		return implicitImports.contains(rawType)
			|| explicitImports.contains(rawType);
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
