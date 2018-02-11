package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

import java.util.Objects;

public class ConcreateTypeTypeArgument extends TypeArgument {
	private final JavaType type;

	public ConcreateTypeTypeArgument(JavaType type) {
		this.type = Objects.requireNonNull(type);
	}

	public String toJavaString() {
		return type.asSourceCodeString();
	}
}
