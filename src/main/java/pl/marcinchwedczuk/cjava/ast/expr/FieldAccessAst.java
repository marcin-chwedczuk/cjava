package pl.marcinchwedczuk.cjava.ast.expr;

import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaType;

public class FieldAccessAst extends ExprAst {
	private final ClassType classContainingField;
	private final String fieldName;
	private final JavaType fieldType;

	public FieldAccessAst(ClassType classContainingField, String fieldName, JavaType fieldType) {
		this.classContainingField = classContainingField;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
	}
}
