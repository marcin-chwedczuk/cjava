package pl.marcinchwedczuk.cjava.ast.expr;

public interface BinaryOp {
	ExprAst getLeft();
	ExprAst getRight();
	JavaOperator getOperator();
}
