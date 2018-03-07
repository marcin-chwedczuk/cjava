package pl.marcinchwedczuk.nomoregotos.ast;

public class ExprStatementAst extends StatementBlockAst {
	public final String instruction;

	public ExprStatementAst(String instruction) {
		this.instruction = instruction;
	}

	@Override
	public String toString() {
		return instruction + ";";
	}

}
