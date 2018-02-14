package pl.marcinchwedczuk.cjava.decompiler.signature;

import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaType;
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
		JavaType superclass = parseClassTypeSignature();
		List<JavaType> interaces = parseSuperInterfaces();

		return new ClassSignature(typeParameters, superclass, interaces);
	}

	private List<JavaType> parseSuperInterfaces() {
		List<JavaType> interfaces = new ArrayList<>();

		while(!tokenStream.ended()) {
			interfaces.add(parseClassTypeSignature());
		}

		return interfaces;
	}

	List<TypeParameter> parseTypeParameters() {
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

		JavaType classBound = null;
		List<JavaType> interfaceBounds = new ArrayList<>();

		// class bound - can be empty
		tokenStream.match(':');
		if (!tokenStream.currentIs(':')) {
			classBound = parseReferenceTypeSignature();
		}

		// interface bounds
		while (tokenStream.currentIs(':')) {
			tokenStream.match(':');

			JavaType interfaceBound = parseReferenceTypeSignature();
			interfaceBounds.add(interfaceBound);
		}

		return new TypeParameter(identifier, classBound, interfaceBounds);
	}

	private JavaType parseReferenceTypeSignature() {
		return new JavaTypeSignatureParser(tokenStream)
				.parseReferenceTypeSignature();
	}

	private JavaType parseClassTypeSignature() {
		return new JavaTypeSignatureParser(tokenStream)
				.parseClassTypeSignature();
	}
}
