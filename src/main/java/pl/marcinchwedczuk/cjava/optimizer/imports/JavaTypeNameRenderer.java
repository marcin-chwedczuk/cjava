package pl.marcinchwedczuk.cjava.optimizer.imports;

import pl.marcinchwedczuk.cjava.decompiler.JvmConstants;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.PackageName;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static pl.marcinchwedczuk.cjava.decompiler.JvmConstants.JAVA_LANG_PACKAGE;

public class JavaTypeNameRenderer {
	private final PackageName currentPackage;
	private final Set<ClassType> importedTypes;

	public JavaTypeNameRenderer(PackageName currentPackage, JavaTypeHistogram histogram) {
		this.currentPackage = currentPackage;
		this.importedTypes = selectTypesToImport(currentPackage, histogram);
	}

	private static Set<ClassType> selectTypesToImport(
			PackageName currentPackage, JavaTypeHistogram histogram) {

		Set<String> alreadyImported = new HashSet<>();
		Set<ClassType> typesToImport = new HashSet<>();

		for (ClassType type : histogram.getTypesSortedByFrequency()) {
			ClassType rawType = type.toRawType();
			String simpleName = type.computeSimpleClassName();

			if (alreadyImported.contains(simpleName)) {
				// Simple name is already imported
				// (maybe it is used to import other type).
				continue;
			}

			if (rawType.isPartOfPackage(currentPackage)) {
				// Types in current package are implicitly imported

				alreadyImported.add(simpleName);
				continue;
			}

			if (rawType.isPartOfPackage(JAVA_LANG_PACKAGE)) {
				// Types from java.lang are implicitly imported

				alreadyImported.add(simpleName);
				continue;
			}

			// Add import
			typesToImport.add(rawType);
			alreadyImported.add(simpleName);
		}

		return typesToImport;
	}

	public List<ImportStatement> getImports() {
		return importedTypes.stream()
				.map(ImportStatement::new)
				.collect(toList());
	}
}
