package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodParameter;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class ParameterValueAst extends LValueAst {
	public static ParameterValueAst forParameter(MethodParameter parameter) {
		return new AutoValue_ParameterValueAst(parameter);
	}

	public abstract MethodParameter getParameter();

	@Override
	public JavaType getResultType() {
		return getParameter().getType();
	}
}
