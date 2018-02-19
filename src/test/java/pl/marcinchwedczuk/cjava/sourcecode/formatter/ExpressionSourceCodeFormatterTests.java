package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.expr.*;
import pl.marcinchwedczuk.cjava.decompiler.fixture.AstBuilder;
import pl.marcinchwedczuk.cjava.decompiler.signature.LocalVariable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static pl.marcinchwedczuk.cjava.ast.expr.JavaOperator.*;
import static pl.marcinchwedczuk.cjava.decompiler.fixture.AstBuilder.*;

public class ExpressionSourceCodeFormatterTests {

	@Test
	public void canFormatBinaryOperations() throws Exception {
		// (a + b + c) * b
		ExprAst expr =
				binOp(MULTIPLICATION,
						binOp(ADDITION,
								binOp(ADDITION,
										intParam("a"),
										intParam("b")),
								intParam("c")),
						intParam("b"));

		String sourceCode = format(expr);

		assertThat(sourceCode)
				.isEqualTo("(a + b + c) * b");
	}

	@Test
	public void canFormatNewInstruction() throws Exception {
		// new java.util.Random().nextDouble()
		MethodCallAst newRandomNextDouble =
				AstBuilder.newRandomNextDouble();

		String sourceCode = format(newRandomNextDouble);

		assertThat(sourceCode)
				.isEqualTo("new java.util.Random().nextDouble()");
	}

	@Test
	public void canFormatNewArray() throws Exception {
		NewArrayAst newArray = NewArrayAst.create(string(), integer(2));
		String sourceCode = format(newArray);
		assertThat(sourceCode).isEqualTo("new java.lang.String[2]");

		NewArrayAst newPrimitiveArray = NewArrayAst.create(integer(), integer(123));
		sourceCode = format(newPrimitiveArray);
		assertThat(sourceCode).isEqualTo("new int[123]");
	}

	@Test
	public void canFormatArrayAccess() throws Exception {
		// string[] var0;
		// var0[0] = "foo";
		LocalVariable var0 = var(stringArray(), "var0");
		ArrayAccess arrayAccess =
				ArrayAccess.create(accessVar(var0), integer(0));
		AssignmentOpAst assignment =
				AssignmentOpAst.create(arrayAccess, string("foo"));

		String sourceCode = format(assignment);

		assertThat(sourceCode)
				.isEqualTo("var0[0] = \"foo\"");
	}

	@Test
	public void canFormatCast() throws Exception {
		CastAst castAst = castToInt(doubleParam("aDouble"));

		String sourceCode = format(castAst);

		assertThat(sourceCode)
				.isEqualTo("(int)aDouble");
	}

	@Test
	public void canFormatCastWithComplexExpression() throws Exception {
		CastAst castAst = castToInt(
				binOp(ADDITION,
					doubleParam("aDouble"),
					intParam("anInt")));

		String sourceCode = format(castAst);

		assertThat(sourceCode)
				.isEqualTo("(int)(aDouble + anInt)");
	}

	private static String format(ExprAst expr) {
		JavaCodeWriter codeWriter = new JavaCodeWriter();

		new ExpressionSourceCodeFormatter(codeWriter)
				.convertAstToJavaCode(expr);

		return codeWriter.dumpSourceCode();
	}
}