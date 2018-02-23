package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.statement.ExprStatementAst;
import pl.marcinchwedczuk.cjava.ast.statement.StatementAst;
import pl.marcinchwedczuk.cjava.ast.statement.VariableDeclarationStatementAst;
import pl.marcinchwedczuk.cjava.decompiler.fixture.AstBuilder;
import pl.marcinchwedczuk.cjava.decompiler.fixture.AstFixtures;
import pl.marcinchwedczuk.cjava.optimizer.imports.FullQualifiedNameJavaTypeNameRenderer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static pl.marcinchwedczuk.cjava.decompiler.fixture.AstBuilder.string;
import static pl.marcinchwedczuk.cjava.decompiler.fixture.AstBuilder.var;

public class StatementSourceCodeFormatterTests {

	@Test
	public void canFormatPrintHelloWorldStatement() throws Exception {
		ExprStatementAst printHelloWorld =
				AstFixtures.createPrintHelloWorldStatement();

		String sourceCode = format(printHelloWorld);

		assertThat(sourceCode)
				.isEqualToIgnoringWhitespace(
						"java.lang.System.out.println(\"Hello, world!\");");
	}


	@Test
	public void canFormatVariableDeclaration() throws Exception {
		VariableDeclarationStatementAst varDeclaration = VariableDeclarationStatementAst.create(
				var(string(), "abc"),
				string("fuzble"));

		String sourceCode = format(varDeclaration);

		assertThat(sourceCode)
				.isEqualToIgnoringWhitespace("java.lang.String abc = \"fuzble\";");
	}

	private static String format(StatementAst printHelloWorld) {
		JavaCodeWriter codeWriter = new JavaCodeWriter();

		new StatementSourceCodeFormatter(
					new FullQualifiedNameJavaTypeNameRenderer(),
					codeWriter, printHelloWorld)
				.convertAstToJavaCode();

		return codeWriter.dumpSourceCode();
	}
}