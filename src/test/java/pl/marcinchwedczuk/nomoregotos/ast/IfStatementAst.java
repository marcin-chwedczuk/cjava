package pl.marcinchwedczuk.nomoregotos.ast;

import pl.marcinchwedczuk.nomoregotos.condexpr.Condition;

public class IfStatementAst extends StatementAst {
	public final Condition condition;
	public final StatementBlockAst thenBlock;
	public StatementBlockAst elseBlock;

	public IfStatementAst(Condition condition, StatementBlockAst thenBlock) {
		this.thenBlock = thenBlock;
		this.condition = condition;
	}

	@Override
	public String toString() {
		String conditionText = condition.toString();
		String thenBlockText = thenBlock.toString();

		String elseBlockText = elseBlock != null
				? elseBlock.toString()
				: null;

		return String.format(
				"if (%s) %s" + (elseBlockText != null ? " else %s" : ""),
				conditionText, thenBlockText, elseBlockText);
	}
}
