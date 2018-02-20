package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class CastAst extends ExprAst implements UnaryOp {
	public static CastAst create(JavaType targetType, ExprAst expr) {
		return new AutoValue_CastAst.Builder()
				.setTargetType(targetType)
				.setExpr(expr)
				.build();
	}

	public abstract JavaType getTargetType();
	public abstract ExprAst getExpr();

	public abstract Builder toBuilder();

	@Override
	public JavaOperator getOperator() {
		return JavaOperator.TYPE_CAST;
	}

	@Override
	public JavaType getResultType() {
		return getTargetType();
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
		public abstract Builder setTargetType(JavaType type);
		public abstract Builder setExpr(ExprAst expr);

		public abstract CastAst build();
	}
}
