package pl.marcinchwedczuk.cjava.ast.annotation;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;

@AutoValue
public abstract class AnnotationPropertyAssignmentAst {
	public static AnnotationPropertyAssignmentAst create(String propertyName, ExprAst propertyValue) {
		return new AutoValue_AnnotationPropertyAssignmentAst(propertyName, propertyValue);
	}

	public abstract String getPropertyName();
	public abstract ExprAst getPropertyValue();
}
