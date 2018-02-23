package pl.marcinchwedczuk.cjava.optimizer.imports;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.PackageName;

import java.util.regex.Pattern;

import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.marcinchwedczuk.cjava.decompiler.fixture.AstBuilder.*;

public class ImportAlgorithmTests {
	/*
	 * TEST SCENARIO:
	 * - mc.test package contains types
	 *   - TestClassA (currently decompiled)
	 *   - TestClassB
	 *   - String (this shadows java.lang.String type)
	 * - mc.other
	 *   - FooClass
	 *   - ArrayList (can shadow java.util.ArrayList)
	 */

	private static final PackageName TEST_PACKAGE = PackageName.fromString("mc.test");

	private static final ClassType TEST_TESTCLASS_A =
			ClassType.fromPackageAndClassName(TEST_PACKAGE.asJavaSouceCode(), "TestClassA");

	private static final ClassType TEST_TESTCLASS_B =
			ClassType.fromPackageAndClassName(TEST_PACKAGE.asJavaSouceCode(), "TestClassB");

	private static final ClassType TEST_STRING =
			ClassType.fromPackageAndClassName(TEST_PACKAGE.asJavaSouceCode(), "String");

	private static final PackageName OTHER_PACKAGE = PackageName.fromString("mc.other");

	private static final ClassType OTHER_FOOCLASS =
			ClassType.fromPackageAndClassName(OTHER_PACKAGE.asJavaSouceCode(), "FooClass");

	private static final ClassType OTHER_ARRAYLIST =
			ClassType.fromPackageAndClassName(OTHER_PACKAGE.asJavaSouceCode(), "ArrayList");


	private static final ClassType JAVA_UTIL_REGEX_PATTERN =
			ClassType.of(Pattern.class);

	@Test
	public void alwaysImportsTheTypeThatIsTheMostUsedInTheCompilationUnit() throws Exception {
		// Given two types that have the same name ArrayList:
		JavaTypeHistogram histogram = JavaTypeHistogram.fromUsages(
				arrayListOfString(),
				OTHER_ARRAYLIST,
				arrayListOfObjectArrays(),
				arrayListOfString(),
				OTHER_ARRAYLIST,
				arrayListOfString(),
				OTHER_ARRAYLIST);

		ImportAlgorithm algorithm = createAndRunAlgorithm(histogram);

		// Selects the most used:
		assertThat(algorithm.findExplicitImport())
				.containsExactlyInAnyOrder(rawArrayList(), string());

		assertThat(algorithm.findImplicitImports())
				.doesNotContain(OTHER_ARRAYLIST, rawArrayList());
	}

	@Test
	public void doesNotImportATypeThatHasTheSameNameAsATopLevelTypeDeclaration() throws Exception {
		ClassType anotherTestClass = ClassType.fromPackageAndClassName(
				"some.other.package",
				TEST_TESTCLASS_A.computeSimpleClassName());

		JavaTypeHistogram histogram =
				JavaTypeHistogram.fromUsages(anotherTestClass);

		ImportAlgorithm algorithm = createAndRunAlgorithm(histogram);

		assertThat(algorithm.findExplicitImport())
				.isEmpty();

		assertThat(algorithm.findImplicitImports())
				.isEmpty();
	}

	@Test
	public void doesNotExplicitlyImportTypesFromTheCompilationUnitPackage() throws Exception {
		JavaTypeHistogram histogram =
				JavaTypeHistogram.fromUsages(TEST_TESTCLASS_B);

		ImportAlgorithm algorithm = createAndRunAlgorithm(histogram);

		assertThat(algorithm.findExplicitImport())
				.isEmpty();

		assertThat(algorithm.findImplicitImports())
				.containsExactly(TEST_TESTCLASS_B);
	}

	@Test
	public void doesNotExplicitlyImportTypesFromJavaLangPackage() throws Exception {
		JavaTypeHistogram histogram = JavaTypeHistogram.fromUsages(
				object(), integerWrapper());

		ImportAlgorithm algorithm = createAndRunAlgorithm(histogram);

		assertThat(algorithm.findExplicitImport())
				.isEmpty();

		assertThat(algorithm.findImplicitImports())
				.containsExactlyInAnyOrder(object(), integerWrapper());
	}

	@Test
	public void canProduceMultipleImports() throws Exception {
		JavaTypeHistogram histogram = JavaTypeHistogram.fromUsages(
				arrayListOfString(),
				arrayListOfObjectArrays(),
				mapInterface(),
				listInterface(),
				arrayListOfString(),
				mapInterface());

		ImportAlgorithm algorithm = createAndRunAlgorithm(histogram);

		// String is shadowed by class in mc.test package - so it is explicitly imported
		assertThat(algorithm.findExplicitImport())
				.containsExactlyInAnyOrder(rawArrayList(), mapInterface(), listInterface(), string());

		assertThat(algorithm.findImplicitImports())
				.containsExactlyInAnyOrder(object());
	}

	@Test
	public void doesNotImportJavaLangTypeIfItCausesACollisionWithTopLevelTypeName() throws Exception {
		ClassType topLevelClass = ClassType.fromPackageAndClassName("test", "Object");

		JavaTypeHistogram histogram = JavaTypeHistogram.fromUsages(
				object(), string());

		ImportAlgorithm algorithm = new ImportAlgorithm(
				PackageName.fromString("test"),
				ImmutableSet.of(),
				singleton(topLevelClass),
				histogram);

		algorithm.selectTypesToImport();

		assertThat(algorithm.findExplicitImport())
				.isEmpty();

		assertThat(algorithm.findImplicitImports())
				.containsExactlyInAnyOrder(string());
	}

	private ImportAlgorithm createAndRunAlgorithm(JavaTypeHistogram histogram) {
		ImportAlgorithm algorithm = new ImportAlgorithm(
				TEST_PACKAGE,
				ImmutableSet.of(TEST_STRING, TEST_TESTCLASS_B),
				singleton(TEST_TESTCLASS_A),
				histogram);

		algorithm.selectTypesToImport();

		return algorithm;
	}
}