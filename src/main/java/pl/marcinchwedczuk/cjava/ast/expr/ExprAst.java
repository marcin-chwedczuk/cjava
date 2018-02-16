package pl.marcinchwedczuk.cjava.ast.expr;

import pl.marcinchwedczuk.cjava.ast.Ast;

public abstract class ExprAst extends Ast {
	public Object getRawValue() {
		throw new IllegalStateException("Operation not supported.");
	}
}
