package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.expr.literal.ArrayLiteral;
import pl.marcinchwedczuk.cjava.ast.expr.literal.IntegerLiteral;
import pl.marcinchwedczuk.cjava.ast.expr.literal.LiteralAst;
import pl.marcinchwedczuk.cjava.ast.expr.literal.StringLiteral;

import static pl.marcinchwedczuk.cjava.sourcecode.formatter.JavaLiteralUtil.javaEscape;

public class ExpressionFormatter implements SourceCodeFormatter {
	private final ExprAst exprAst;
	private final JavaCodeWriter codeWriter;

	public ExpressionFormatter(ExprAst exprAst, JavaCodeWriter codeWriter) {
		this.exprAst = exprAst;
		this.codeWriter = codeWriter;
	}

	@Override
	public void convertAstToJavaCode() {
		if (exprAst instanceof LiteralAst) {
			writeLiteral();
		} else {
			throw new AssertionError("Cannot format expression of type: " +
					exprAst.getClass().getSimpleName());
		}
	}

	private void writeLiteral() {
		if (exprAst instanceof IntegerLiteral) {
			IntegerLiteral integerLiteral = ((IntegerLiteral) exprAst);
			codeWriter.print(Integer.toString(integerLiteral.getValue()));
		} else if (exprAst instanceof StringLiteral) {
			StringLiteral stringLiteral = ((StringLiteral) exprAst);
			codeWriter
					.print("\"")
					.print(javaEscape(stringLiteral.getValue()))
					.print("\"");
		} else if (exprAst instanceof ArrayLiteral) {
			ArrayLiteral arrayLiteral = (ArrayLiteral) this.exprAst;

			ListWriter.writeList(arrayLiteral.getElements())
					.before(() -> codeWriter.print("{ "))
					.element((elementAst, position) ->
						new ExpressionFormatter(elementAst, codeWriter)
								.convertAstToJavaCode())
					.between(() -> codeWriter.print(", "))
					.after(() -> codeWriter.print(" }"))
					.write();
		} else {
			throw new AssertionError("Literal not supported!");
		}

	}
}
