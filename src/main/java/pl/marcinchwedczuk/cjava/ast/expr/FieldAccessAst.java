package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class FieldAccessAst extends ExprAst {
	public static FieldAccessAst create(
			ClassType classContainingField, String fieldName, JavaType fieldType) {

		return new AutoValue_FieldAccessAst(classContainingField, fieldName, fieldType);
	}

	public abstract ClassType getClassContainingField();
	public abstract String getFieldName();
	public abstract JavaType getFieldType();
}
