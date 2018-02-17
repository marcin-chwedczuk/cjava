package pl.marcinchwedczuk.cjava.decompiler.signature;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static pl.marcinchwedczuk.cjava.util.ListUtils.readOnlyCopy;

@AutoValue
public abstract class TypeParameter {

	public static TypeParameter basic(String parameterName) {
		return create(parameterName, ClassType.of(Object.class), ImmutableList.of());
	}

	public static TypeParameter create(String parameterName,
									   JavaType classBound,
									   List<JavaType> interfaceBounds) {

		return new AutoValue_TypeParameter(
				parameterName, classBound, ImmutableList.copyOf(interfaceBounds));
	}

	public abstract String getName();
	@Nullable
	public abstract JavaType getClassBound();
	public abstract ImmutableList<JavaType> getInterfaceBounds();

	public String toJavaString() {
		Stream<JavaType> optionalClassBound = Stream.of(getClassBound())
				.filter(Objects::nonNull);

		String parameterBound =
				Stream.concat(optionalClassBound, getInterfaceBounds().stream())
					.map(JavaType::asSourceCodeString)
					.collect(joining(" & "));

		if (!parameterBound.isEmpty()) {
			parameterBound = " extends " + parameterBound;
		}

		return getName().concat(parameterBound);
	}

}
