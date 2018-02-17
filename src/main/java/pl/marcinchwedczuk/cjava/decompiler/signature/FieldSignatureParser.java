package pl.marcinchwedczuk.cjava.decompiler.signature;

import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.JavaTypeSignatureParser;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.TokenStream;

import static java.util.Objects.requireNonNull;

public class FieldSignatureParser {
	private final TokenStream tokenStream;

	public FieldSignatureParser(TokenStream tokenStream) {
		this.tokenStream = requireNonNull(tokenStream);
	}

	public JavaType parse() {
		return new JavaTypeSignatureParser(tokenStream)
				.parseReferenceTypeSignature();
	}
}
