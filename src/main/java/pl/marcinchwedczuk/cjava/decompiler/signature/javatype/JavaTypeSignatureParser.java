package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

import pl.marcinchwedczuk.cjava.decompiler.signature.parser.TokenStream;

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

	public JavaTypeSignature parse() {
		return parseJavaTypeSignature();
	}

	private JavaTypeSignature parseJavaTypeSignature() {
		if (tokenStream.currentIsAnyOf("BCDFIJSZ")) {
			return parseBaseType();
		}
		else {
			return parseReferenceTypeSignature();
		}
	}

	public JavaTypeSignature parseReferenceTypeSignature() {
		if (tokenStream.currentIs(ARRAY_TYPE_MARKER)) {
			return parseArrayTypeSignature();
		}

		if (tokenStream.currentIs(TYPE_VARIABLE_MARKER)) {
			return parseTypeVariableSignature();
		}

		return parseClassTypeSignature();
	}

	public JavaTypeSignature parseClassTypeSignature() {
		tokenStream.match(CLASS_TYPE_SIGNATURE_MARKER);

		List<String> packageSpecifier = parsePackageSpecifier();

		List<SimpleClassType> classes = new ArrayList<>();

		while (!tokenStream.currentIs(';')) {
			SimpleClassType simpleClassType = parseSimpleClassTypeSignature();
			classes.add(simpleClassType);
		}

		tokenStream.match(CLASS_TYPE_SIGNATURE_END);

		return new ClassType(packageSpecifier, classes);
	}

	private SimpleClassType parseSimpleClassTypeSignature() {
		String classIdentifier = tokenStream.matchIdentifier();
		List<TypeArgument> typeArguments = parseTypeArguments();

		return new SimpleClassType(classIdentifier, typeArguments);
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

			JavaTypeSignature type = parseReferenceTypeSignature();

			return TypeArgument.forBoundedWildcard(boundType, type);
		}

		JavaTypeSignature type = parseReferenceTypeSignature();
		return TypeArgument.forConcreateType(type);
	}

	private List<String> parsePackageSpecifier() {
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

		return packageSpecifier;
	}

	private JavaTypeSignature parseTypeVariableSignature() {
		tokenStream.match(TYPE_VARIABLE_MARKER);
		JavaTypeSignature typeVariable =
				new TypeVariable(tokenStream.matchIdentifier());
		tokenStream.match(TYPE_VARIABLE_END_MARKER);

		return typeVariable;
	}

	private JavaTypeSignature parseArrayTypeSignature() {
		int arrayDimension = 0;

		while (tokenStream.currentIs(ARRAY_TYPE_MARKER)) {
			tokenStream.match(ARRAY_TYPE_MARKER);
			arrayDimension++;
		}

		JavaTypeSignature arrayType = parseJavaTypeSignature();

		return new ArrayType(arrayDimension, arrayType);
	}

	private JavaTypeSignature parseBaseType() {
		JavaTypeSignature baseType = BaseType.parse(tokenStream.current());
		tokenStream.matchCurrent();
		return baseType;
	}

}
