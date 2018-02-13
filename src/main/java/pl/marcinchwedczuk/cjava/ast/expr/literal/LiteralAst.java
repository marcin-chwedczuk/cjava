package pl.marcinchwedczuk.cjava.ast.expr.literal;

import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;

public abstract class LiteralAst extends ExprAst {
	public abstract Object getRawValue();
}
