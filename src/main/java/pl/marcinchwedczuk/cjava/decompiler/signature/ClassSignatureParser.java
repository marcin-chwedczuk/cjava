package pl.marcinchwedczuk.cjava.decompiler.signature;

import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaTypeSignature;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaTypeSignatureParser;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.TokenStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassSignatureParser {
	private final TokenStream tokenStream;

	public ClassSignatureParser(TokenStream tokenStream) {
		this.tokenStream = Objects.requireNonNull(tokenStream);
	}

	public ClassSignature parse() {
		List<TypeParameter> typeParameters = parseTypeParameters();
		JavaTypeSignature superclass = parseClassTypeSignature();
		List<JavaTypeSignature> interaces = parseSuperInterfaces();

		return new ClassSignature(typeParameters, superclass, interaces);
	}

	private List<JavaTypeSignature> parseSuperInterfaces() {
		List<JavaTypeSignature> interfaces = new ArrayList<>();

		while(!tokenStream.ended()) {
			interfaces.add(parseClassTypeSignature());
		}

		return interfaces;
	}

	private List<TypeParameter> parseTypeParameters() {
		List<TypeParameter> typeParameters = new ArrayList<>();

		if (tokenStream.currentIs('<')) {
			tokenStream.match('<');

			while (!tokenStream.currentIs('>')) {
				typeParameters.add(parseTypeParameter());
			}

			tokenStream.match('>');
		}

		return typeParameters;
	}

	private TypeParameter parseTypeParameter() {
		String identifier = tokenStream.matchIdentifier();

		JavaTypeSignature classBound = null;
		List<JavaTypeSignature> interfaceBounds = new ArrayList<>();

		// class bound - can be empty
		tokenStream.match(':');
		if (!tokenStream.currentIs(':')) {
			classBound = parseReferenceTypeSignature();
		}

		// interface bounds
		while (tokenStream.currentIs(':')) {
			JavaTypeSignature interfaceBound = parseReferenceTypeSignature();
			interfaceBounds.add(interfaceBound);
		}

		return new TypeParameter(identifier, classBound, interfaceBounds);
	}

	private JavaTypeSignature parseReferenceTypeSignature() {
		return new JavaTypeSignatureParser(tokenStream)
				.parseReferenceTypeSignature();
	}

	private JavaTypeSignature parseClassTypeSignature() {
		return new JavaTypeSignatureParser(tokenStream)
				.parseClassTypeSignature();
	}
}
