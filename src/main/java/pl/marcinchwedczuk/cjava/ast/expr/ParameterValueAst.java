package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ParameterValueAst extends ExprAst {
	public static ParameterValueAst forParameter(String parameterName) {
		return new AutoValue_ParameterValueAst(parameterName);
	}

	public abstract String parameterName();
}
