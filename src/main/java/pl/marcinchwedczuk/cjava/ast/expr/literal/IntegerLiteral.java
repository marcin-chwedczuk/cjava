package pl.marcinchwedczuk.cjava.ast.expr.literal;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class IntegerLiteral extends LiteralAst {
	public static IntegerLiteral of(int value) {
		return new AutoValue_IntegerLiteral(value);
	}

	public abstract int getValue();

	@Override
	public Object getRawValue() {
		return getValue();
	}
}
