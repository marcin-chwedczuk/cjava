package pl.marcinchwedczuk.cjava.decompiler.typesystem;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs.TypeArgument;

import java.util.List;

import static java.util.stream.Collectors.joining;

@AutoValue
public abstract class SimpleClassType {
	public static SimpleClassType fromClassName(String className) {
		return new AutoValue_SimpleClassType(className, ImmutableList.of());
	}

	public static SimpleClassType forGenericClass(String className, List<TypeArgument> typeArguments) {
		return new AutoValue_SimpleClassType(className, ImmutableList.copyOf(typeArguments));
	}

	public static SimpleClassType forGenericClass(String className, TypeArgument... typeArguments) {
		return new AutoValue_SimpleClassType(className, ImmutableList.copyOf(typeArguments));
	}

	public abstract String getClassName();
	public abstract ImmutableList<TypeArgument> getTypeArguments();

	public String asSourceCodeString() {
		StringBuilder javaType = new StringBuilder();

		javaType.append(getClassName());

		if (!getTypeArguments().isEmpty()) {
			javaType.append("<");

			javaType.append(
					getTypeArguments().stream()
						.map(TypeArgument::toJavaString)
						.collect(joining(", ")));

			javaType.append(">");
		}

		return javaType.toString();
	}
}
