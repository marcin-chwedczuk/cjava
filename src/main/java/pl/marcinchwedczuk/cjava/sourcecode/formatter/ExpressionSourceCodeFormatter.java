package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.ast.FieldDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.expr.FieldAccessAst;
import pl.marcinchwedczuk.cjava.ast.expr.MethodCallAst;
import pl.marcinchwedczuk.cjava.ast.expr.ThisValueAst;
import pl.marcinchwedczuk.cjava.ast.expr.literal.ArrayLiteral;
import pl.marcinchwedczuk.cjava.ast.expr.literal.IntegerLiteral;
import pl.marcinchwedczuk.cjava.ast.expr.literal.LiteralAst;
import pl.marcinchwedczuk.cjava.ast.expr.literal.StringLiteral;

import java.util.Objects;

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
			printThis((ThisValueAst)expression);
		} else {
			throw new RuntimeException("Expression: " + expression.getClass().getSimpleName() +
				" is not supported yet!");
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
