package pl.marcinchwedczuk.cjava.ast.expr.literal;

import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;

public class StringLiteral extends LiteralAst {
	private final String value;

	public StringLiteral(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public Object getRawValue() {
		return value;
	}
}
