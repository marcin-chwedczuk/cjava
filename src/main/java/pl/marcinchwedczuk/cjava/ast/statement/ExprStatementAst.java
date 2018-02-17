package pl.marcinchwedczuk.cjava.ast.statement;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.expr.MethodCallAst;

@AutoValue
public abstract class ExprStatementAst extends StatementAst {
	public static ExprStatementAst fromExpr(ExprAst expression) {
		return new AutoValue_ExprStatementAst(expression);
	}

	public abstract ExprAst getExpression();
}
