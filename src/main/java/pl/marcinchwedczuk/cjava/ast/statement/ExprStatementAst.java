package pl.marcinchwedczuk.cjava.ast.statement;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.Ast;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.expr.MethodCallAst;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;

@AutoValue
public abstract class ExprStatementAst extends StatementAst {
	public static ExprStatementAst fromExpr(ExprAst expression) {
		return new AutoValue_ExprStatementAst.Builder()
				.setExpression(expression)
				.build();
	}

	public abstract ExprAst getExpression();

	public abstract Builder toBuilder();

	@Override
	public ExprStatementAst astMap(AstMapper mapper) {
		ExprAst mappedExpr = getExpression().astMap(mapper);

		Builder mapped = this
				.toBuilder()
				.setExpression(mappedExpr);

		return mapper.map(this, mapped);
	}

	@AutoValue.Builder
	public static abstract class Builder {
		public abstract Builder setExpression(ExprAst expr);
		public abstract ExprStatementAst build();
	}
}
