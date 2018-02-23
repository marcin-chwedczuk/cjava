package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.decompiler.signature.TypeParameter;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;
import pl.marcinchwedczuk.cjava.optimizer.imports.JavaTypeNameRenderer;

import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

public class TypeParameterDeclarationSourceCodeFormatter extends BaseSourceCodeFormatter {
	private final TypeParameter typeParameter;

	protected TypeParameterDeclarationSourceCodeFormatter(JavaTypeNameRenderer typeNameRenderer, JavaCodeWriter codeWriter, TypeParameter typeParameter) {
		super(typeNameRenderer, codeWriter);
		this.typeParameter = requireNonNull(typeParameter);
	}

	public void convertAstToJavaCode() {
		codeWriter.print(typeParameter.getName());

		Stream<JavaType> optionalClassBound = Stream.of(typeParameter.getClassBound())
				.filter(Objects::nonNull);

		String parameterBound =
				Stream.concat(optionalClassBound, typeParameter.getInterfaceBounds().stream())
						.map(typeNameRenderer::renderTypeName)
						.collect(joining(" & "));

		if (!parameterBound.isEmpty()) {
			codeWriter
					.print(" extends ")
					.print(parameterBound);
		}
	}
}
