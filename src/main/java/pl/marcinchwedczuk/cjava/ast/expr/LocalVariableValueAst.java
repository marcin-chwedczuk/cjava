package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.decompiler.signature.LocalVariable;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class LocalVariableValueAst extends LValueAst {
	public static LocalVariableValueAst forVariable(LocalVariable variable) {
		return new AutoValue_LocalVariableValueAst.Builder()
				.setVariable(variable)
				.build();
	}

	public abstract LocalVariable getVariable();

	public abstract Builder toBuilder();

	@Override
	public JavaType getResultType() {
		return getVariable().getType();
	}

	@Override
	public LValueAst astMap(AstMapper mapper) {
		Builder mapped = this.toBuilder();
		return mapper.map(this, mapped);
	}

	@AutoValue.Builder
	public static abstract class Builder {
		public abstract Builder setVariable(LocalVariable variable);

		public abstract LocalVariableValueAst build();
	}
}
