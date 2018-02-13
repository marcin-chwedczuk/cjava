package pl.marcinchwedczuk.cjava.decompiler;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationPropertyAssignmentAst;
import pl.marcinchwedczuk.cjava.ast.expr.literal.LiteralAst;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.marcinchwedczuk.cjava.bytecode.test.fixtures.FixtureConstants.FIXTURE_PACKAGE;

public class AnnotationDecompilationTests extends BaseDecompilerTests {
	@Test
	public void canDecompileAnnotationsPutOnTopLevelClass() throws Exception {
		ClassDeclarationAst classDeclaration =
				decompile(Fixture_ClassWithAnnotations.class);

		assertThat(classDeclaration.getAnnotations())
				.hasSize(2);

		// Fixture_Annotation
		AnnotationAst ast = classDeclaration.getAnnotations().get(0);

		assertThat(ast.getAnnotationType().asSourceCodeString())
				.isEqualTo(FIXTURE_PACKAGE + ".Fixture_Annotation");

		assertThat(ast.getElementValuePairs())
				.hasSize(3);

		assertAssignsValueToProperty(ast.getElementValuePairs().get(0),
				"intField", 123);

		assertAssignsValueToProperty(ast.getElementValuePairs().get(1),
				"stringField", "foo");

		assertAssignsValueToProperty(ast.getElementValuePairs().get(2),
				"stringArrayField", new Object[] { "foo", "bar" });

		// Fixture_EmptyAnnotation
		ast = classDeclaration.getAnnotations().get(1);

		assertThat(ast.getAnnotationType().asSourceCodeString())
				.isEqualTo(FIXTURE_PACKAGE + ".Fixture_EmptyAnnotation");

		assertThat(ast.getElementValuePairs())
				.isEmpty();

	}

	private void assertAssignsValueToProperty(
			AnnotationPropertyAssignmentAst assignmentAst,
			String propertyName, Object value)
	{
		assertThat(assignmentAst.getPropertyName())
				.isEqualTo(propertyName);

		assertThat(((LiteralAst)assignmentAst.getPropertyValue()).getRawValue())
				.isEqualTo(value);
	}
}
