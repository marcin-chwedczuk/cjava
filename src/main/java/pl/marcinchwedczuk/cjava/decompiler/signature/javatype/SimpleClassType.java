package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;

public class SimpleClassType {
	public static SimpleClassType fromClassName(String className) {
		return new SimpleClassType(className, emptyList());
	}

	public static SimpleClassType forGenericClass(String className, TypeArgument... typeArguments) {
		return new SimpleClassType(className, asList(typeArguments));
	}

	private final String className;
	private final List<TypeArgument> typeArguments;

	public SimpleClassType(String className, List<TypeArgument> typeArguments) {
		this.className = className;
		this.typeArguments = typeArguments;
	}

	public SimpleClassType(String className) {
		this(className, emptyList());
	}

	public String getClassName() {
		return className;
	}

	public List<TypeArgument> getTypeArguments() {
		return typeArguments;
	}

	public String asSourceCodeString() {
		StringBuilder javaType = new StringBuilder();

		javaType.append(className);

		if (!typeArguments.isEmpty()) {
			javaType.append("<");

			javaType.append(
					typeArguments.stream()
						.map(TypeArgument::toJavaString)
						.collect(joining(", ")));

			javaType.append(">");
		}

		return javaType.toString();
	}
}
