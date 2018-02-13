package pl.marcinchwedczuk.cjava.ast.annotation;

import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;

public class AnnotationPropertyAssignmentAst {
	private final String propertyName;
	private final ExprAst propertyValue;

	public AnnotationPropertyAssignmentAst(String propertyName, ExprAst propertyValue) {
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public ExprAst getPropertyValue() {
		return propertyValue;
	}
}
