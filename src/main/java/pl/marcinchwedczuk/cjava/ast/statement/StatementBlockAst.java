package pl.marcinchwedczuk.cjava.ast.statement;

import pl.marcinchwedczuk.cjava.ast.Ast;

import java.util.List;

public class StatementBlockAst extends Ast {
	private final List<StatementAst> statements;

	public StatementBlockAst(List<StatementAst> statements) {
		this.statements = statements;
	}

	public StatementAst getStatement(int index) {
		return statements.get(index);
	}
}
