package pl.marcinchwedczuk.cjava.ast.annotation;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static pl.marcinchwedczuk.cjava.util.ListUtils.readOnlyCopy;

@AutoValue
public abstract class AnnotationAst {
	public static AnnotationAst create(JavaType annotationType,
									   List<AnnotationPropertyAssignmentAst> elementValuePairs) {
		return new AutoValue_AnnotationAst(
				annotationType, ImmutableList.copyOf(elementValuePairs));
	}

	public static AnnotationAst create(JavaType annotationType) {
		return create(annotationType, emptyList());
	}

	public abstract JavaType getAnnotationType();
	public abstract ImmutableList<AnnotationPropertyAssignmentAst> getPropertiesAssignments();

	public boolean hasPropertiesAssignments() {
		return !getPropertiesAssignments().isEmpty();
	}
}
