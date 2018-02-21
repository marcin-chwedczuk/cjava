package pl.marcinchwedczuk.cjava.decompiler.signature.parser;

import pl.marcinchwedczuk.cjava.decompiler.typesystem.*;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs.BoundType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs.TypeArgument;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JavaTypeSignatureParser {
	private static final char ARRAY_TYPE_MARKER = '[';
	private static final char TYPE_VARIABLE_MARKER = 'T';
	private static final char TYPE_VARIABLE_END_MARKER = ';';
	private static final char PACKAGE_IDENTIFIER_SEPARATOR = '/';
	private static final char TYPE_ARGUMENTS_START = '<';
	private static final char TYPE_ARGUMENTS_END = '>';
	private static final char CLASS_TYPE_SIGNATURE_MARKER = 'L';
	private static final char CLASS_TYPE_SIGNATURE_END = ';';

	private final TokenStream tokenStream;

	public JavaTypeSignatureParser(TokenStream tokenStream) {
		this.tokenStream = Objects.requireNonNull(tokenStream);
	}

	public JavaType parse() {
		return parseJavaTypeSignature();
	}

	private JavaType parseJavaTypeSignature() {
		if (tokenStream.currentIsAnyOf("BCDFIJSZ")) {
			return parseBaseType();
		}
		else {
			return parseReferenceTypeSignature();
		}
	}

	public JavaType parseReferenceTypeSignature() {
		if (tokenStream.currentIs(ARRAY_TYPE_MARKER)) {
			return parseArrayTypeSignature();
		}

		if (tokenStream.currentIs(TYPE_VARIABLE_MARKER)) {
			return parseTypeVariableSignature();
		}

		return parseClassTypeSignature();
	}

	public JavaType parseClassTypeSignature() {
		tokenStream.match(CLASS_TYPE_SIGNATURE_MARKER);

		PackageName packageSpecifier = parsePackageSpecifier();

		List<SimpleClassType> classes = new ArrayList<>();

		// format: SomeClass .InnerClass1 .InnerClass2
		SimpleClassType simpleClassType = parseSimpleClassTypeSignature();
		classes.add(simpleClassType);

		while (!tokenStream.currentIs(';')) {
			tokenStream.match('.');
			simpleClassType = parseSimpleClassTypeSignature();
			classes.add(simpleClassType);
		}

		tokenStream.match(CLASS_TYPE_SIGNATURE_END);

		return ClassType.create(packageSpecifier, classes);
	}

	private SimpleClassType parseSimpleClassTypeSignature() {
		String classIdentifier = tokenStream.matchIdentifier();
		List<TypeArgument> typeArguments = parseTypeArguments();

		return SimpleClassType.forGenericClass(classIdentifier, typeArguments);
	}

	private List<TypeArgument> parseTypeArguments() {
		List<TypeArgument> typeArguments = new ArrayList<>();

		if (tokenStream.currentIs(TYPE_ARGUMENTS_START)) {
			tokenStream.match(TYPE_ARGUMENTS_START);

			while (!tokenStream.currentIs(TYPE_ARGUMENTS_END)) {
				TypeArgument typeArgument = parseTypeArgument();
				typeArguments.add(typeArgument);
			}

			tokenStream.match(TYPE_ARGUMENTS_END);
		}

		return typeArguments;
	}

	private TypeArgument parseTypeArgument() {
		if (tokenStream.currentIs('*')) {
			tokenStream.matchCurrent();

			return TypeArgument.forWildcard();
		}

		if (tokenStream.currentIsAnyOf("+-")) {
			BoundType boundType = BoundType.parse(tokenStream.current());
			tokenStream.matchCurrent();

			JavaType type = parseReferenceTypeSignature();

			return TypeArgument.forBoundedWildcard(boundType, type);
		}

		JavaType type = parseReferenceTypeSignature();
		return TypeArgument.forConcreateType(type);
	}

	private PackageName parsePackageSpecifier() {
		List<String> packageSpecifier = new ArrayList<>();

		while(true) {
			tokenStream.savePosition();

			String maybePackagePart =
					tokenStream.matchIdentifier();

			if (tokenStream.currentIs(PACKAGE_IDENTIFIER_SEPARATOR)) {
				tokenStream.match(PACKAGE_IDENTIFIER_SEPARATOR);

				packageSpecifier.add(maybePackagePart);
				tokenStream.dropSavedPosition();
			}
			else {
				tokenStream.restoreSavedPosition();
				break;
			}
		}

		return PackageName.from(packageSpecifier);
	}

	private JavaType parseTypeVariableSignature() {
		tokenStream.match(TYPE_VARIABLE_MARKER);
		JavaType typeVariable =
				TypeVariable.fromTypeParameterName(tokenStream.matchIdentifier());
		tokenStream.match(TYPE_VARIABLE_END_MARKER);

		return typeVariable;
	}

	private JavaType parseArrayTypeSignature() {
		int arrayDimension = 0;

		while (tokenStream.currentIs(ARRAY_TYPE_MARKER)) {
			tokenStream.match(ARRAY_TYPE_MARKER);
			arrayDimension++;
		}

		JavaType arrayType = parseJavaTypeSignature();

		return ArrayType.create(arrayDimension, arrayType);
	}

	private JavaType parseBaseType() {
		JavaType baseType = PrimitiveType.parse(tokenStream.current());
		tokenStream.matchCurrent();
		return baseType;
	}

}
