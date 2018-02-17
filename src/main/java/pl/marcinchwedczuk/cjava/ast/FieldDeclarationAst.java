package pl.marcinchwedczuk.cjava.ast;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;

@AutoValue
public abstract class FieldDeclarationAst {
	public static FieldDeclarationAst.Builder builder(JavaType fieldType, String fieldName) {
		AutoValue_FieldDeclarationAst.Builder builder =
				new AutoValue_FieldDeclarationAst.Builder();

		builder.setFieldType(fieldType)
				.setFieldName(fieldName)

				// set default values
				.setVisibility(Visibility.PACKAGE)
				.setFinal(false)
				.setStatic(false)
				.setVolatile(false)
				.setTransient(false)
				.setAnnotations(ImmutableList.of());

		return builder;
	}

	public abstract Visibility getVisibility();
	public abstract JavaType getFieldType();
	public abstract String getFieldName();

	public abstract boolean isFinal();
	public abstract boolean isStatic();
	public abstract boolean isTransient();
	public abstract boolean isVolatile();

	public abstract ImmutableList<AnnotationAst> getAnnotations();

	@AutoValue.Builder
	public abstract static class Builder {
		public abstract Builder setVisibility(Visibility visibility);
		public abstract Builder setFieldType(JavaType fieldType);
		public abstract Builder setFieldName(String fieldName);

		public abstract Builder setFinal(boolean aFinal);
		public abstract Builder setStatic(boolean aStatic);
		public abstract Builder setTransient(boolean aTransient);
		public abstract Builder setVolatile(boolean aVolatile);

		public abstract Builder setAnnotations(AnnotationAst... annotations);
		public abstract Builder setAnnotations(List<AnnotationAst> annotations);

		public abstract FieldDeclarationAst build();
	}
}
