package pl.marcinchwedczuk.cjava.ast.expr;

public interface UnaryOp {
	ExprAst getExpr();
	JavaOperator getOperator();
}
