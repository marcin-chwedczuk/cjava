package pl.marcinchwedczuk.cjava.optimizer.imports;

import com.google.common.collect.ImmutableSet;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.PackageName;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static pl.marcinchwedczuk.cjava.decompiler.JvmConstants.JAVA_LANG_PACKAGE;

public class ImportAlgorithm {
	private final PackageName currentPackage;
	private final Set<ClassType> otherTypesInCurrentPackage;
	private final Set<ClassType> topLevelTypeDeclarations;
	private final JavaTypeHistogram histogram;

	private final Set<ClassType> currentPackageImports = new HashSet<>();
	private final Set<ClassType> explicitImports = new HashSet<>();
	private final Set<ClassType> implicitImports = new HashSet<>();

	public ImportAlgorithm(PackageName currentPackage,
						   Set<ClassType> otherTypesInCurrentPackage,
						   Set<ClassType> topLevelTypeDeclarations,
						   JavaTypeHistogram histogram) {
		this.currentPackage = currentPackage;
		this.otherTypesInCurrentPackage = otherTypesInCurrentPackage;
		this.topLevelTypeDeclarations = topLevelTypeDeclarations;
		this.histogram = histogram;
	}

	public void selectTypesToImport() {
		initializeCurrentPackageTypes();

		importTypesFromJavaLang();
		importTypesFromCurrentPackage();
		importNotImplicitlyImportedTypes();
	}

	private void importNotImplicitlyImportedTypes() {
		List<ClassType> notImplicitlyImportedTypes = histogram.getTypesSortedByFrequency().stream()
				.filter(t ->
						!t.isPartOfPackage(JAVA_LANG_PACKAGE) &&
						!t.isPartOfPackage(currentPackage))
				.collect(toList());

		for (ClassType type : notImplicitlyImportedTypes) {
			if (conflictsWith(topLevelTypeDeclarations, type)) continue;
			if (conflictsWith(explicitImports, type)) continue;
			if (conflictsWith(implicitImports, type)) continue;

			explicitImports.add(type);
		}
	}

	private void importTypesFromCurrentPackage() {
		List<ClassType> currentPackageTypes = histogram.getTypesSortedByFrequency().stream()
				.filter(t -> t.isPartOfPackage(currentPackage))
				.collect(toList());

		for (ClassType type : currentPackageTypes) {
			if (conflictsWith(explicitImports, type)) continue;
			if (conflictsWith(implicitImports, type)) continue;

			implicitImports.add(type);
		}
	}

	private void importTypesFromJavaLang() {
		List<ClassType> javaLangTypes = histogram.getTypesSortedByFrequency().stream()
				.filter(t -> t.isPartOfPackage(JAVA_LANG_PACKAGE))
				.collect(toList());

		for (ClassType type : javaLangTypes) {
			if (conflictsWith(topLevelTypeDeclarations, type)) continue;

			if (conflictsWith(currentPackageImports, type)) {
				explicitImports.add(type);
			} else {
				implicitImports.add(type);
			}
		}
	}

	private void initializeCurrentPackageTypes() {
		topLevelTypeDeclarations.stream()
				.map(ClassType::toRawType)
				.forEach(currentPackageImports::add);

		otherTypesInCurrentPackage.stream()
				.map(ClassType::toRawType)
				.forEach(currentPackageImports::add);
	}

	private boolean conflictsWith(Set<ClassType> types, ClassType type) {
		if (types.contains(type)) {
			return false;
		}

		ClassType conflictingType = findConflictingType(types, type);
		return (conflictingType != null);
	}

	private ClassType findConflictingType(Set<ClassType> types, ClassType type) {
		String typeSimpleName = type.computeSimpleClassName();

		return types.stream()
				.filter(t -> t.hasSimpleClassName(typeSimpleName))
				.findAny()
				.orElse(null);

	}

	public ImmutableSet<ClassType> findExplicitImport() {
		return ImmutableSet.copyOf(explicitImports);
	}

	public ImmutableSet<ClassType> findImplicitImports() {
		return ImmutableSet.copyOf(implicitImports);
	}
}
