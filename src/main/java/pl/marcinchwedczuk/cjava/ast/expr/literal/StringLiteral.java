package pl.marcinchwedczuk.cjava.ast.expr.literal;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;

@AutoValue
public abstract class StringLiteral extends LiteralAst {

	public static StringLiteral of(String string) {
		return new AutoValue_StringLiteral(string);
	}

	public abstract String getValue();

	@Override
	public Object getRawValue() {
		return getValue();
	}
}
