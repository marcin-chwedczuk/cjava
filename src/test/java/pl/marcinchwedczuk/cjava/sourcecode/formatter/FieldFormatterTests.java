package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.FieldDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.Visibility;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.ClassType;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class FieldFormatterTests {
	@Test
	public void canPrintJavaCodeForSimpleField() throws Exception {
		FieldDeclarationAst declarationAst = new FieldDeclarationAst(
				ClassType.fromPackageAndClassName("java.lang", "Boolean"),
				"isValid");

		declarationAst.setVisibility(Visibility.PRIVATE);
		declarationAst.setTransient(true);

		String sourceCode = format(declarationAst);

		assertThat(sourceCode)
				.isEqualTo("private transient java.lang.Boolean isValid;");
	}

	@Test
	public void canPrintFieldAnnotations() throws Exception {
		FieldDeclarationAst declarationAst = new FieldDeclarationAst(
				ClassType.fromPackageAndClassName("java.lang", "Boolean"),
				"propertyA");
		declarationAst.setVisibility(Visibility.PACKAGE);

		declarationAst.setAnnotations(asList(
				new AnnotationAst(
						ClassType.fromPackageAndClassName("mc.test", "Validate"))
		));

		String sourceCode = format(declarationAst);

		assertThat(sourceCode)
				.isEqualTo(
						"@mc.test.Validate\n" +
						"java.lang.Boolean propertyA;");
	}

	private String format(FieldDeclarationAst declarationAst) {
		JavaCodeWriter codeWriter = new JavaCodeWriter();

		new FieldSourceCodeFormatter(declarationAst, codeWriter)
				.convertAstToJavaCode();

		return codeWriter.dumpSourceCode();
	}
}
