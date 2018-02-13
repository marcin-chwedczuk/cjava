package pl.marcinchwedczuk.cjava.ast.expr;

public abstract class ExprAst {
	public Object getRawValue() {
		throw new IllegalStateException("Operation not supported.");
	}
}
