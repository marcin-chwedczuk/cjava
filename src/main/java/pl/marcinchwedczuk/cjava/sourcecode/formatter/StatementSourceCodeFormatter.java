package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.statement.*;
import pl.marcinchwedczuk.cjava.decompiler.signature.LocalVariable;
import pl.marcinchwedczuk.cjava.optimizer.imports.JavaTypeNameRenderer;

import static java.util.Objects.requireNonNull;

public class StatementSourceCodeFormatter extends BaseSourceCodeFormatter {
	private final StatementAst statement;

	public StatementSourceCodeFormatter(JavaTypeNameRenderer typeNameRenderer, JavaCodeWriter codeWriter, StatementAst statement) {
		super(typeNameRenderer, codeWriter);
		this.statement = requireNonNull(statement);
	}

	public void convertAstToJavaCode() {
		if (statement instanceof ExprStatementAst) {
			printExpression((ExprStatementAst) statement);
		} else if (statement instanceof ReturnStatementAst) {
			printReturn((ReturnStatementAst) statement);
		} else if (statement instanceof ReturnValueStatementAst) {
			printReturnValue((ReturnValueStatementAst)statement);
		} else if (statement instanceof VariableDeclarationStatementAst) {
			printVariableDeclaration((VariableDeclarationStatementAst) statement);
		} else if (statement instanceof ThrowStatementAst) {
			printThrowStatement((ThrowStatementAst)statement);
		} else {
			throw new RuntimeException("Statement: " +
					statement.getClass().getSimpleName() +
					" is not yet supported!");
		}
	}

	private void printThrowStatement(ThrowStatementAst throwStatement) {
		codeWriter.printIndent().print("throw ");
		printExpression(throwStatement.getException());
		codeWriter.print(";").printNewLine();
	}

	private void printExpression(ExprStatementAst exprStatement) {
		codeWriter.printIndent();
		printExpression(exprStatement.getExpression());
		codeWriter.print(";").printNewLine();
	}

	private void printVariableDeclaration(VariableDeclarationStatementAst statement) {
		LocalVariable variable = statement.getVariable();

		codeWriter.printIndent()
				.print(typeName(variable.getType()))
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
		new ExpressionSourceCodeFormatter(typeNameRenderer, codeWriter)
				.convertAstToJavaCode(expr);
	}
}
