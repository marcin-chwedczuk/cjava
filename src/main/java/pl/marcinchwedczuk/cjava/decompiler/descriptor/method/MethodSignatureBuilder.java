package pl.marcinchwedczuk.cjava.decompiler.descriptor.method;

import pl.marcinchwedczuk.cjava.decompiler.signature.TypeParameter;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.BaseType;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaType;
import pl.marcinchwedczuk.cjava.util.ListUtils;
import sun.reflect.FieldInfo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static pl.marcinchwedczuk.cjava.util.ListUtils.lastElement;
import static pl.marcinchwedczuk.cjava.util.ListUtils.withoutLastElement;

public class MethodSignatureBuilder {
	private List<TypeParameter> genericTypeParameters = emptyList();
	private List<JavaType> parameterTypes = emptyList();
	private JavaType returnType;
	private List<JavaType> throwsExceptions = emptyList();

	/* package */ MethodSignatureBuilder() { }

	public MethodSignatureBuilder genericParameters(TypeParameter... typeParameters) {
		this.genericTypeParameters = asList(typeParameters);
		return this;
	}

	public MethodSignatureBuilder signature(JavaType returnType, JavaType... parameterTypes) {
		this.parameterTypes = asList(parameterTypes);
		this.returnType = returnType;
		return this;
	}

	public MethodSignature build() {
		return new MethodSignature(
				genericTypeParameters,
				parameterTypes,
				returnType,
				throwsExceptions);
	}

}
