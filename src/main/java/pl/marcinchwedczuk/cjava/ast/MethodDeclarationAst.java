package pl.marcinchwedczuk.cjava.ast;

import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.decompiler.descriptor.method.MethodSignature;

import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;

public class MethodDeclarationAst {
	private final String methodName;
	private final MethodSignature methodSignature;

	private Visibility visibility;
	private boolean isStatic;
	private boolean isAbstract;
	private boolean isFinal;
	private boolean isNative;
	private boolean isSynchronized;
	private boolean isVarargs;
	private boolean isStrictFP;

	private List<AnnotationAst> annotations = emptyList();

	public MethodDeclarationAst(String methodName, MethodSignature methodSignature) {
		this.methodName = requireNonNull(methodName);
		this.methodSignature = requireNonNull(methodSignature);
		this.visibility = Visibility.PACKAGE;
	}

	public String getMethodName() {
		return methodName;
	}

	public MethodSignature getMethodSignature() {
		return methodSignature;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public void setAbstract(boolean anAbstract) {
		this.isAbstract = anAbstract;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setFinal(boolean aFinal) {
		this.isFinal = aFinal;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public void setNative(boolean aNative) {
		this.isNative = aNative;
	}

	public boolean isNative() {
		return isNative;
	}

	public void setSynchronized(boolean aSynchronized) {
		this.isSynchronized = aSynchronized;
	}

	public boolean isSynchronized() {
		return isSynchronized;
	}

	public void setVarargs(boolean varargs) {
		this.isVarargs = varargs;
	}

	public boolean isVarargs() {
		return isVarargs;
	}

	public void setStrictFP(boolean strictFP) {
		this.isStrictFP = strictFP;
	}

	public boolean isStrictFP() {
		return isStrictFP;
	}

	public List<AnnotationAst> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<AnnotationAst> annotations) {
		this.annotations = annotations;
	}
}
