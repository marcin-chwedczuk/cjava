package pl.marcinchwedczuk.cjava.ast.statement;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.ast.Ast;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

@AutoValue
public abstract class StatementBlockAst extends Ast {
	public static StatementBlockAst fromStatements(List<StatementAst> statements) {
		return new AutoValue_StatementBlockAst(ImmutableList.copyOf(statements));
	}

	public static StatementBlockAst empty() {
		return fromStatements(emptyList());
	}

	public abstract ImmutableList<StatementAst> getStatements();

	public StatementAst getStatement(int index) {
		return getStatements().get(index);
	}
}
