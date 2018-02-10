package pl.marcinchwedczuk.cjava.decompiler.signature;

import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaTypeSignature;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class ClassSignature {
	private final List<TypeParameter> typeParameters;
	private final JavaTypeSignature superclass;
	private final List<JavaTypeSignature> interaces;

	public ClassSignature(List<TypeParameter> typeParameters,
						  JavaTypeSignature superclass,
						  List<JavaTypeSignature> interaces) {
		this.typeParameters = typeParameters;
		this.superclass = superclass;
		this.interaces = interaces;
	}

	public String toJavaString() {
		StringBuilder signature = new StringBuilder();

		if (!typeParameters.isEmpty()) {
			signature.append('<');

			signature.append(
					typeParameters.stream()
						.map(TypeParameter::toJavaString)
						.collect(joining(", ")));

			signature.append('>');
		}

		if (superclass != null) {
			signature
					.append(" extends ")
					.append(superclass.toJavaType());

		}

		if (!interaces.isEmpty()) {
			signature.append(" implements ");

			signature.append(
					interaces.stream()
						.map(JavaTypeSignature::toJavaType)
						.collect(joining(", ")));
		}

		return signature.toString();
	}
}
