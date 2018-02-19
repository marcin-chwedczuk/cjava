package pl.marcinchwedczuk.cjava.ast.expr;

import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;

public abstract class LValueAst extends ExprAst {

	@Override
	public LValueAst astMap(AstMapper mapper) {
		throw new RuntimeException();
	}
}
