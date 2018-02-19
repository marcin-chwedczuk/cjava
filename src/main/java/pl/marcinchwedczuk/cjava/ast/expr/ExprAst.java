package pl.marcinchwedczuk.cjava.ast.expr;

import pl.marcinchwedczuk.cjava.ast.Ast;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

public abstract class ExprAst extends Ast {
	public Object getRawValue() {
		throw new IllegalStateException("Operation not supported.");
	}

	public abstract JavaType getResultType();

	@Override
	public ExprAst astMap(AstMapper mapper) {
		return super.astMap(mapper);
	}
}
