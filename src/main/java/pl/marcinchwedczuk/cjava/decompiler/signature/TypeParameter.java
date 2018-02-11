package pl.marcinchwedczuk.cjava.decompiler.signature;

import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static pl.marcinchwedczuk.cjava.util.ListUtils.readOnlyCopy;

public class TypeParameter {
	private final String name;
	@Nullable
	private final JavaType classBound;
	private final List<JavaType> interfaceBounds;

	public TypeParameter(String name,
						 @Nullable JavaType classBound,
						 List<JavaType> interfaceBounds) {
		this.name = requireNonNull(name);
		this.classBound = classBound;
		this.interfaceBounds = readOnlyCopy(interfaceBounds);
	}

	public String getName() {
		return name;
	}

	public Optional<JavaType> getClassBound() {
		return Optional.ofNullable(classBound);
	}

	public List<JavaType> getInterfaceBounds() {
		return interfaceBounds;
	}

	public String toJavaString() {
		Stream<JavaType> optionalClassBound = Stream.of(classBound)
				.filter(Objects::nonNull);

		String parameterBound = Stream.concat(optionalClassBound, interfaceBounds.stream())
				.map(JavaType::asSourceCodeString)
				.collect(joining(" & "));

		if (!parameterBound.isEmpty()) {
			parameterBound = " extends " + parameterBound;
		}

		return name.concat(parameterBound);
	}
}
