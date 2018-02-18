package pl.marcinchwedczuk.cjava.decompiler.fixture;

import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.expr.FieldAccessAst;
import pl.marcinchwedczuk.cjava.ast.expr.MethodCallAst;
import pl.marcinchwedczuk.cjava.ast.expr.literal.StringLiteral;
import pl.marcinchwedczuk.cjava.ast.statement.ExprStatementAst;
import pl.marcinchwedczuk.cjava.ast.statement.StatementAst;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;

import java.io.PrintStream;

import static java.util.Arrays.asList;
import static pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType.VOID;

public class AstFixtures {
	/**
	 * Returns AST for:
	 * {@code
	 * 	System.out.println("Hello, world!");
	 * }
	 */
	public static ExprStatementAst createPrintHelloWorldStatement() {
		ExprAst systemOut = FieldAccessAst.create(
				ClassType.of(System.class),
				"out",
				ClassType.of(PrintStream.class));

		ExprAst helloWorldString = StringLiteral.of("Hello, world!");

		ExprStatementAst printlnCall = ExprStatementAst.fromExpr(
				MethodCallAst.create(
						ClassType.of(PrintStream.class), "println",
						MethodSignature.basic(VOID, ClassType.of(String.class)),
						systemOut, asList(helloWorldString)));

		return printlnCall;
	}
}
