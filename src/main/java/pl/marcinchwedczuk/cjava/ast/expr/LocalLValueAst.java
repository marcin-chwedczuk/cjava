package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.decompiler.signature.LocalVariable;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class LocalLValueAst extends LValueAst {
	public static LocalLValueAst forVariable(LocalVariable variable) {
		return new AutoValue_LocalLValueAst(variable);
	}

	public abstract LocalVariable getVariable();

	@Override
	public JavaType getResultType() {
		return getVariable().getType();
	}
}
