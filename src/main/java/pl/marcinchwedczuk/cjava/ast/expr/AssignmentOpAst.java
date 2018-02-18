package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class AssignmentOpAst extends ExprAst {

	public static AssignmentOpAst create(LValueAst variable, ExprAst value) {
		return new AutoValue_AssignmentOpAst(variable, value);
	}

	public abstract LValueAst getVariable();
	public abstract ExprAst getValue();

	@Override
	public JavaType getResultType() {
		return getVariable().getResultType();
	}
}
