package pl.marcinchwedczuk.cjava.optimizer.imports;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ArrayType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.PackageName;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.SimpleClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs.TypeArgument;

import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.marcinchwedczuk.cjava.decompiler.fixture.AstBuilder.*;
import static pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs.BoundType.EXTENDS;

public class ImportOptimizingJavaTypeNameRendererTests {

	private final PackageName MC_TEST_PACKAGE = PackageName.fromString("mc.test");
	private final ClassType JAVA_UTIL_REGEX_PATTERN = ClassType.of(Pattern.class);

	@Test
	public void notImportedTypeMustUseFullyQualifiedName() throws Exception {
		ImportOptimizingJavaTypeNameRenderer renderer = createRenderer(
				explicitImports(),
				implicitImports());

		String patternTypeName =
				renderer.renderTypeName(JAVA_UTIL_REGEX_PATTERN);

		assertThat(patternTypeName)
				.isEqualTo("java.util.regex.Pattern");
	}

	@Test
	public void explicitlyImportedTypesUseOnlySimpleName() throws Exception {
		ImportOptimizingJavaTypeNameRenderer renderer = createRenderer(
				explicitImports(JAVA_UTIL_REGEX_PATTERN),
				implicitImports());

		String patternTypeName =
				renderer.renderTypeName(JAVA_UTIL_REGEX_PATTERN);

		assertThat(patternTypeName)
				.isEqualTo("Pattern");
	}

	@Test
	public void implicitlyImportedTypesUseOnlySimpleName() throws Exception {
		ImportOptimizingJavaTypeNameRenderer renderer = createRenderer(
				explicitImports(),
				implicitImports(JAVA_UTIL_REGEX_PATTERN));

		String patternTypeName =
				renderer.renderTypeName(JAVA_UTIL_REGEX_PATTERN);

		assertThat(patternTypeName)
				.isEqualTo("Pattern");
	}

	@Test
	public void properlyRendersPrimitiveTypes() throws Exception {
		ImportOptimizingJavaTypeNameRenderer renderer = createRenderer(
				explicitImports(), implicitImports());

		String intTypeName = renderer.renderTypeName(integer());

		assertThat(intTypeName)
				.isEqualTo("int");
	}

	@Test
	public void properlyRendersArrayName() throws Exception {
		ImportOptimizingJavaTypeNameRenderer renderer = createRenderer(
				explicitImports(), implicitImports(string()));

		ArrayType arrayOfPatterns = ArrayType.create(2, JAVA_UTIL_REGEX_PATTERN);
		String renderedName = renderer.renderTypeName(arrayOfPatterns);
		assertThat(renderedName)
				.isEqualTo("java.util.regex.Pattern[][]");

		ArrayType arrayOfStrings = ArrayType.create(2, string());
		renderedName = renderer.renderTypeName(arrayOfStrings);
		assertThat(renderedName)
				.isEqualTo("String[][]");
	}

	@Test
	public void properlyRendersTypeArguments() throws Exception {
		ImportOptimizingJavaTypeNameRenderer renderer = createRenderer(
				explicitImports(), implicitImports());

		// mc.test.List<java.util.regex.Pattern>
		ClassType type1 =
			ClassType.create(MC_TEST_PACKAGE, SimpleClassType.forGenericClass("List",
						TypeArgument.forConcreateType(JAVA_UTIL_REGEX_PATTERN)));

		assertThat(renderer.renderTypeName(type1))
				.isEqualTo("mc.test.List<java.util.regex.Pattern>");

		// mc.test.List<?>
		ClassType type2 =
				ClassType.create(MC_TEST_PACKAGE, SimpleClassType.forGenericClass("List",
					TypeArgument.forWildcard()));

		assertThat(renderer.renderTypeName(type2))
				.isEqualTo("mc.test.List<?>");

		// mc.test.List<? extends java.util.regex.Pattern>
		ClassType type3 =
				ClassType.create(MC_TEST_PACKAGE, SimpleClassType.forGenericClass("List",
						TypeArgument.forBoundedWildcard(EXTENDS, JAVA_UTIL_REGEX_PATTERN)));

		assertThat(renderer.renderTypeName(type3))
				.isEqualTo("mc.test.List<? extends java.util.regex.Pattern>");
	}

	@Test
	public void importStatementsAreCreatedFromExplicitImports() throws Exception {
		ImportOptimizingJavaTypeNameRenderer renderer = createRenderer(
				explicitImports(rawArrayList()),
				implicitImports(object(), string()));

		List<ImportStatement> imports = renderer.getImports();

		assertThat(imports).hasSize(1);
		assertThat(imports.get(0).getTypeToImport())
				.isEqualTo(rawArrayList());
	}

	@Test
	public void bugCorrectlyRendersImportedGenericType() throws Exception {
		ImportOptimizingJavaTypeNameRenderer renderer = createRenderer(
				explicitImports(rawArrayList()),
				implicitImports(string()));

		String typeName = renderer.renderTypeName(arrayListOfString());

		assertThat(typeName).isEqualTo("ArrayList<String>");
	}

	private static ImportOptimizingJavaTypeNameRenderer createRenderer(
			ImmutableSet<ClassType> explicitImports,
			ImmutableSet<ClassType> implicitImports) {
		return new ImportOptimizingJavaTypeNameRenderer(explicitImports, implicitImports);
	}

	private static ImmutableSet<ClassType> explicitImports(ClassType... types) {
		return ImmutableSet.copyOf(types);
	}

	private static ImmutableSet<ClassType> implicitImports(ClassType... types) {
		return ImmutableSet.copyOf(types);
	}
}