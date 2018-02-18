package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class BinaryOpAst extends ExprAst {
	public static BinaryOpAst create(BinaryOperator operator, ExprAst left, ExprAst right) {
		return new AutoValue_BinaryOpAst(operator, left, right);
	}

	public abstract BinaryOperator getOperator();
	public abstract ExprAst getLeft();
	public abstract ExprAst getRight();

	@Override
	public JavaType getResultType() {
		JavaType left = getLeft().getResultType();
		JavaType right = getRight().getResultType();

		Preconditions.checkState(left.equals(right),
				"Types should be equal.");

		return left;
	}
}
