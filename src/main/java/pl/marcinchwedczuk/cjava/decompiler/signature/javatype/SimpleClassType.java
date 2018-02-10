package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

import java.util.List;

import static java.util.stream.Collectors.joining;

public class SimpleClassType {
	private final String identifier;
	private final List<TypeArgument> typeArguments;

	public SimpleClassType(String identifier, List<TypeArgument> typeArguments) {
		this.identifier = identifier;
		this.typeArguments = typeArguments;
	}

	public String toJavaType() {
		StringBuilder javaType = new StringBuilder();

		javaType.append(identifier);

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
