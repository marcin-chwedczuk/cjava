package pl.marcinchwedczuk.cjava.decompiler.descriptor.method;

import pl.marcinchwedczuk.cjava.decompiler.signature.TypeParameter;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaType;
import pl.marcinchwedczuk.cjava.util.ListUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static pl.marcinchwedczuk.cjava.util.ListUtils.lastElement;
import static pl.marcinchwedczuk.cjava.util.ListUtils.readOnlyCopy;
import static pl.marcinchwedczuk.cjava.util.ListUtils.withoutLastElement;

public class MethodSignature {
	public static MethodSignature basic(JavaType... types) {
		return new MethodSignature(withoutLastElement(types), lastElement(types));
	}

	public static MethodSignatureBuilder builder() {
		return new MethodSignatureBuilder();
	}

	private final List<JavaType> parameterTypes;
	private final JavaType returnType;
	private final List<TypeParameter> genericTypeParameters;
	private final List<JavaType> throwsExceptions;

	public MethodSignature(
			List<TypeParameter> genericTypeParameters,
			List<JavaType> parameterTypes,
			JavaType returnType,
			List<JavaType> throwsExceptions) {
		this.parameterTypes = readOnlyCopy(parameterTypes);
		this.returnType = requireNonNull(returnType);
		this.genericTypeParameters = readOnlyCopy(genericTypeParameters);
		this.throwsExceptions = readOnlyCopy(throwsExceptions);
	}

	public MethodSignature(List<JavaType> parameterTypes, JavaType returnType) {
		this(emptyList(), parameterTypes, returnType, emptyList());
	}

	public List<JavaType> getParameterTypes() {
		return parameterTypes;
	}

	public JavaType getReturnType() {
		return returnType;
	}

	public JavaType getParameterType(int index) {
		return parameterTypes.get(index);
	}

	public List<TypeParameter> getGenericTypeParameters() {
		return genericTypeParameters;
	}

	public List<JavaType> getThrowsExceptions() {
		return throwsExceptions;
	}

	public String asJavaSoucrceCode() {
		StringBuilder javaSignature = new StringBuilder();

		if (!genericTypeParameters.isEmpty()) {
			javaSignature
					.append('<')
					.append(
						genericTypeParameters.stream()
								.map(TypeParameter::toJavaString)
								.collect(joining(", ")))
					.append("> ");
		}

		javaSignature
				.append(returnType.asSourceCodeString())
				.append("(");

		javaSignature.append(
			parameterTypes.stream()
					.map(JavaType::asSourceCodeString)
					.collect(joining(", ")));

		javaSignature.append(")");

		if (!throwsExceptions.isEmpty()) {
			javaSignature.append(" throws ")
					.append(throwsExceptions.stream()
								.map(JavaType::asSourceCodeString)
								.collect(joining(", ")));
		}

		return javaSignature.toString();
	}
}
