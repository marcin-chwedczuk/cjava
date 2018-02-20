package pl.marcinchwedczuk.cjava.ast.visitor;

import pl.marcinchwedczuk.cjava.ast.*;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationPropertyAssignmentAst;
import pl.marcinchwedczuk.cjava.ast.expr.*;
import pl.marcinchwedczuk.cjava.ast.expr.literal.ArrayLiteral;
import pl.marcinchwedczuk.cjava.ast.expr.literal.DoubleLiteral;
import pl.marcinchwedczuk.cjava.ast.expr.literal.IntegerLiteral;
import pl.marcinchwedczuk.cjava.ast.expr.literal.StringLiteral;
import pl.marcinchwedczuk.cjava.ast.statement.*;

public interface AstMapper {
	ClassDeclarationAst map(ClassDeclarationAst current, ClassDeclarationAst.Builder mapped);

	FieldDeclarationAst map(FieldDeclarationAst current, FieldDeclarationAst.Builder mapped);
	MethodDeclarationAst map(MethodDeclarationAst current, MethodDeclarationAst.Builder mapped);

	AnnotationAst map(AnnotationAst current, AnnotationAst.Builder mapped);
	AnnotationPropertyAssignmentAst map(AnnotationPropertyAssignmentAst current, AnnotationPropertyAssignmentAst.Builder mapped);

	StatementBlockAst map(StatementBlockAst current, StatementBlockAst.Builder mapped);
	ExprStatementAst map(ExprStatementAst current, ExprStatementAst.Builder mapped);
	ReturnStatementAst map(ReturnStatementAst current);
	ReturnValueStatementAst map(ReturnValueStatementAst current, ReturnValueStatementAst.Builder mapped);
	StatementAst map(ThrowStatementAst current, ThrowStatementAst.Builder mapped);

	VariableDeclarationStatementAst map(VariableDeclarationStatementAst current, VariableDeclarationStatementAst.Builder mapped);
	IntegerLiteral map(IntegerLiteral current);
	DoubleLiteral map(DoubleLiteral current);

	StringLiteral map(StringLiteral current);
	ArrayLiteral map(ArrayLiteral current, ArrayLiteral.Builder mapped);
	LValueAst map(ArrayAccess current, ArrayAccess.Builder mapped);
	ExprAst map(AssignmentOpAst current, AssignmentOpAst.Builder mapped);
	ExprAst map(BinaryOpAst current, BinaryOpAst.Builder mapped);
	ExprAst map(CastAst current, CastAst.Builder mapped);
	ExprAst map(FieldAccessAst current, FieldAccessAst.Builder mapped);
	LValueAst map(LocalVariableValueAst current, LocalVariableValueAst.Builder mapped);
	ExprAst map(MethodCallAst current, MethodCallAst.Builder mapped);
	ExprAst map(NewArrayAst current, NewArrayAst.Builder mapped);
	ExprAst map(NewInstanceAst current, NewInstanceAst.Builder mapped);
	LValueAst map(ParameterValueAst current);
	ExprAst map(ThisValueAst current);
	ExprAst map(UnaryOpAst current, UnaryOpAst.Builder mapped);

	CompilationUnitAst map(CompilationUnitAst current, CompilationUnitAst.Builder mapped)	;
}
