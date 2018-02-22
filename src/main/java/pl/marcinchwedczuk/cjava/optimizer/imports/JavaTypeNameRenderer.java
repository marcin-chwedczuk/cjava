package pl.marcinchwedczuk.cjava.optimizer.imports;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import pl.marcinchwedczuk.cjava.decompiler.JvmConstants;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static pl.marcinchwedczuk.cjava.decompiler.JvmConstants.JAVA_LANG_PACKAGE;
import static pl.marcinchwedczuk.cjava.util.ListUtils.readOnlyCopy;

public class JavaTypeNameRenderer {
	private final PackageName currentPackage;

	private final ImmutableSet<ClassType> topLevelTypeDeclarations;
	private final ImmutableSet<ClassType> importedTypes;
	private final ImmutableSet<String> allocatedNames;

	public JavaTypeNameRenderer(
			PackageName currentPackage,
			Set<ClassType> topLevelTypeDeclarations,
			JavaTypeHistogram histogram)
	{
		this.currentPackage = currentPackage;
		this.topLevelTypeDeclarations = ImmutableSet.copyOf(topLevelTypeDeclarations);

		new ImportAlgorithm(
								currentPackage, new HashSet<>(),
								topLevelTypeDeclarations, histogram)
					.selectTypesToImport();

		this.importedTypes = ImmutableSet.of();

		this.allocatedNames = Stream.concat(
					this.topLevelTypeDeclarations.stream(),
					this.importedTypes.stream())
				.map(ClassType::computeSimpleClassName)
				.collect(toImmutableSet());
	}


	public List<ImportStatement> getImports() {
		return importedTypes.stream()
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

		if (type.isPartOfPackage(JAVA_LANG_PACKAGE) && !isShadowedByImport(type)) {
			return type.computeSimpleClassName();
		}

		if (type.isPartOfPackage(currentPackage) && !isShadowedByImport(type)) {
			return type.computeSimpleClassName();
		}

		// full type name
		return type.asSourceCodeString();
	}

	private boolean isImported(ClassType type) {
		return importedTypes.contains(type.toRawType());
	}

	private boolean isShadowedByImport(ClassType type) {
		return allocatedNames.contains(type.computeSimpleClassName());
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
