package pl.marcinchwedczuk.cjava.ast.annotation;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.Ast;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;

@AutoValue
public abstract class AnnotationPropertyAssignmentAst extends Ast {
	public static AnnotationPropertyAssignmentAst create(String newPropertyName, ExprAst newPropertyValue) {
		return new AutoValue_AnnotationPropertyAssignmentAst.Builder()
				.setPropertyName(newPropertyName)
				.setPropertyValue(newPropertyValue)
				.build();
	}

	public abstract String getPropertyName();
	public abstract ExprAst getPropertyValue();

	public abstract Builder toBuilder();

	@Override
	public AnnotationPropertyAssignmentAst astMap(AstMapper mapper) {
		ExprAst mappedExpr = getPropertyValue().astMap(mapper);

		Builder mapped = this
				.toBuilder()
				.setPropertyValue(mappedExpr);

		return mapper.map(this, mapped);
	}

	@AutoValue.Builder
	public abstract static class Builder {
		public abstract Builder setPropertyName(String newPropertyName);
		public abstract Builder setPropertyValue(ExprAst newPropertyValue);

		public abstract AnnotationPropertyAssignmentAst build();
	}
}
