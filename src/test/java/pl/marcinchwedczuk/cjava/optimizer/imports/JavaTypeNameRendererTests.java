package pl.marcinchwedczuk.cjava.optimizer.imports;

import org.junit.Before;
import org.junit.Test;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.*;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs.BoundType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs.TypeArgument;

import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static pl.marcinchwedczuk.cjava.decompiler.fixture.AstBuilder.*;
import static pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs.BoundType.EXTENDS;
import static pl.marcinchwedczuk.cjava.util.ListUtils.firstElement;

public class JavaTypeNameRendererTests {
	private static final ClassType OTHER_ARRAYLIST =
			ClassType.of(JavaTypeNameRendererTests.ArrayList.class);

	private PackageName TEST_PACKAGE;
	private PackageName OTHER_PACKAGE;

	private ClassType TESTCLASS_DECLARATION;
	private ClassType JAVA_UTIL_REGEX_PATTERN;

	@Before
	public void setUp() throws Exception {
		TEST_PACKAGE = PackageName.fromString("mc.test");
		OTHER_PACKAGE = PackageName.fromString("mc.other");

		TESTCLASS_DECLARATION =
				ClassType.fromPackageAndClassName("mc.test", "TestClass");

		JAVA_UTIL_REGEX_PATTERN = ClassType.of(Pattern.class);
	}

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

		JavaTypeNameRenderer renderer = createRenderer(histogram);

		assertThat(renderer.getImports())
				.hasSize(1);

