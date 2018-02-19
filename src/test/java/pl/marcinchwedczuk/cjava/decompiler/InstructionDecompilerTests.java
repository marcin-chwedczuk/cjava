package pl.marcinchwedczuk.cjava.decompiler;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.expr.*;
import pl.marcinchwedczuk.cjava.ast.expr.literal.IntegerLiteral;
import pl.marcinchwedczuk.cjava.ast.expr.literal.StringLiteral;
import pl.marcinchwedczuk.cjava.ast.statement.ExprStatementAst;
import pl.marcinchwedczuk.cjava.ast.statement.ReturnValueStatementAst;
import pl.marcinchwedczuk.cjava.ast.statement.StatementBlockAst;
import pl.marcinchwedczuk.cjava.ast.statement.VariableDeclarationStatementAst;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_Expressions;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_HelloWorld;
import pl.marcinchwedczuk.cjava.decompiler.fixture.AstBuilder;
import pl.marcinchwedczuk.cjava.decompiler.fixture.AstFixtures;
import pl.marcinchwedczuk.cjava.decompiler.signature.LocalVariable;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ArrayType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static pl.marcinchwedczuk.cjava.ast.expr.JavaOperator.*;
import static pl.marcinchwedczuk.cjava.decompiler.fixture.AstBuilder.*;


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
		ExprAst expected = binOp(ADDITION,
				binOp(MULTIPLICATION,
						binOp(ADDITION,
								binOp(ADDITION,
										intParam("arg0"),
										intParam("arg1")),
								intParam("arg2")),
						intParam("arg1")),

				binOp(DIVISION,
						binOp(MULTIPLICATION,
								intParam("arg2"),
								binOp(SUBTRACTION,
										binOp(SUBTRACTION,
												intParam("arg0"), intParam("arg1")),
										intParam("arg2"))),
						binOp(ADDITION,
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
		ExprAst expected = binOp(DIVISION,
				mathMethod("cos",
						binOp(MULTIPLICATION,
								doubleParam("arg1"),
								mathMethod("sin",
										binOp(ADDITION,
												newRandomNextDouble(),
												doubleParam("arg0"))))
						),
				mathMethod("atan2",
						doubleParam("arg0"),
						doubleParam("arg1")));

		assertThat(expr).isEqualTo(expected);
	}

	@Test
	public void canDecompileArrayCreation() throws Exception {
		StatementBlockAst methodBody =
				getMethodBody(Fixture_Expressions.class, "createArray");

		// var var0 = new java.lang.String[2];
		VariableDeclarationStatementAst var0Declaration =
				(VariableDeclarationStatementAst) methodBody.getStatement(0);

		LocalVariable var0 = var(stringArray(), "var0");

		VariableDeclarationStatementAst expectedVar0Declaration =
				VariableDeclarationStatementAst.create(
						var0,
						NewArrayAst.create(string(), integer(2)));

		assertThat(var0Declaration).isEqualTo(expectedVar0Declaration);

		// var0[0] = "foo";
		ExprStatementAst assignment1 = (ExprStatementAst) methodBody.getStatement(1);

		ExprStatementAst expectedAssignment1 = ExprStatementAst.fromExpr(
				AssignmentOpAst.create(
						ArrayAccess.create(accessVar(var0), integer(0)),
						string("foo")));

		assertThat(assignment1).isEqualTo(expectedAssignment1);

		// var0[1] = "bar"; - we don't test this

		// return var0;
		ReturnValueStatementAst returnVar0 = (ReturnValueStatementAst) methodBody.getStatement(3);

		ReturnValueStatementAst expectedReturnVar0 = ReturnValueStatementAst.create(
				accessVar(var0));

		assertThat(returnVar0).isEqualTo(expectedReturnVar0);
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