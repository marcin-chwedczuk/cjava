package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class AssignmentOpAst extends ExprAst implements BinaryOp {

	public static AssignmentOpAst create(LValueAst variable, ExprAst value) {
		return new AutoValue_AssignmentOpAst.Builder()
				.setVariable(variable)
				.setValue(value)
				.build();
	}

	public abstract LValueAst getVariable();
	public abstract ExprAst getValue();

	public abstract Builder toBuilder();

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

	@Override
	public AssignmentOpAst astMap(AstMapper mapper) {
		LValueAst mappedVariable = getVariable().astMap(mapper);
		ExprAst mappedValue = getValue().astMap(mapper);

		Builder mapped = this
				.toBuilder()
				.setVariable(mappedVariable)
				.setValue(mappedValue);

		return mapper.map(this, mapped);
	}

	@AutoValue.Builder
	public abstract static class Builder {
		public abstract Builder setVariable(LValueAst lvalue);
		public abstract Builder setValue(ExprAst value);

		public abstract AssignmentOpAst build();
	}
}
