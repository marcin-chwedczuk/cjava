package pl.marcinchwedczuk.cjava.decompiler.signature;

import pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.JavaTypeSignatureParser;
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
		List<TypeParameter> typeParameters =
				new ClassSignatureParser(tokenStream).parseTypeParameters();

		List<JavaType> parametersTypes = parseParametersTypes();
		JavaType returnType = parseReturnType();

		List<JavaType> checkedExceptions = parseCheckedExceptions();

		return MethodSignature.builder()
				.typeParameters(typeParameters)
				.signature(returnType, parametersTypes)
				.checkedExceptions(checkedExceptions)
				.build();
	}

	private JavaType parseReturnType() {
		if (tokenStream.currentIs('V')) {
			tokenStream.matchCurrent();
			return PrimitiveType.VOID;
		}

		return parseTypeSignature();
	}


	private List<JavaType> parseParametersTypes() {
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

	private List<JavaType> parseCheckedExceptions() {
		List<JavaType> throwsExceptions = new ArrayList<>();

		while (!tokenStream.ended()) {
			tokenStream.match('^');

			JavaType exceptionType = parseTypeSignature();
			throwsExceptions.add(exceptionType);
		}

		return throwsExceptions;
	}
}
