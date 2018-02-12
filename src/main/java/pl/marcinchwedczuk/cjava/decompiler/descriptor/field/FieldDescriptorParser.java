package pl.marcinchwedczuk.cjava.decompiler.descriptor.field;

import pl.marcinchwedczuk.cjava.bytecode.InvalidJavaClassFileException;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.*;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.TokenStream;
import pl.marcinchwedczuk.cjava.util.ListUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static pl.marcinchwedczuk.cjava.util.ListUtils.lastElement;
import static pl.marcinchwedczuk.cjava.util.ListUtils.withoutLastElement;

public class FieldDescriptorParser {
	private final TokenStream input;

	public FieldDescriptorParser(TokenStream input) {
		this.input = requireNonNull(input);
	}

	public JavaType parse() {
		JavaType type = parseFieldType();

		if (!input.ended()) {
			throw new InvalidJavaClassFileException(
					"Invalid field descriptor: '" + input.toString() + "'.");
		}

		return type;
	}

	private JavaType parseFieldType() {
		if (input.currentIsAnyOf("BCDFIJSZ")) {
			return parseBaseType();
		} else if (input.currentIs('L')) {
			return parseObjectType();
		} else if (input.currentIs('[')) {
			return parseArrayType();
		} else {
			throw new InvalidJavaClassFileException(
					"Invalid field descriptor: '" + input.toString() + "'.");
		}
	}

	private JavaType parseArrayType() {
		int dimension = 0;

		while (input.currentIs('[')) {
			input.matchCurrent();
			dimension++;
		}

		JavaType elementType = parseFieldType();

		return new ArrayType(dimension, elementType);
	}

	private JavaType parseBaseType() {
		BaseType baseType = BaseType.parse(input.current());
		input.matchCurrent();

		return baseType;
	}

	private JavaType parseObjectType() {
		input.match('L');

		StringBuilder typeName = new StringBuilder();

		while (!input.currentIs(';')) {
			typeName.append(input.current());
			input.matchCurrent();
		}

		input.match(';');

		List<String> parts =
				Arrays.asList(typeName.toString().split("/"));

		List<String> packagePart = withoutLastElement(parts);
		SimpleClassType className = new SimpleClassType(lastElement(parts));

		return new ClassType(packagePart, className);
	}
}