		// Then only java.util.ArrayList is imported
		assertThat(renderer.getImports().get(0).getTypeToImport())
				.isEqualTo(rawArrayList());
	}

	@Test
	public void doesNotImportATypeThatHasTheSameNameAsATopLevelTypeDeclaration() throws Exception {
		ClassType anotherTestClass = ClassType.fromPackageAndClassName("some.other.package0",
				TESTCLASS_DECLARATION.computeSimpleClassName());

		JavaTypeHistogram histogram =
				JavaTypeHistogram.fromUsages(anotherTestClass);

		JavaTypeNameRenderer renderer = createRenderer(histogram);

		assertThat(renderer.getImports())
				.isEmpty();
	}

	@Test
	public void doesNotImportTypesFromTheCompilationUnitPackage() throws Exception {
		ClassType typeA =
				ClassType.create(TEST_PACKAGE, SimpleClassType.fromClassName("TypeA"));

		ClassType typeB =
				ClassType.create(TEST_PACKAGE, SimpleClassType.fromClassName("TypeB"));

		JavaTypeHistogram histogram = JavaTypeHistogram.fromUsages(
				typeA, typeB, typeA);

		JavaTypeNameRenderer renderer = createRenderer(histogram);

		assertThat(renderer.getImports())
				.isEmpty();
	}

	@Test
	public void doesNotImportTypesFromJavaLangPackage() throws Exception {
		JavaTypeHistogram histogram = JavaTypeHistogram.fromUsages(
				string(), object(), integerWrapper());

		JavaTypeNameRenderer renderer = createRenderer(histogram);

		assertThat(renderer.getImports())
				.isEmpty();
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

		JavaTypeNameRenderer renderer = createRenderer(histogram);

		List<JavaType> importedTypes = renderer.getImports().stream()
				.map(ImportStatement::getTypeToImport)
				.collect(toList());

		assertThat(importedTypes)
				.hasSize(3)
				.contains(rawArrayList(), mapInterface(), listInterface());
	}

	@Test
	public void notImportedTypeMustUseFullyQualifiedName() throws Exception {
		JavaTypeNameRenderer renderer = createRenderer();

		String patternTypeName =
				renderer.renderTypeName(ClassType.of(Pattern.class));

		assertThat(patternTypeName)
				.isEqualTo("java.util.regex.Pattern");
	}

	@Test
	public void properlyRendersPrimitiveTypes() throws Exception {
		JavaTypeNameRenderer renderer = createRenderer();

		String intTypeName = renderer.renderTypeName(integer());

		assertThat(intTypeName)
				.isEqualTo("int");
	}

	@Test
	public void properlyRendersArrayName() throws Exception {
		JavaTypeNameRenderer renderer = createRenderer();

		ArrayType arrayOfPatterns = ArrayType.create(2, JAVA_UTIL_REGEX_PATTERN);

		String renderedName = renderer.renderTypeName(arrayOfPatterns);

		assertThat(renderedName)
				.isEqualTo("java.util.regex.Pattern[][]");
	}

	@Test
	public void properlyRendersTypeArguments() throws Exception {
		JavaTypeNameRenderer renderer = createRenderer();

		// mc.test.List<java.util.regex.Pattern>
		ClassType type1 =
			ClassType.create(OTHER_PACKAGE, SimpleClassType.forGenericClass("List",
						TypeArgument.forConcreateType(JAVA_UTIL_REGEX_PATTERN)));

		assertThat(renderer.renderTypeName(type1))
				.isEqualTo("mc.other.List<java.util.regex.Pattern>");

		// mc.test.List<?>
		ClassType type2 =
				ClassType.create(OTHER_PACKAGE, SimpleClassType.forGenericClass("List",
					TypeArgument.forWildcard()));

		assertThat(renderer.renderTypeName(type2))
				.isEqualTo("mc.other.List<?>");

		// mc.test.List<? extends java.util.regex.Pattern>
		ClassType type3 =
				ClassType.create(OTHER_PACKAGE, SimpleClassType.forGenericClass("List",
						TypeArgument.forBoundedWildcard(EXTENDS, JAVA_UTIL_REGEX_PATTERN)));

		assertThat(renderer.renderTypeName(type3))
				.isEqualTo("mc.other.List<? extends java.util.regex.Pattern>");

	}

	@Test
	public void typesFromJavaLangAreImportedByDefault() throws Exception {
		JavaTypeNameRenderer renderer = createRenderer();

		assertThat(renderer.renderTypeName(object()))
				.isEqualTo("Object");

		assertThat(renderer.renderTypeName(string()))
				.isEqualTo("String");
	}

	@Test
	public void typesFromJavaLangCannotBeShadowed() throws Exception {
		ClassType OTHER_OBJECT =
				ClassType.fromPackageAndClassName("mc.other", "Object");

		JavaTypeHistogram histogram =
				JavaTypeHistogram.fromUsages(OTHER_OBJECT);

		JavaTypeNameRenderer renderer = createRenderer(histogram);

		assertThat(renderer.renderTypeName(object()))
				.as("java.lang.Object type")
				.isEqualTo("Object");

		assertThat(renderer.renderTypeName(OTHER_OBJECT))
				.as("mc.test.Object type")
				.isEqualTo("mc.test.Object");
	}

	@Test
	public void typesFromCurrentPackageAreImportedByDefault() throws Exception {
		JavaTypeNameRenderer renderer = createRenderer();

		ClassType type = ClassType.fromPackageAndClassName(
				TEST_PACKAGE.asJavaSouceCode(), "SomeClass");

		assertThat(renderer.renderTypeName(type))
				.isEqualTo("SomeClass");
	}

	@Test
	public void typeInCurrentPackageCanShadowTypeFromJavaLang() throws Exception {
		ClassType otherString =
				ClassType.fromPackageAndClassName("mc.test", "String");

		JavaTypeHistogram histogram = JavaTypeHistogram.fromUsages(otherString);

		JavaTypeNameRenderer renderer = createRenderer(histogram);

		assertThat(renderer.renderTypeName(otherString))
				.isEqualTo("String");
	}

	// TODO: Rendering tests

	private JavaTypeNameRenderer createRenderer() {
		return createRenderer(new JavaTypeHistogram());
	}

	private JavaTypeNameRenderer createRenderer(JavaTypeHistogram histogram) {
		return new JavaTypeNameRenderer(
				TEST_PACKAGE, singleton(TESTCLASS_DECLARATION), histogram);
	}

	public static class ArrayList { }
}