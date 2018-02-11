package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

import java.util.Objects;

import static pl.marcinchwedczuk.cjava.decompiler.signature.javatype.BoundType.EXTENDS;

public class BoundedWildcardTypeArgument extends TypeArgument {
	private final BoundType boundType;
	private final JavaTypeSignature type;

	public BoundedWildcardTypeArgument(BoundType boundType, JavaTypeSignature type) {
		this.boundType = Objects.requireNonNull(boundType);
		this.type = Objects.requireNonNull(type);
	}

	public String toJavaString() {
		StringBuilder javaString = new StringBuilder();

		javaString.append("? ");

		switch (boundType) {
			case EXTENDS:
				javaString.append("extends ");
				break;
			case SUPER:
				javaString.append("super ");
				break;
		}

		javaString.append(type.toJavaType());

		return javaString.toString();
	}
}
