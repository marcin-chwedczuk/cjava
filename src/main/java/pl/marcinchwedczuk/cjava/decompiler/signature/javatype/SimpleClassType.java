package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;

public class SimpleClassType {
	public static SimpleClassType fromClassName(String className) {
		return new SimpleClassType(className, emptyList());
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
