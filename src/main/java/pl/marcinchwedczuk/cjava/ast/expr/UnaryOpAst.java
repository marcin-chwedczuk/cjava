package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class UnaryOpAst extends ExprAst implements UnaryOp {

	public static UnaryOpAst create(JavaOperator operator, ExprAst expr) {
		return new AutoValue_UnaryOpAst.Builder()
				.setOperator(operator)
				.setExpr(expr)
				.build();
	}

	public abstract JavaOperator getOperator();
	public abstract ExprAst getExpr();

	public abstract Builder toBuilder();

	@Override
	public JavaType getResultType() {
		// This is a bit naive implementation
		// e.g. `-((short)3)` has type int.
		return getExpr().getResultType();
	}

	@Override
	public ExprAst astMap(AstMapper mapper) {
		ExprAst mappedExpr = getExpr().astMap(mapper);

		Builder mapped = this
				.toBuilder()
				.setExpr(mappedExpr);

		return mapper.map(this, mapped);
	}

	@AutoValue.Builder
	public static abstract class Builder {
		public abstract Builder setOperator(JavaOperator operator);
		public abstract Builder setExpr(ExprAst expr);

		public abstract UnaryOpAst build();
	}
}
