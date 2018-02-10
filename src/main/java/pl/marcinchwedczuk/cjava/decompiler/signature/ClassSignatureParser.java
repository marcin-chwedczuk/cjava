package pl.marcinchwedczuk.cjava.decompiler.signature;

import pl.marcinchwedczuk.cjava.decompiler.signature.parser.TokenStream;

import java.util.Objects;

public class ClassSignatureParser {
	private final TokenStream tokenStream;

	public ClassSignatureParser(TokenStream tokenStream) {
		this.tokenStream = Objects.requireNonNull(tokenStream);
	}

	public ClassSignature parse() {
		if (tokenStream.currentIs('<')) {
			tokenStream.match('<');

			String identifier = tokenStream.matchIdentifier();
			tokenStream.match(':');

			if (!tokenStream.currentIs(':')) {
				// read class bound
			}

			tokenStream.match('>');
		}


		throw new RuntimeException("foo");
	}
}
