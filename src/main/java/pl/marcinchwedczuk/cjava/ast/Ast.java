package pl.marcinchwedczuk.cjava.ast;

import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;

public abstract class Ast {
	public <T extends Ast> T astMap(AstMapper mapper) {
		throw new RuntimeException("This method should be overriden in the superclass.");
	}
}
