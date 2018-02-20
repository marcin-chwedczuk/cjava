package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodParameter;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class ParameterValueAst extends LValueAst {
	public static ParameterValueAst forParameter(MethodParameter parameter) {
		return new AutoValue_ParameterValueAst.Builder()
				.setParameter(parameter)
				.build();
	}

	public abstract MethodParameter getParameter();

	public abstract Builder toBuilder();

	@Override
	public JavaType getResultType() {
		return getParameter().getType();
	}

	@Override
	public LValueAst astMap(AstMapper mapper) {
		return mapper.map(this);
	}

	@AutoValue.Builder
	public static abstract class Builder {
		public abstract Builder setParameter(MethodParameter methodParameter);

		public abstract ParameterValueAst build();
	}
}
