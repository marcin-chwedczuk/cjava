package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.ast.expr.*;
import pl.marcinchwedczuk.cjava.ast.expr.literal.*;

import static java.util.Objects.requireNonNull;
import static pl.marcinchwedczuk.cjava.sourcecode.formatter.JavaLiteralUtil.javaEscape;
import static pl.marcinchwedczuk.cjava.sourcecode.formatter.ListWriter.writeList;

public class ExpressionSourceCodeFormatter {
	private final JavaCodeWriter codeWriter;

	public ExpressionSourceCodeFormatter(JavaCodeWriter codeWriter) {
		this.codeWriter = requireNonNull(codeWriter);
	}

	public void convertAstToJavaCode(ExprAst expression) {
		printExpression(expression);
	}

	private void printExpression(ExprAst expression) {
		if (expression instanceof LiteralAst) {
			printLiteral((LiteralAst)expression);
		} else if (expression instanceof FieldAccessAst) {
			printFieldAccess((FieldAccessAst) expression);
		} else if (expression instanceof MethodCallAst) {
			printMethodCall((MethodCallAst) expression);
		} else if(expression instanceof ThisValueAst) {
			printThis((ThisValueAst) expression);
		} else if (expression instanceof BinaryOpAst) {
			printBinaryOperator((BinaryOpAst) expression);
		} else if (expression instanceof ParameterValueAst) {
			printParameter((ParameterValueAst) expression);
		} else if (expression instanceof NewInstanceAst) {
			printNewInstance((NewInstanceAst) expression);
		} else if (expression instanceof CastAst) {
			printCast((CastAst) expression);
		} else if (expression instanceof NewArrayAst) {
			printNewArray((NewArrayAst) expression);
		} else if (expression instanceof ArrayAccess) {
			printArrayAccess((ArrayAccess) expression);
		} else if (expression instanceof LocalVariableValueAst) {
			printLocalVariableAccess((LocalVariableValueAst) expression);
		} else if (expression instanceof AssignmentOpAst) {
			printAssignment((AssignmentOpAst) expression);
		} else {
			throw new RuntimeException("Expression: " + expression.getClass().getSimpleName() +
				" is not supported yet!");
		}
	}

	private void printAssignment(AssignmentOpAst assignment) {
		printExpression(assignment.getVariable());
		codeWriter.print(" = ");
		printExpression(assignment.getValue());
	}

	private void printLocalVariableAccess(LocalVariableValueAst localVariableAccess) {
		codeWriter.print(localVariableAccess.getVariable().getName());
	}

	private void printArrayAccess(ArrayAccess arrayAccess) {
		// As program below demonstrates - we need to be careful here
		// String[] a, b;
		// (a = b = new String[3])[4] = "foo";

		ExprAst arrayRef = arrayAccess.getArrayRef();

		if ((arrayRef instanceof LValueAst) ||
				(arrayRef instanceof MethodCallAst) ||
				(arrayRef instanceof NewArrayAst))
		{
			// no parentheses
			printExpression(arrayAccess.getArrayRef());
		} else {
			codeWriter.print("(");
			printExpression(arrayAccess.getArrayRef());
			codeWriter.print(")");
		}

		codeWriter.print("[");
		printExpression(arrayAccess.getIndexExpr());
		codeWriter.print("]");
	}

	private void printNewArray(NewArrayAst newArrayAst) {
		codeWriter.print("new ")
				.print(newArrayAst.getElementType().asSourceCodeString())
				.print("[");

		printExpression(newArrayAst.getSizeExpr());

		codeWriter.print("]");
	}

	private void printCast(CastAst castAst) {
		codeWriter.print("(")
				.print(castAst.getTargetType().asSourceCodeString())
				.print(")");

		ExprAst expr = castAst.getExpr();

		if (shouldParentheseUnaryOperatorExpr(JavaOperator.TYPE_CAST, expr)) {
			codeWriter.print("(");
			printExpression(expr);
			codeWriter.print(")");
		} else {
			printExpression(expr);
		}
	}

