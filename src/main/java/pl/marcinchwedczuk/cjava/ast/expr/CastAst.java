package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class CastAst extends ExprAst implements UnaryOp {

	public static CastAst create(JavaType targetType, ExprAst expr) {
		return new AutoValue_CastAst(targetType, expr);
	}

	public abstract JavaType getTargetType();
	public abstract ExprAst getExpr();

	@Override
	public JavaOperator getOperator() {
		return JavaOperator.TYPE_CAST;
	}

	@Override
	public JavaType getResultType() {
		return getTargetType();
	}
}
