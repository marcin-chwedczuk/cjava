package pl.marcinchwedczuk.cjava.ast.statement;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;

@AutoValue
public abstract class ThrowStatementAst extends StatementAst {
	public static ThrowStatementAst create(ExprAst exception) {
		return new AutoValue_ThrowStatementAst.Builder()
				.setException(exception)
				.build();
	}

	public abstract ExprAst getException();
	public abstract Builder toBuilder();

	@Override
	public StatementAst astMap(AstMapper mapper) {
		ExprAst mappedException = getException().astMap(mapper);

		Builder mapped = this
				.toBuilder()
				.setException(mappedException);

		return mapper.map(this, mapped);
	}

	@AutoValue.Builder
	public static abstract class Builder {
		public abstract Builder setException(ExprAst expr);
		public abstract ThrowStatementAst build();
	}
}
