package pl.marcinchwedczuk.cjava.decompiler.descriptor.field;

import pl.marcinchwedczuk.cjava.bytecode.InvalidJavaClassFileException;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.TokenStream;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.*;

import java.util.Arrays;
import java.util.List;

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

	public JavaType parseFieldType() {
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

		return ArrayType.create(dimension, elementType);
	}

	private JavaType parseBaseType() {
		PrimitiveType primitiveType = PrimitiveType.parse(input.current());
		input.matchCurrent();

		return primitiveType;
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
		PackageName packageName = PackageName.from(packagePart);

		SimpleClassType className = SimpleClassType.fromClassName(lastElement(parts));

		return ClassType.create(packageName, className);
	}
}
