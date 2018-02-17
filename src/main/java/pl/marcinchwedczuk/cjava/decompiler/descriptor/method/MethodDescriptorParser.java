package pl.marcinchwedczuk.cjava.decompiler.descriptor.method;

import pl.marcinchwedczuk.cjava.bytecode.InvalidJavaClassFileException;
import pl.marcinchwedczuk.cjava.decompiler.descriptor.field.FieldDescriptorParser;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.TokenStream;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class MethodDescriptorParser {
	private final TokenStream input;

	public MethodDescriptorParser(TokenStream input) {
		this.input = requireNonNull(input);
	}

	public MethodSignature parse() {
		List<JavaType> parameterTypes = parseParameterDescriptors();
		JavaType returnType = parseReturnDescriptor();

		if (!input.ended()) {
			throw new InvalidJavaClassFileException(
					"Invalid method descriptor: '" + input.toString() + "'.");
		}

		return MethodSignature.basic(returnType, parameterTypes);
	}

	private List<JavaType> parseParameterDescriptors() {
		input.match('(');

		List<JavaType> parameterTypes = new ArrayList<>();
		while (!input.currentIs(')')) {
			JavaType parameterType = parseFieldType();
			parameterTypes.add(parameterType);
		}

		input.match(')');
		return parameterTypes;
	}

	private JavaType parseReturnDescriptor() {
		return parseFieldType();
	}

	private JavaType parseFieldType() {
		if (input.currentIs('V')) {
			input.matchCurrent();
			return PrimitiveType.VOID;
		}

		return new FieldDescriptorParser(input).parseFieldType();
	}
}
