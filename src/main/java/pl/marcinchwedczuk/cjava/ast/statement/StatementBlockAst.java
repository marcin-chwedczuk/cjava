package pl.marcinchwedczuk.cjava.ast.statement;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.ast.Ast;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@AutoValue
public abstract class StatementBlockAst extends Ast {
	public static StatementBlockAst fromStatements(List<StatementAst> statements) {
		return new AutoValue_StatementBlockAst.Builder()
				.setStatements(statements)
				.build();
	}

	public static StatementBlockAst empty() {
		return fromStatements(emptyList());
	}

	public abstract ImmutableList<StatementAst> getStatements();
	public abstract Builder toBuilder();

	@Override
	public StatementBlockAst astMap(AstMapper mapper) {
		List<StatementAst> mappedStatements = getStatements().stream()
				.map(s -> s.astMap(mapper))
				.collect(toList());

		Builder mapped = this
				.toBuilder()
				.setStatements(mappedStatements);

		return mapper.map(this, mapped);
	}

	public StatementAst getStatement(int index) {
		return getStatements().get(index);
	}

	@AutoValue.Builder
	public abstract static class Builder {
		public abstract Builder setStatements(List<StatementAst> statements);

		public abstract StatementBlockAst build();
	}
}
