package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class NewOpAst extends ExprAst {

	public static NewOpAst create(JavaType type) {
		return new AutoValue_NewOpAst(type);
	}

	public abstract JavaType getType();

	// TODO: Constructor parameters
}
