package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

import static pl.marcinchwedczuk.cjava.decompiler.signature.javatype.BoundType.EXTENDS;

public abstract class TypeArgument {
	public static TypeArgument forWildcard() {
		return new WildcardTypeArgument();
	}

	public static TypeArgument forConcreateType(JavaTypeSignature type) {
		return new ConcreateTypeTypeArgument(type);
	}

	public static TypeArgument forBoundedWildcard(BoundType boundType, JavaTypeSignature bound) {
		return new BoundedWildcardTypeArgument(boundType, bound);
	}

	public abstract String toJavaString();
}
