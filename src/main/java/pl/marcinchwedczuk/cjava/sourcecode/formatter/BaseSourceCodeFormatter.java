package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;
import pl.marcinchwedczuk.cjava.optimizer.imports.JavaTypeNameRenderer;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public abstract class BaseSourceCodeFormatter {
	protected final JavaTypeNameRenderer typeNameRenderer;
	protected final JavaCodeWriter codeWriter;

	protected BaseSourceCodeFormatter(JavaTypeNameRenderer typeNameRenderer, JavaCodeWriter codeWriter) {
		this.typeNameRenderer = requireNonNull(typeNameRenderer);
		this.codeWriter = requireNonNull(codeWriter);
	}

	protected String typeName(JavaType type) {
		return typeNameRenderer.renderTypeName(type);
	}
}
