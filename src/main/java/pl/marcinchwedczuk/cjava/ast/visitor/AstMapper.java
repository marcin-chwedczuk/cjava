package pl.marcinchwedczuk.cjava.ast.visitor;

import pl.marcinchwedczuk.cjava.ast.Ast;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.FieldDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.MethodDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationPropertyAssignmentAst;
import pl.marcinchwedczuk.cjava.ast.expr.ArrayAccess;
import pl.marcinchwedczuk.cjava.ast.expr.AssignmentOpAst;
import pl.marcinchwedczuk.cjava.ast.expr.BinaryOpAst;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.expr.literal.ArrayLiteral;
import pl.marcinchwedczuk.cjava.ast.expr.literal.IntegerLiteral;
import pl.marcinchwedczuk.cjava.ast.expr.literal.StringLiteral;
import pl.marcinchwedczuk.cjava.ast.statement.*;

public interface AstMapper {
	ClassDeclarationAst map(ClassDeclarationAst current, ClassDeclarationAst.Builder mapped);

	FieldDeclarationAst map(FieldDeclarationAst fieldDeclaration, FieldDeclarationAst.Builder mapped);
	MethodDeclarationAst map(MethodDeclarationAst methodDeclaration, MethodDeclarationAst.Builder mapped);

	AnnotationAst map(AnnotationAst current, AnnotationAst.Builder mapped);
	AnnotationPropertyAssignmentAst map(AnnotationPropertyAssignmentAst current, AnnotationPropertyAssignmentAst.Builder mapped);

	StatementBlockAst map(StatementBlockAst current, StatementBlockAst.Builder mapped);
	ExprStatementAst map(ExprStatementAst current, ExprStatementAst.Builder mapped);
	ReturnStatementAst map(ReturnStatementAst current);
	ReturnValueStatementAst map(ReturnValueStatementAst current, ReturnValueStatementAst.Builder mapped);
	VariableDeclarationStatementAst map(VariableDeclarationStatementAst current, VariableDeclarationStatementAst.Builder mapped);

	IntegerLiteral map(IntegerLiteral current);
	StringLiteral map(StringLiteral current);
	ArrayLiteral map(ArrayLiteral current, ArrayLiteral.Builder mapped);

	ArrayAccess map(ArrayAccess current, ArrayAccess.Builder mapped);
	AssignmentOpAst map(AssignmentOpAst current, AssignmentOpAst.Builder mapped);
	BinaryOpAst map(BinaryOpAst current, BinaryOpAst.Builder mapped);
}
