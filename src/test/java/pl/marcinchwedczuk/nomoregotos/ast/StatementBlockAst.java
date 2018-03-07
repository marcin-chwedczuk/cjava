package pl.marcinchwedczuk.nomoregotos.ast;

import com.google.common.collect.Lists;

import java.sql.Statement;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class StatementBlockAst extends StatementAst {
	private List<StatementAst> statements = Lists.newArrayList();

	public StatementBlockAst addStatement(StatementAst statement) {
		statements.add(statement);
		return this;
	}

	public List<StatementAst> getStatements() {
		return statements;
	}

	@Override
	public String toString() {
		String statementsText = statements.stream()
				.map(s -> addIndent(s.toString()))
				.collect(joining("\n"));

		return "{\n" + statementsText + "\n}";
	}
}
