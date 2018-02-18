package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class NewInstanceAst extends ExprAst {

	public static NewInstanceAst create(JavaType type) {
		return new AutoValue_NewInstanceAst(type);
	}

	public abstract JavaType getType();

	// TODO: Constructor parameters

	@Override
	public JavaType getResultType() {
		return getType();
	}
}
