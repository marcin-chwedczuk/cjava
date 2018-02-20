package pl.marcinchwedczuk.cjava.ast.expr;

import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;

public abstract class LValueAst extends ExprAst {
	public abstract LValueAst astMap(AstMapper mapper);
}
