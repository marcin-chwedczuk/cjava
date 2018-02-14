package pl.marcinchwedczuk.cjava.ast;

import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.bytecode.attribute.RuntimeVisibleAnnotationsAttribute;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaType;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;

public class FieldDeclarationAst {
	private final JavaType fieldType;
	private final String fieldName;
	private Visibility visibility;
	private boolean isFinal;
	private boolean isStatic;
	private boolean isTransient;
	private boolean isVolatile;

	private List<AnnotationAst> annotations = emptyList();

	public FieldDeclarationAst(JavaType fieldType, String fieldName) {
		this.fieldType = requireNonNull(fieldType);
		this.fieldName = requireNonNull(fieldName);
	}

	public JavaType getFieldType() {
		return fieldType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(boolean aFinal) {
		isFinal = aFinal;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean aStatic) {
		isStatic = aStatic;
	}

	public boolean isTransient() {
		return isTransient;
	}

	public void setTransient(boolean aTransient) {
		isTransient = aTransient;
	}

	public boolean isVolatile() {
		return isVolatile;
	}

	public void setVolatile(boolean aVolatile) {
		isVolatile = aVolatile;
	}

	public List<AnnotationAst> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<AnnotationAst> annotations) {
		this.annotations = annotations;
	}
}
