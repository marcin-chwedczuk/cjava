package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class BinaryOpAst extends ExprAst {
	public static BinaryOpAst create(BinaryOperator operator, ExprAst left, ExprAst right) {
		return new AutoValue_BinaryOpAst(operator, left, right);
	}

	public abstract BinaryOperator getOperator();
	public abstract ExprAst getLeft();
	public abstract ExprAst getRight();
}
