package pl.marcinchwedczuk.cjava.decompiler.signature;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@AutoValue
public abstract class MethodSignature {
	public static MethodSignature basic(JavaType returnType, JavaType... parameterTypes) {
		return builder().signature(returnType, parameterTypes).build();
	}

	public static MethodSignature basic(JavaType returnType, List<JavaType> parameterTypes) {
		return builder().signature(returnType, parameterTypes).build();
	}

	static MethodSignature create(
			List<TypeParameter> genericParameters,
			JavaType returnType,
			List<MethodParameter> parameters,
			List<JavaType> checkedExceptions)
	{
		return new AutoValue_MethodSignature(
				copyOf(genericParameters),
				returnType,
				copyOf(parameters),
				copyOf(checkedExceptions));
	}


	public static MethodSignatureBuilder builder() {
		return new MethodSignatureBuilder();
	}

	public abstract ImmutableList<TypeParameter> getTypeParameters();
	public abstract JavaType getReturnType();
	public abstract ImmutableList<MethodParameter> getParameters();
	public abstract ImmutableList<JavaType> getCheckedExceptions();

	public ImmutableList<JavaType> getParametersTypes() {
		return getParameters().stream()
				.map(MethodParameter::getType)
				.collect(toImmutableList());
	}

	public JavaType getParameterType(int index) {
		return getParametersTypes().get(index);
	}

	public boolean hasVoidReturnType() {
		return PrimitiveType.VOID.equals(getReturnType());
	}

	public int getArity() {
		return getParametersTypes().size();
	}

	public String asJavaSoucrceCode() {
		StringBuilder javaSignature = new StringBuilder();

		if (!getTypeParameters().isEmpty()) {
			javaSignature
					.append('<')
					.append(
						getTypeParameters().stream()
								.map(TypeParameter::toJavaString)
								.collect(joining(", ")))
					.append("> ");
		}

		javaSignature
				.append(getReturnType().asSourceCodeString())
				.append("(");

		javaSignature.append(
			getParametersTypes().stream()
					.map(JavaType::asSourceCodeString)
					.collect(joining(", ")));

		javaSignature.append(")");

		if (!getCheckedExceptions().isEmpty()) {
			javaSignature.append(" throws ")
					.append(getCheckedExceptions().stream()
								.map(JavaType::asSourceCodeString)
								.collect(joining(", ")));
		}

		return javaSignature.toString();
	}
}
