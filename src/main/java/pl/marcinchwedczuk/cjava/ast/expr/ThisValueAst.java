package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.Ast;

@AutoValue
public abstract class ThisValueAst extends ExprAst {
	public static ThisValueAst create() {
		return new AutoValue_ThisValueAst();
	}
}
