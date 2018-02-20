package pl.marcinchwedczuk.cjava.ast;

import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;

public abstract class Ast {
	public abstract Ast astMap(AstMapper mapper);
}
