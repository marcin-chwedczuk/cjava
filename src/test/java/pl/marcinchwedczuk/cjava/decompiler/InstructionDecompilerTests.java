package pl.marcinchwedczuk.cjava.decompiler;

import org.junit.Before;
import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.expr.MethodCallAst;
import pl.marcinchwedczuk.cjava.ast.statement.ExprStatementAst;
import pl.marcinchwedczuk.cjava.ast.statement.StatementBlockAst;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithThreeFields;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_HelloWorld;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


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

		assertThat(methodBody.getStatement(0))
				.isInstanceOf(ExprStatementAst.class);
	}
}