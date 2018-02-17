package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationPropertyAssignmentAst;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.expr.literal.IntegerLiteral;
import pl.marcinchwedczuk.cjava.ast.expr.literal.StringLiteral;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

public class AnnotationFormatterTests {
	@Test
	public void canFormatEmptyAnnotation() throws Exception {
		AnnotationAst annotationAst = AnnotationAst.create(
				ClassType.fromPackageAndClassName("foo.bar", "Annotation"));

		String javaCode = format(annotationAst);

		assertThat(javaCode)
				.isEqualTo("@foo.bar.Annotation");
	}

	@Test
	public void canFormatAnnotationWithAssignments() throws Exception {
		AnnotationAst annotationAst = AnnotationAst.create(
				ClassType.fromPackageAndClassName("foo.bar", "Annotation"),
				asList(
						assign("someProperty", IntegerLiteral.of(1)),
						assign("otherProperty", StringLiteral.of("Some string"))
				));

		String javaCode = format(annotationAst);

		assertThat(javaCode)
				.isEqualTo("@foo.bar.Annotation(someProperty = 1, otherProperty = \"Some string\")");
	}

	private static AnnotationPropertyAssignmentAst assign(String property, ExprAst value) {
		return AnnotationPropertyAssignmentAst.create(property, value);
	}

	private static String format(AnnotationAst annotationAst) {
		JavaCodeWriter codeWriter = new JavaCodeWriter();

		AnnotationSourceCodeFormatter formatter =
				new AnnotationSourceCodeFormatter(annotationAst, codeWriter);
		formatter.convertAstToJavaCode();

		return codeWriter.dumpSourceCode();
	}
}
