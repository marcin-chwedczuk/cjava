package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class AssignmentOpAst extends ExprAst implements BinaryOp {

	public static AssignmentOpAst create(LValueAst variable, ExprAst value) {
		return new AutoValue_AssignmentOpAst(variable, value);
	}

	public abstract LValueAst getVariable();
	public abstract ExprAst getValue();

	@Override
	public ExprAst getLeft() {
		return getVariable();
	}

	@Override
	public ExprAst getRight() {
		return getValue();
	}

	@Override
	public JavaOperator getOperator() {
		return JavaOperator.ASSIGNMENT;
	}

	@Override
	public JavaType getResultType() {
		return getVariable().getResultType();
	}
}
