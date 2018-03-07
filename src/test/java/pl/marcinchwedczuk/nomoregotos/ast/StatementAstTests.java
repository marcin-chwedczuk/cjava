package pl.marcinchwedczuk.nomoregotos.ast;

import org.junit.Test;
import pl.marcinchwedczuk.nomoregotos.condexpr.Condition;

import static org.assertj.core.api.Assertions.assertThat;

public class StatementAstTests {
	@Test
	public void toStringWorks() throws Exception {
		IfStatementAst if_ = new IfStatementAst(
				Condition.variable("x"),
				new StatementBlockAst()
					.addStatement(new IfStatementAst(
							Condition.negatedVariable("y"),
							new StatementBlockAst()
								.addStatement(new ExprStatementAst("n1")))));

		if_.elseBlock = new StatementBlockAst()
				.addStatement(new ExprStatementAst("n2"));

		System.out.println(if_.elseBlock.toString());
		System.out.println(if_.toString());
	}
}
