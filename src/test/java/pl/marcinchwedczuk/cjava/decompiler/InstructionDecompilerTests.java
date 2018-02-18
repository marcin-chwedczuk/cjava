package pl.marcinchwedczuk.cjava.decompiler;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.expr.*;
import pl.marcinchwedczuk.cjava.ast.statement.ExprStatementAst;
import pl.marcinchwedczuk.cjava.ast.statement.ReturnValueStatementAst;
import pl.marcinchwedczuk.cjava.ast.statement.StatementAst;
import pl.marcinchwedczuk.cjava.ast.statement.StatementBlockAst;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_Expressions;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_HelloWorld;
import pl.marcinchwedczuk.cjava.decompiler.fixture.AstFixtures;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType;

import java.io.IOException;
import java.util.Collections;
import java.util.Random;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static pl.marcinchwedczuk.cjava.ast.expr.BinaryOperator.*;
import static pl.marcinchwedczuk.cjava.decompiler.fixture.AstBuilder.*;
import static pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType.DOUBLE;
import static pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType.INT;
import static pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType.VOID;


public class InstructionDecompilerTests extends BaseDecompilerTests {

	@Test
	public void canDecompileHelloWorld() throws Exception {
		StatementBlockAst methodBody =
				getMethodBody(Fixture_HelloWorld.class, "main");

		ExprStatementAst methodCall =
				AstFixtures.createPrintHelloWorldStatement();

		assertThat(methodBody.getStatement(0))
				.isEqualTo(methodCall);
	}

	@Test
	public void canDecompileIntegerArithmeticalExpr() throws Exception {
		StatementBlockAst integerArithmeticBody =
				getMethodBody(Fixture_Expressions.class, "integerArithmentic");

		ExprAst expr =
				((ReturnValueStatementAst) integerArithmeticBody.getStatement(0))
					.getValue();

		// expression: (a+b+c)*b + c*(a-b-c)/(a+b);
		ExprAst expected = binOp(ADD,
				binOp(MULTIPLY,
						binOp(ADD,
								binOp(ADD,
										intParam("arg0"),
										intParam("arg1")),
								intParam("arg2")),
						intParam("arg1")),

				binOp(DIVIDE,
						binOp(MULTIPLY,
								intParam("arg2"),
								binOp(SUBTRACT,
										binOp(SUBTRACT,
												intParam("arg0"), intParam("arg1")),
										intParam("arg2"))),
						binOp(ADD,
								intParam("arg0"),
								intParam("arg1"))));

		assertThat(expr).isEqualTo(expected);
	}

	@Test
	public void canDecompileArithmeticWithMethodCalls() throws Exception {
		StatementBlockAst integerArithmeticBody =
				getMethodBody(Fixture_Expressions.class, "doubleArithmeticWithMethodCalls");

		ExprAst expr =
				((ReturnValueStatementAst) integerArithmeticBody.getStatement(0))
						.getValue();

		// double doubleArithmeticWithMethodCalls(double a, double b)
		// Math.cos(b * Math.sin(new Random().nextDouble() + a)) / Math.atan2(a, b)
		ExprAst expected = binOp(DIVIDE,
				mathMethod("cos",
						binOp(MULTIPLY,
								doubleParam("arg1"),
								mathMethod("sin",
										binOp(ADD,
												newRandomNextDouble(),
												doubleParam("arg0"))))
						),
				mathMethod("atan2",
						doubleParam("arg0"),
						doubleParam("arg1")));

		assertThat(expr).isEqualTo(expected);
	}

	private StatementBlockAst getMethodBody(Class<?> klass, String methodName) throws IOException {
		ClassDeclarationAst classDeclAst = decompile(klass);

		StatementBlockAst methodBody =
				findMethodByName(classDeclAst, methodName).getMethodBody();

		assertThat(methodBody)
				.as("Body of method '" + methodName + "'")
				.isNotNull();

		return methodBody;
	}
}