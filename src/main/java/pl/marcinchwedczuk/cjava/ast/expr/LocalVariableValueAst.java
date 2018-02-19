package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.decompiler.signature.LocalVariable;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class LocalVariableValueAst extends LValueAst {
	public static LocalVariableValueAst forVariable(LocalVariable variable) {
		return new AutoValue_LocalVariableValueAst(variable);
	}

	public abstract LocalVariable getVariable();

	@Override
	public JavaType getResultType() {
		return getVariable().getType();
	}
}
