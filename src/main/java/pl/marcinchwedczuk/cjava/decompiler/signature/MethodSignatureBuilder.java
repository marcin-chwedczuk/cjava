package pl.marcinchwedczuk.cjava.decompiler.signature;

import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static pl.marcinchwedczuk.cjava.util.ListUtils.lastElement;
import static pl.marcinchwedczuk.cjava.util.ListUtils.withoutLastElement;

public class MethodSignatureBuilder {
	private List<TypeParameter> typeParameters = emptyList();
	private List<JavaType> parametersTypes = emptyList();
	private JavaType returnType;
	private List<JavaType> checkedExceptions = emptyList();

	/* package */ MethodSignatureBuilder() { }

	public MethodSignatureBuilder typeParameters(List<TypeParameter> typeParameters) {
		this.typeParameters = typeParameters;
		return this;
	}

	public MethodSignatureBuilder typeParameters(TypeParameter... typeParameters) {
		return typeParameters(asList(typeParameters));
	}

	public MethodSignatureBuilder signature(JavaType returnType, List<JavaType> parametersTypes) {
		this.parametersTypes = parametersTypes;
		this.returnType = returnType;
		return this;
	}

	public MethodSignatureBuilder signature(JavaType returnType, JavaType... parametersTypes) {
		return signature(returnType, asList(parametersTypes));
	}

	public MethodSignatureBuilder checkedExceptions(List<JavaType> exceptionTypes) {
		this.checkedExceptions = exceptionTypes;
		return this;
	}

	public MethodSignatureBuilder checkedExceptions(JavaType... checkedExceptions) {
		return checkedExceptions(asList(checkedExceptions));
	}

	public MethodSignature build() {
		return MethodSignature.create(
				typeParameters,
				returnType,
				parametersTypes,
				checkedExceptions);
	}

}
