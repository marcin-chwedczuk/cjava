package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

import java.util.Objects;

import static pl.marcinchwedczuk.cjava.decompiler.signature.javatype.BoundType.EXTENDS;

public class ConcreateTypeTypeArgument extends TypeArgument {
	private final JavaTypeSignature type;

	public ConcreateTypeTypeArgument(JavaTypeSignature type) {
		this.type = Objects.requireNonNull(type);
	}

	public String toJavaString() {
		return type.toJavaType();
	}
}
