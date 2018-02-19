package pl.marcinchwedczuk.cjava.ast.expr.literal;

import pl.marcinchwedczuk.cjava.ast.Ast;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;

public abstract class LiteralAst extends ExprAst {
	public abstract Object getRawValue();
}
