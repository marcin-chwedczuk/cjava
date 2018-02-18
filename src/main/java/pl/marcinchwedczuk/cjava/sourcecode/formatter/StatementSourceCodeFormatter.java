package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.statement.ExprStatementAst;
import pl.marcinchwedczuk.cjava.ast.statement.ReturnStatementAst;
import pl.marcinchwedczuk.cjava.ast.statement.StatementAst;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class StatementSourceCodeFormatter implements SourceCodeFormatter {
	private final StatementAst statement;
	private final JavaCodeWriter codeWriter;

	public StatementSourceCodeFormatter(StatementAst statement, JavaCodeWriter codeWriter) {
		this.statement = requireNonNull(statement);
		this.codeWriter = requireNonNull(codeWriter);
	}

	@Override
	public void convertAstToJavaCode() {
		if (statement instanceof ExprStatementAst) {
			printExpression((ExprStatementAst) statement);
		} else if (statement instanceof ReturnStatementAst) {
			printReturn((ReturnStatementAst)statement);
		} else {
			throw new RuntimeException("Statement: " +
					statement.getClass().getSimpleName() +
					" is not yet supported!");
		}
	}

	private void printReturn(ReturnStatementAst returnStatement) {
		codeWriter.print("return;");
	}

	private void printExpression(ExprStatementAst statement) {
		ExprAst expression = statement.getExpression();

		codeWriter.printIndent();

		new ExpressionSourceCodeFormatter(codeWriter)
				.convertAstToJavaCode(expression);

		codeWriter.print(";");
	}
}
