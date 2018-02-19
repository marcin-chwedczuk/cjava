package pl.marcinchwedczuk.cjava.ast;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.decompiler.signature.TypeParameter;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static pl.marcinchwedczuk.cjava.util.ListUtils.readOnlyCopy;

@AutoValue
public abstract class ClassDeclarationAst extends TypeDeclarationAst {
	public static ClassDeclarationAst create(List<AnnotationAst> newAnnotations, Visibility newVisibility, boolean newAbstract, boolean newFinal, ClassType newClassName, ImmutableList<TypeParameter> newTypeParameters, JavaType newSuperClass, ImmutableList<JavaType> newImplementedInterfaces, ImmutableList<FieldDeclarationAst> newFields, ImmutableList<MethodDeclarationAst> newMethods) {
		return builder()
				.setAnnotations(newAnnotations)
				.setVisibility(newVisibility)
				.setAbstract(newAbstract)
				.setFinal(newFinal)
				.setClassName(newClassName)
				.setTypeParameters(newTypeParameters)
				.setSuperClass(newSuperClass)
				.setImplementedInterfaces(newImplementedInterfaces)
				.setFields(newFields)
				.setMethods(newMethods)
				.build();
	}

	public abstract List<AnnotationAst> getAnnotations();

	public abstract Visibility getVisibility();
	public abstract boolean isAbstract();
	public abstract boolean isFinal();

	public abstract ClassType getClassName();
	public abstract ImmutableList<TypeParameter> getTypeParameters();

	public abstract JavaType getSuperClass();
	public abstract ImmutableList<JavaType> getImplementedInterfaces();

	public abstract ImmutableList<FieldDeclarationAst> getFields();
	public abstract ImmutableList<MethodDeclarationAst> getMethods();

	public abstract Builder toBuilder();

	public boolean isGenericClassDeclaration() {
		return !getTypeParameters().isEmpty();
	}

	public boolean isImplementingInterfaces() {
		return !getImplementedInterfaces().isEmpty();
	}

	@Override
	public ClassDeclarationAst astMap(AstMapper mapper) {
		// map fields
		List<FieldDeclarationAst> mappedFields = getFields().stream()
				.map(f -> f.astMap(mapper))
				.collect(toList());

		// map methods
		List<MethodDeclarationAst> mappedMethods = getMethods().stream()
				.map(m -> m.astMap(mapper))
				.collect(toList());

		// map annotations
		List<AnnotationAst> mappedAnnotations = getAnnotations().stream()
				.map(a -> a.astMap(mapper))
				.collect(toList());

		// map this
		Builder mapped = this.toBuilder()
				.setFields(mappedFields)
				.setMethods(mappedMethods)
				.setAnnotations(mappedAnnotations);

		return mapper.map(this, mapped);
	}


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
