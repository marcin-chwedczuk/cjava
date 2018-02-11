package pl.marcinchwedczuk.cjava.ast;

import com.google.common.collect.Lists;
import pl.marcinchwedczuk.cjava.decompiler.signature.TypeParameter;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaType;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;
import static pl.marcinchwedczuk.cjava.util.ListUtils.readOnlyCopy;

public class ClassDeclarationAst extends TypeDeclarationAst {
	private final ClassType className;
	private final List<TypeParameter> typeParameters;

	private final JavaType superClassName;
	private final List<JavaType> implementedInterfaces;

	private boolean isAbstract;
	private boolean isFinal;
	private Visibility visibility;

	public ClassDeclarationAst(ClassType className,
							   List<TypeParameter> typeParameters,
							   JavaType superClassName,
							   List<JavaType> implementedInterfaces) {

		this.className = requireNonNull(className);
		this.typeParameters = readOnlyCopy(typeParameters);
		this.superClassName = requireNonNull(superClassName);
		this.implementedInterfaces = readOnlyCopy(implementedInterfaces);
	}

	public ClassType getClassName() {
		return className;
	}

	public List<TypeParameter> getTypeParameters() {
		return typeParameters;
	}

	public JavaType getSuperClassName() {
		return superClassName;
	}

	public List<JavaType> getImplementedInterfaces() {
		return implementedInterfaces;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean anAbstract) {
		isAbstract = anAbstract;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(boolean aFinal) {
		isFinal = aFinal;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public boolean isGenericClassDeclaration() {
		return !getTypeParameters().isEmpty();
	}

	public boolean isImplementingInterfaces() {
		return !getImplementedInterfaces().isEmpty();
	}
}
