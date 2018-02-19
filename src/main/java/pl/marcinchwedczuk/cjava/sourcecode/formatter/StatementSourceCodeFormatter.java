package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.statement.*;
import pl.marcinchwedczuk.cjava.decompiler.signature.LocalVariable;

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
			printReturn((ReturnStatementAst) statement);
		} else if (statement instanceof ReturnValueStatementAst) {
			printReturnValue((ReturnValueStatementAst)statement);
		} else if (statement instanceof VariableDeclarationStatementAst) {
			printVariableDeclaration((VariableDeclarationStatementAst)statement);
		} else {
			throw new RuntimeException("Statement: " +
					statement.getClass().getSimpleName() +
					" is not yet supported!");
		}
	}

	private void printExpression(ExprStatementAst exprStatement) {
		codeWriter.printIndent();
		printExpression(exprStatement.getExpression());
		codeWriter.print(";").printNewLine();
	}

	private void printVariableDeclaration(VariableDeclarationStatementAst statement) {
		LocalVariable variable = statement.getVariable();

		codeWriter.printIndent()
				.print(variable.getType().asSourceCodeString())
				.print(" ")
				.print(variable.getName())
				.print(" = ");
		printExpression(statement.getInitializationExpr());
		codeWriter.print(";").printNewLine();
	}

	private void printReturnValue(ReturnValueStatementAst returnValue) {
		codeWriter.printIndent().print("return ");
		printExpression(returnValue.getValue());
		codeWriter.print(";").printNewLine();
	}

	private void printReturn(ReturnStatementAst returnStatement) {
		codeWriter.printIndent()
				.print("return;")
				.printNewLine();
	}

	private void printExpression(ExprAst expr) {
		new ExpressionSourceCodeFormatter(codeWriter)
				.convertAstToJavaCode(expr);
	}
}
