package pl.marcinchwedczuk.cjava.decompiler;

import org.junit.Before;
import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.expr.FieldAccessAst;
import pl.marcinchwedczuk.cjava.ast.expr.MethodCallAst;
import pl.marcinchwedczuk.cjava.ast.expr.literal.StringLiteral;
import pl.marcinchwedczuk.cjava.ast.statement.ExprStatementAst;
import pl.marcinchwedczuk.cjava.ast.statement.StatementBlockAst;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_HelloWorld;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;

import java.io.PrintStream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType.VOID;


public class InstructionDecompilerTests extends BaseDecompilerTests {

	private ClassDeclarationAst classDeclAst;

	@Before
	public void setUp() throws Exception {
		classDeclAst = decompile(Fixture_HelloWorld.class);
	}

	@Test
	public void canDecompileHelloWorld() throws Exception {
		StatementBlockAst methodBody =
				findMethodByName(classDeclAst, "main").getMethodBody();

		assertThat(methodBody)
				.isNotNull();

		ExprAst thisExpr = FieldAccessAst.create(
				ClassType.of(System.class),
				"out",
				ClassType.of(PrintStream.class));

		ExprAst helloWorld = StringLiteral.of("Hello, world!");

		ExprStatementAst methodCall = ExprStatementAst.fromExpr(
				MethodCallAst.create(
						ClassType.of(PrintStream.class), "println",
						MethodSignature.basic(VOID, ClassType.of(String.class)),
						thisExpr, asList(helloWorld)));

		assertThat(methodBody.getStatement(0))
				.isEqualTo(methodCall);
	}
}