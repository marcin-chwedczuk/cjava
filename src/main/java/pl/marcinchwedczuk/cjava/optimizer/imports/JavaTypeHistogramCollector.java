package pl.marcinchwedczuk.cjava.optimizer.imports;

import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
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
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.ast.visitor.BaseAstMapper;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.signature.TypeParameter;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class JavaTypeHistogramCollector extends BaseAstMapper {
	private final JavaTypeHistogram histogram;

	public JavaTypeHistogramCollector(JavaTypeHistogram histogram) {
		this.histogram = requireNonNull(histogram);
	}

	@Override
	public ClassDeclarationAst map(ClassDeclarationAst current, ClassDeclarationAst.Builder mapped) {
		histogram.addUsage(current.getSuperClass());

		current
			.getImplementedInterfaces()
			.forEach(histogram::addUsage);

		addTypeParameters(current.getTypeParameters());

		return current;
	}


	@Override
	public FieldDeclarationAst map(FieldDeclarationAst current, FieldDeclarationAst.Builder mapped) {
		histogram.addUsage(current.getFieldType());

		return current;
	}

	@Override
	public MethodDeclarationAst map(MethodDeclarationAst current, MethodDeclarationAst.Builder mapped) {
		MethodSignature methodSignature = current.getMethodSignature();

		histogram.addUsage(methodSignature.getReturnType());

		methodSignature
				.getParametersTypes()
				.forEach(histogram::addUsage);

		addTypeParameters(methodSignature.getTypeParameters());

		return current;
	}

	@Override
	public AnnotationAst map(AnnotationAst current, AnnotationAst.Builder mapped) {
		histogram.addUsage(current.getAnnotationType());

		return current;
	}

	@Override
	public VariableDeclarationStatementAst map(VariableDeclarationStatementAst current, VariableDeclarationStatementAst.Builder mapped) {
		histogram.addUsage(current.getVariable().getType());

		return current;
	}

	@Override
	public ArrayLiteral map(ArrayLiteral current, ArrayLiteral.Builder mapped) {
		// TODO: Check later
		histogram.addUsage(current.getResultType());

		return current;
	}

	@Override
	public ExprAst map(CastAst current, CastAst.Builder mapped) {
		histogram.addUsage(current.getTargetType());

		return current;
	}

	@Override
	public ExprAst map(NewArrayAst current, NewArrayAst.Builder mapped) {
		histogram.addUsage(current.getElementType());

		return current;
	}

	@Override
	public ExprAst map(NewInstanceAst current, NewInstanceAst.Builder mapped) {
		histogram.addUsage(current.getType());

		return current;
	}

	@Override
	public ExprAst map(FieldAccessAst current, FieldAccessAst.Builder mapped) {
		histogram.addUsage(current.getClassContainingField());

		return current;
	}

	@Override
	public ExprAst map(MethodCallAst current, MethodCallAst.Builder mapped) {
		if (current.getThisArgument() == null) {
			// static call
			histogram.addUsage(current.getClassContainingMethod());
		}

		return current;
	}


	private void addTypeParameters(List<TypeParameter> typeParameters) {
		typeParameters
				.forEach(p -> {
					if (p.getClassBound() != null) {
						histogram.addUsage(p.getClassBound());
					}

					p.getInterfaceBounds()
							.forEach(histogram::addUsage);
				});
	}
}
