package pl.marcinchwedczuk.cjava.ast;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.decompiler.signature.TypeParameter;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static pl.marcinchwedczuk.cjava.util.ListUtils.readOnlyCopy;

@AutoValue
public abstract class ClassDeclarationAst extends TypeDeclarationAst {

	public static Builder builder() {
		Builder builder = new AutoValue_ClassDeclarationAst.Builder();

		// Set default values
		builder
				.setAbstract(false)
				.setFinal(false)
				.setVisibility(Visibility.PACKAGE)

				.setFields(ImmutableList.of())
				.setMethods(ImmutableList.of())
				.setAnnotations(ImmutableList.of());

		return builder;
	}

	public abstract List<AnnotationAst> getAnnotations();

	public abstract Visibility getVisibility();
	public abstract boolean isAbstract();
	public abstract boolean isFinal();

	public abstract ClassType getClassName();
	public abstract List<TypeParameter> getTypeParameters();

	public abstract JavaType getSuperClass();
	public abstract List<JavaType> getImplementedInterfaces();

	public abstract List<FieldDeclarationAst> getFields();
	public abstract List<MethodDeclarationAst> getMethods();

	public boolean isGenericClassDeclaration() {
		return !getTypeParameters().isEmpty();
	}

	public boolean isImplementingInterfaces() {
		return !getImplementedInterfaces().isEmpty();
	}

	@AutoValue.Builder
	public abstract static class Builder {
		public abstract Builder setAnnotations(List<AnnotationAst> annotations);

		public abstract Builder setVisibility(Visibility visibility);

		public abstract Builder setAbstract(boolean anAbstract);
		public abstract Builder setFinal(boolean aFinal);

		public abstract Builder setClassName(ClassType className);
		public abstract Builder setTypeParameters(List<TypeParameter> typeParameters);

		public abstract Builder setSuperClass(JavaType superClass);
		public abstract Builder setImplementedInterfaces(List<JavaType> implementedInterfaces);

		public abstract Builder setFields(List<FieldDeclarationAst> fields);
		public abstract Builder setMethods(List<MethodDeclarationAst> methods);

		public abstract ClassDeclarationAst build();
	}
}
