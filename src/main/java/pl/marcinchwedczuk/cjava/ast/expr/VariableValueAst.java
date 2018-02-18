package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class VariableValueAst extends ExprAst {
	public static VariableValueAst forVariable(String variableName) {
		return new AutoValue_VariableValueAst(variableName);
	}

	public abstract String getVariableName();
}
