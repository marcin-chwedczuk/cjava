package pl.marcinchwedczuk.cjava.decompiler.signature;

import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static pl.marcinchwedczuk.cjava.util.ListUtils.readOnlyCopy;

public class ClassSignature {
	private final List<TypeParameter> typeParameters;
	private final JavaType superclass;
	private final List<JavaType> implementedInterfaces;

	public ClassSignature(List<TypeParameter> typeParameters,
						  JavaType superclass,
						  List<JavaType> implementedInterfaces) {

		this.typeParameters = readOnlyCopy(typeParameters);
		this.superclass = requireNonNull(superclass);
		this.implementedInterfaces = readOnlyCopy(implementedInterfaces);
	}

	public List<TypeParameter> getTypeParameters() {
		return typeParameters;
	}

	public JavaType getSuperClass() {
		return superclass;
	}

	public List<JavaType> getImplementedInterfaces() {
		return implementedInterfaces;
	}

	public String asJavaSourceCode() {
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
					.append(superclass.asSourceCodeString());

		}

		if (!implementedInterfaces.isEmpty()) {
			signature.append(" implements ");

			signature.append(
					implementedInterfaces.stream()
						.map(JavaType::asSourceCodeString)
						.collect(joining(", ")));
		}

		return signature.toString();
	}
}
