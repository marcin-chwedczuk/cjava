package pl.marcinchwedczuk.cjava.decompiler.signature;

import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaTypeSignature;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class TypeParameter {
	private final String identifier;
	private final JavaTypeSignature classBound;
	private final List<JavaTypeSignature> interfaceBounds;

	public TypeParameter(String identifier, JavaTypeSignature classBound, List<JavaTypeSignature> interfaceBounds) {

		this.identifier = identifier;
		this.classBound = classBound;
		this.interfaceBounds = interfaceBounds;
	}

	public String toJavaString() {
		String parameterBound = Stream.concat(Stream.of(classBound), interfaceBounds.stream())
				.map(JavaTypeSignature::toJavaType)
				.collect(joining(" & "));

		if (!parameterBound.isEmpty()) {
			parameterBound = " extends " + parameterBound;
		}

		return identifier.concat(parameterBound);
	}
}
