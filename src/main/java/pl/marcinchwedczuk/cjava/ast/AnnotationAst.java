package pl.marcinchwedczuk.cjava.ast;

import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaType;

public class AnnotationAst {
	private JavaType annotationType;

	public JavaType getAnnotationType() {
		return annotationType;
	}

	public void setAnnotationType(JavaType annotationType) {
		this.annotationType = annotationType;
	}
}