	private boolean shouldParentheseUnaryOperatorExpr(JavaOperator operator, ExprAst expr) {
		if (expr instanceof BinaryOp) {
			return true;
		}

		return false;
	}

	private void printNewInstance(NewInstanceAst newInstance) {
		codeWriter.print("new ")
				.print(newInstance.getType().asSourceCodeString())
				.print("()");
	}

	private void printParameter(ParameterValueAst parameter) {
		codeWriter.print(parameter.getParameter().getName());
	}

	private void printBinaryOperator(BinaryOpAst binaryOp) {
		JavaOperator operator = binaryOp.getOperator();

		printWithMaybeParentheses(operator, binaryOp.getLeft());

		codeWriter.print(" ");
		printOperator(operator);
		codeWriter.print(" ");

		printWithMaybeParentheses(operator, binaryOp.getRight());
	}

	private void printWithMaybeParentheses(JavaOperator parentOperator, ExprAst childExpr) {
		if (childExpr instanceof BinaryOpAst) {
			BinaryOpAst childBinaryOp = (BinaryOpAst) childExpr;

			if (childBinaryOp.getOperator().hasLowerPrecedenceThan(parentOperator)) {
				codeWriter.print("(");
				printExpression(childBinaryOp);
				codeWriter.print(")");

				return;
			}

		}

		// non breakable expression - no parentheses necessary
		printExpression(childExpr);
	}

	private void printOperator(JavaOperator operator) {
		switch (operator) {
			case ADDITION:
				codeWriter.print("+");
				break;

			case MULTIPLICATION:
				codeWriter.print("*");
				break;

			case SUBTRACTION:
				codeWriter.print("-");
				break;

			case DIVISION:
				codeWriter.print("/");
				break;

			default:
				throw new RuntimeException("Operator " + operator + " is not supported.");
		}
	}

	private void printThis(ThisValueAst thisExpr) {
		codeWriter.print("this");
	}

	private void printMethodCall(MethodCallAst methodCall) {
		if (methodCall.getThisArgument() != null) {
			printExpression(methodCall.getThisArgument());
		} else {
			codeWriter.print(methodCall.getClassContainingMethod().asSourceCodeString());
		}

		codeWriter
				.print(".")
				.print(methodCall.getMethodName());

		writeList(methodCall.getMethodArguments())
				.before(codeWriter.printAction("("))
				.element((methodArgument, pos) -> {
					printExpression(methodArgument);
				})
				.between(codeWriter.printAction(", "))
				.after(codeWriter.printAction(")"))
				.write();

	}

	private void printFieldAccess(FieldAccessAst fieldAccess) {
		codeWriter.print(
			fieldAccess.getClassContainingField().asSourceCodeString());

		codeWriter.print(".").print(fieldAccess.getFieldName());
	}

	private void printLiteral(LiteralAst expression) {
		if (expression instanceof IntegerLiteral) {
			IntegerLiteral integerLiteral = ((IntegerLiteral) expression);
			codeWriter.print(Integer.toString(integerLiteral.getValue()));
		} else if (expression instanceof DoubleLiteral) {
			DoubleLiteral doubleLiteral = (DoubleLiteral)expression;
			codeWriter.print(Double.toString(doubleLiteral.getValue()));
		} else if (expression instanceof StringLiteral) {
			StringLiteral stringLiteral = ((StringLiteral) expression);
			codeWriter
					.print("\"")
					.print(javaEscape(stringLiteral.getValue()))
					.print("\"");
		} else if (expression instanceof ArrayLiteral) {
			ArrayLiteral arrayLiteral = (ArrayLiteral) expression;

			ListWriter.writeList(arrayLiteral.getElements())
					.before(() -> codeWriter.print("{ "))
					.element((elementAst, position) ->
							new ExpressionSourceCodeFormatter(codeWriter)
									.convertAstToJavaCode(elementAst))
					.between(() -> codeWriter.print(", "))
					.after(() -> codeWriter.print(" }"))
					.write();
		} else {
			throw new RuntimeException("Literal valueType: " + expression.getClass().getSimpleName() +
					" is not yet supported.");
		}
	}
}
