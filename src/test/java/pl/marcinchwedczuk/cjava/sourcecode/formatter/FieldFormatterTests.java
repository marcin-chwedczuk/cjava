package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.FieldDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.Visibility;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class FieldFormatterTests {
	@Test
	public void canPrintJavaCodeForSimpleField() throws Exception {
		FieldDeclarationAst declarationAst = FieldDeclarationAst
				.builder(ClassType.of(Boolean.class), "isValid")
				.setVisibility(Visibility.PRIVATE)
				.setTransient(true)
				.build();

		String sourceCode = format(declarationAst);

		assertThat(sourceCode)
				.isEqualTo("private transient java.lang.Boolean isValid;");
	}

	@Test
	public void canPrintFieldAnnotations() throws Exception {
		FieldDeclarationAst declarationAst = FieldDeclarationAst
				.builder(ClassType.of(Boolean.class), "propertyA")
				.setVisibility(Visibility.PACKAGE)
				.setAnnotations(
					AnnotationAst.create(ClassType.fromPackageAndClassName("mc.test", "Validate")))
				.build();

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
