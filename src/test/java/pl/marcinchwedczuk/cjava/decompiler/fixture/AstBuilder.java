package pl.marcinchwedczuk.cjava.decompiler.fixture;

import pl.marcinchwedczuk.cjava.ast.expr.BinaryOpAst;
import pl.marcinchwedczuk.cjava.ast.expr.BinaryOperator;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.expr.ParameterValueAst;

public class AstBuilder {
	public static BinaryOpAst binOp(BinaryOperator operator, ExprAst left, ExprAst right) {
		return BinaryOpAst.create(operator, left, right);
	}

	public static ParameterValueAst param(String name) {
		return ParameterValueAst.forParameter(name);
	}
}
