package pl.marcinchwedczuk.cjava.ast.visitor;

import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.CompilationUnitAst;
import pl.marcinchwedczuk.cjava.ast.FieldDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.MethodDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationPropertyAssignmentAst;
import pl.marcinchwedczuk.cjava.ast.expr.*;
import pl.marcinchwedczuk.cjava.ast.expr.literal.ArrayLiteral;
import pl.marcinchwedczuk.cjava.ast.expr.literal.DoubleLiteral;
import pl.marcinchwedczuk.cjava.ast.expr.literal.IntegerLiteral;
import pl.marcinchwedczuk.cjava.ast.expr.literal.StringLiteral;
import pl.marcinchwedczuk.cjava.ast.statement.*;

public class BaseAstMapper implements AstMapper {
	@Override
	public ClassDeclarationAst map(ClassDeclarationAst current, ClassDeclarationAst.Builder mapped) {
		return current;
	}

	@Override
	public FieldDeclarationAst map(FieldDeclarationAst current, FieldDeclarationAst.Builder mapped) {
		return current;
	}

	@Override
	public MethodDeclarationAst map(MethodDeclarationAst current, MethodDeclarationAst.Builder mapped) {
		return current;
	}

	@Override
	public AnnotationAst map(AnnotationAst current, AnnotationAst.Builder mapped) {
		return current;
	}

	@Override
	public AnnotationPropertyAssignmentAst map(AnnotationPropertyAssignmentAst current, AnnotationPropertyAssignmentAst.Builder mapped) {
		return current;
	}

	@Override
	public StatementBlockAst map(StatementBlockAst current, StatementBlockAst.Builder mapped) {
		return current;
	}

	@Override
	public ExprStatementAst map(ExprStatementAst current, ExprStatementAst.Builder mapped) {
		return current;
	}

	@Override
	public ReturnStatementAst map(ReturnStatementAst current) {
		return current;
	}

	@Override
	public ReturnValueStatementAst map(ReturnValueStatementAst current, ReturnValueStatementAst.Builder mapped) {
		return current;
	}

	@Override
	public StatementAst map(ThrowStatementAst current, ThrowStatementAst.Builder mapped) {
		return current;
	}

	@Override
	public VariableDeclarationStatementAst map(VariableDeclarationStatementAst current, VariableDeclarationStatementAst.Builder mapped) {
		return current;
	}

	@Override
	public IntegerLiteral map(IntegerLiteral current) {
		return current;
	}

	@Override
	public DoubleLiteral map(DoubleLiteral current) {
		return current;
	}

	@Override
	public StringLiteral map(StringLiteral current) {
		return current;
	}

	@Override
	public ArrayLiteral map(ArrayLiteral current, ArrayLiteral.Builder mapped) {
		return current;
	}

	@Override
	public LValueAst map(ArrayAccess current, ArrayAccess.Builder mapped) {
		return current;
	}

	@Override
	public ExprAst map(AssignmentOpAst current, AssignmentOpAst.Builder mapped) {
		return current;
	}

	@Override
	public ExprAst map(BinaryOpAst current, BinaryOpAst.Builder mapped) {
		return current;
	}

	@Override
	public ExprAst map(CastAst current, CastAst.Builder mapped) {
		return current;
	}

	@Override
	public ExprAst map(FieldAccessAst current, FieldAccessAst.Builder mapped) {
		return current;
	}

	@Override
	public LValueAst map(LocalVariableValueAst current, LocalVariableValueAst.Builder mapped) {
		return current;
	}

	@Override
	public ExprAst map(MethodCallAst current, MethodCallAst.Builder mapped) {
		return current;
	}

	@Override
	public ExprAst map(NewArrayAst current, NewArrayAst.Builder mapped) {
		return current;
	}

	@Override
	public ExprAst map(NewInstanceAst current, NewInstanceAst.Builder mapped) {
		return current;
	}

	@Override
	public LValueAst map(ParameterValueAst current) {
		return current;
	}

	@Override
	public ExprAst map(ThisValueAst current) {
		return current;
	}

	@Override
	public ExprAst map(UnaryOpAst current, UnaryOpAst.Builder mapped) {
		return current;
	}

	@Override
	public CompilationUnitAst map(CompilationUnitAst current, CompilationUnitAst.Builder mapped) {
		return current;
	}
}
