package pl.marcinchwedczuk.cjava.optimizer.imports;

import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

public class FullQualifiedNameJavaTypeNameRenderer implements JavaTypeNameRenderer {
	@Override
	public String renderTypeName(JavaType type) {
		return type.asSourceCodeString();
	}
}
