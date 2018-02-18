package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.Ast;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class ThisValueAst extends ExprAst {
	public static ThisValueAst create() {
		return new AutoValue_ThisValueAst();
	}

	@Override
	public JavaType getResultType() {
		throw new RuntimeException("Not implemented! Nyaa!");
	}
}
