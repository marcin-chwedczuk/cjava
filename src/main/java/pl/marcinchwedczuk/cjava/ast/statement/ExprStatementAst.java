package pl.marcinchwedczuk.cjava.ast.statement;

import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.expr.MethodCallAst;

public class ExprStatementAst extends StatementAst {
	private final ExprAst expression;

	public ExprStatementAst(ExprAst expression) {
		this.expression = expression;
	}
}
