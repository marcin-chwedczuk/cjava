package pl.marcinchwedczuk.cjava.ast.statement;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.Ast;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.decompiler.signature.LocalVariable;

@AutoValue
public abstract class VariableDeclarationStatementAst extends StatementAst {

	public static VariableDeclarationStatementAst create(LocalVariable variable, ExprAst initializationExpr) {
		return new AutoValue_VariableDeclarationStatementAst.Builder()
				.setVariable(variable)
				.setInitializationExpr(initializationExpr)
				.build();
	}

	public abstract LocalVariable getVariable();
	public abstract ExprAst getInitializationExpr();

	public abstract Builder toBuilder();

	@Override
	public VariableDeclarationStatementAst astMap(AstMapper mapper) {
		ExprAst mappedInitExpr = getInitializationExpr().astMap(mapper);

		Builder mapped = this
				.toBuilder()
				.setInitializationExpr(mappedInitExpr);

		return mapper.map(this, mapped);
	}

	@AutoValue.Builder
	public static abstract class Builder {
		public abstract Builder setVariable(LocalVariable localVariable);
		public abstract Builder setInitializationExpr(ExprAst initializationExpr);

		public abstract VariableDeclarationStatementAst build();
	}
}
