package pl.marcinchwedczuk.cjava.decompiler.signature;

import pl.marcinchwedczuk.cjava.decompiler.descriptor.method.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.BaseType;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaType;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaTypeSignatureParser;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.TokenStream;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class MethodSignatureParser {
	private final TokenStream tokenStream;

	public MethodSignatureParser(TokenStream tokenStream) {
		this.tokenStream = requireNonNull(tokenStream);
	}

	public MethodSignature parseMethodSignature() {
		List<TypeParameter> genericTypeParameters =
				new ClassSignatureParser(tokenStream).parseTypeParameters();

		List<JavaType> parameterTypes = parseParameterTypes();
		JavaType returnType = parseReturnType();

		List<JavaType> throwsExceptions = parseThrows();

		return new MethodSignature(
				genericTypeParameters, returnType, parameterTypes, throwsExceptions);
	}

	private JavaType parseReturnType() {
		if (tokenStream.currentIs('V')) {
			tokenStream.matchCurrent();
			return BaseType.VOID;
		}

		return parseTypeSignature();
	}


	private List<JavaType> parseParameterTypes() {
		tokenStream.match('(');

		List<JavaType> parameterTypes = new ArrayList<>();
		while (!tokenStream.currentIs(')')) {
			JavaType parameterType = parseTypeSignature();
			parameterTypes.add(parameterType);
		}

		tokenStream.match(')');
		return parameterTypes;
	}


	private JavaType parseTypeSignature() {
		return new JavaTypeSignatureParser(tokenStream)
				.parseReferenceTypeSignature();
	}

	private List<JavaType> parseThrows() {
		List<JavaType> throwsExceptions = new ArrayList<>();

		while (!tokenStream.ended()) {
			tokenStream.match('^');

			JavaType exceptionType = parseTypeSignature();
			throwsExceptions.add(exceptionType);
		}

		return throwsExceptions;
	}
}
