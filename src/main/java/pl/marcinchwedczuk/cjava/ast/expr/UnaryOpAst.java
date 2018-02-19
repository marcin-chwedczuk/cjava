package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class UnaryOpAst extends ExprAst implements UnaryOp {

	public static UnaryOpAst create(JavaOperator operator, ExprAst expr) {
		return new AutoValue_UnaryOpAst(operator, expr);
	}

	public abstract JavaOperator getOperator();
	public abstract ExprAst getExpr();

	@Override
	public JavaType getResultType() {
		// This is a bit naive implementation
		// e.g. `-((short)3)` has type int.
		return getExpr().getResultType();
	}
}
