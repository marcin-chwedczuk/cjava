package pl.marcinchwedczuk.cjava.decompiler.descriptor.method;

import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaType;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static pl.marcinchwedczuk.cjava.util.ListUtils.readOnlyCopy;

public class MethodSignature {
	private final List<JavaType> parameterTypes;
	private final JavaType returnType;

	public MethodSignature(List<JavaType> parameterTypes, JavaType returnType) {
		this.parameterTypes = readOnlyCopy(parameterTypes);
		this.returnType = requireNonNull(returnType);
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

	public String asJavaSoucrceCode() {
		StringBuilder javaSignature = new StringBuilder();

		javaSignature
				.append(returnType.asSourceCodeString())
				.append("(");

		javaSignature.append(
			parameterTypes.stream()
					.map(JavaType::asSourceCodeString)
					.collect(joining(", ")));

		javaSignature.append(")");

		return javaSignature.toString();
	}
}
