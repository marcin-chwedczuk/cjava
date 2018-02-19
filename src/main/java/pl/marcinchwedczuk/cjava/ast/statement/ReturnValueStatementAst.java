package pl.marcinchwedczuk.cjava.ast.statement;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.Ast;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;

@AutoValue
public abstract class ReturnValueStatementAst extends StatementAst {
	public static ReturnValueStatementAst create(ExprAst value) {
		return new AutoValue_ReturnValueStatementAst.Builder()
				.setValue(value)
				.build();
	}

	public abstract ExprAst getValue();

	public abstract Builder toBuilder();

	@Override
	public ReturnValueStatementAst astMap(AstMapper mapper) {
		ExprAst mappedExpr = getValue().astMap(mapper);

		Builder mapped = this
				.toBuilder()
				.setValue(mappedExpr);

		return mapper.map(this, mapped);
	}

	@AutoValue.Builder
	public static abstract class Builder {
		public abstract Builder setValue(ExprAst expr);
		public abstract ReturnValueStatementAst build();
	}
}
