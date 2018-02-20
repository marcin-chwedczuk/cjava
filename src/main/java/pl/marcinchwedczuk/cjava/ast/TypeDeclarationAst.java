package pl.marcinchwedczuk.cjava.ast;

import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;

public abstract class TypeDeclarationAst extends Ast {
	@Override
	public abstract TypeDeclarationAst astMap(AstMapper mapper);
}
