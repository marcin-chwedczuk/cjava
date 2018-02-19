package pl.marcinchwedczuk.cjava.ast.statement;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.Ast;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;

@AutoValue
public abstract class ReturnStatementAst extends StatementAst {
	public static ReturnStatementAst create() {
		return new AutoValue_ReturnStatementAst();
	}

	@Override
	public ReturnStatementAst astMap(AstMapper mapper) {
		return mapper.map(this);
	}
}
