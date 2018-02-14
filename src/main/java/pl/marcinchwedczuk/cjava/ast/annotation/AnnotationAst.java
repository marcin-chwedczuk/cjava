package pl.marcinchwedczuk.cjava.ast.annotation;

import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaType;

import java.util.List;
import java.util.Objects;

import static pl.marcinchwedczuk.cjava.util.ListUtils.readOnlyCopy;

public class AnnotationAst {
	private final JavaType annotationType;
	private final List<AnnotationPropertyAssignmentAst> elementValuePairs;

	public AnnotationAst(JavaType annotationType, List<AnnotationPropertyAssignmentAst> elementValuePairs) {
		this.annotationType = Objects.requireNonNull(annotationType);
		this.elementValuePairs = readOnlyCopy(elementValuePairs);
	}

	public boolean hasPropertiesAssignments() {
		return !elementValuePairs.isEmpty();
	}

	public JavaType getAnnotationType() {
		return annotationType;
	}

	public List<AnnotationPropertyAssignmentAst> getPropertiesAssignments() {
		return elementValuePairs;
	}
}
