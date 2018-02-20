package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class FieldAccessAst extends ExprAst {
	public static FieldAccessAst create(
			ClassType classContainingField, String fieldName, JavaType fieldType) {

		return new AutoValue_FieldAccessAst.Builder()
				.setClassContainingField(classContainingField)
				.setFieldName(fieldName)
				.setFieldType(fieldType)
				.build();
	}

	public abstract ClassType getClassContainingField();
	public abstract String getFieldName();
	public abstract JavaType getFieldType();

	public abstract Builder toBuilder();

	@Override
	public JavaType getResultType() {
		return getFieldType();
	}

	@Override
	public ExprAst astMap(AstMapper mapper) {
		Builder mapped = this.toBuilder();
		return mapper.map(this, mapped);
	}

	@AutoValue.Builder
	public static abstract class Builder {
		public abstract Builder setClassContainingField(ClassType class_);
		public abstract Builder setFieldName(String name);
		public abstract Builder setFieldType(JavaType type);

		public abstract FieldAccessAst build();
	}
}
