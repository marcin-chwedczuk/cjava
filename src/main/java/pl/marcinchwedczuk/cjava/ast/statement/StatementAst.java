package pl.marcinchwedczuk.cjava.ast.statement;

import pl.marcinchwedczuk.cjava.ast.Ast;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;

public abstract class StatementAst extends Ast {

	@Override
	public StatementAst astMap(AstMapper mapper) {
		return super.astMap(mapper);
	}
}
