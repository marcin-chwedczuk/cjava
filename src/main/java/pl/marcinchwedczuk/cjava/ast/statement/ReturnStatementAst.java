package pl.marcinchwedczuk.cjava.ast.statement;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.Ast;

@AutoValue
public abstract class ReturnStatementAst extends StatementAst {
	public static ReturnStatementAst create() {
		return new AutoValue_ReturnStatementAst();
	}
}
