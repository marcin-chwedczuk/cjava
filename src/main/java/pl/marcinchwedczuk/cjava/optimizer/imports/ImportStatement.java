package pl.marcinchwedczuk.cjava.optimizer.imports;

import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class ImportStatement {
	private final JavaType typeToImport;

	public ImportStatement(JavaType typeToImport) {
		this.typeToImport = requireNonNull(typeToImport);
	}

	public JavaType getTypeToImport() {
		return typeToImport;
	}

	@Override
	public String toString() {
		return "ImportStatement{" +
				"typeToImport=" + typeToImport +
				'}';
	}
}
