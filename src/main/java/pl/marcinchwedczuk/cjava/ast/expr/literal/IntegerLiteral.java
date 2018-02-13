package pl.marcinchwedczuk.cjava.ast.expr.literal;

public class IntegerLiteral extends LiteralAst {
	private final int value;

	public IntegerLiteral(int value) {
		this.value = value;
	}

	@Override
	public Object getRawValue() {
		return value;
	}
}
