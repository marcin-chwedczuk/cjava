package pl.marcinchwedczuk.cjava.ast.annotation;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.ast.Ast;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static pl.marcinchwedczuk.cjava.util.ListUtils.readOnlyCopy;

@AutoValue
public abstract class AnnotationAst extends Ast {
	public static AnnotationAst create(JavaType newAnnotationType, List<AnnotationPropertyAssignmentAst> newPropertiesAssignments) {
		return new AutoValue_AnnotationAst.Builder()
				.setAnnotationType(newAnnotationType)
				.setPropertiesAssignments(newPropertiesAssignments)
				.build();
	}

	public static AnnotationAst create(JavaType annotationType) {
		return create(annotationType, emptyList());
	}

	public abstract JavaType getAnnotationType();
	public abstract ImmutableList<AnnotationPropertyAssignmentAst> getPropertiesAssignments();

	public abstract Builder toBuilder();

	@Override
	public AnnotationAst astMap(AstMapper mapper) {
		List<AnnotationPropertyAssignmentAst> mappedAssignments = getPropertiesAssignments().stream()
				.map(a -> a.astMap(mapper))
				.collect(toList());

		Builder mapped = this
				.toBuilder()
				.setPropertiesAssignments(mappedAssignments);

		return mapper.map(this, mapped);
	}

	public boolean hasPropertiesAssignments() {
		return !getPropertiesAssignments().isEmpty();
	}

	@AutoValue.Builder
	public abstract static class Builder {
		public abstract Builder setAnnotationType(JavaType newAnnotationType);
		public abstract Builder setPropertiesAssignments(List<AnnotationPropertyAssignmentAst> newPropertiesAssignments);

		public abstract AnnotationAst build();
	}
}
