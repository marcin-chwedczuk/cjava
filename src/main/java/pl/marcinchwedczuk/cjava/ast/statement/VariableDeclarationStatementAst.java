package pl.marcinchwedczuk.cjava.ast.statement;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.decompiler.signature.LocalVariable;

@AutoValue
public abstract class VariableDeclarationStatementAst extends StatementAst {

	public static VariableDeclarationStatementAst create(LocalVariable variable, ExprAst initializationExpr) {
		return new AutoValue_VariableDeclarationStatementAst(variable, initializationExpr);
	}

	public abstract LocalVariable getVariable();
	public abstract ExprAst getInitializationExpr();
}
