package pl.marcinchwedczuk.cjava.ast.statement;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;

@AutoValue
public abstract class ReturnValueStatementAst extends StatementAst {
	public static ReturnValueStatementAst create(ExprAst value) {
		return new AutoValue_ReturnValueStatementAst(value);
	}

	public abstract ExprAst getValue();
}
