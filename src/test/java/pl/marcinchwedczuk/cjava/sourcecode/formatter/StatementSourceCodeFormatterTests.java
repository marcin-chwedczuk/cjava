package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.statement.ExprStatementAst;
import pl.marcinchwedczuk.cjava.decompiler.fixture.AstFixtures;

import static org.assertj.core.api.Assertions.assertThat;

public class StatementSourceCodeFormatterTests {

	@Test
	public void canFormatPrintHelloWorldStatement() throws Exception {
		ExprStatementAst printHelloWorld =
				AstFixtures.createPrintHelloWorldStatement();

		JavaCodeWriter codeWriter = new JavaCodeWriter();
		new StatementSourceCodeFormatter(printHelloWorld, codeWriter)
				.convertAstToJavaCode();
		String sourceCode = codeWriter.dumpSourceCode();

		assertThat(sourceCode)
				.isEqualToIgnoringWhitespace(
						"java.lang.System.out.println(\"Hello, world!\");");

	}
}