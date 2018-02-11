package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

public abstract class TypeArgument {
	public static TypeArgument forWildcard() {
		return new WildcardTypeArgument();
	}

	public static TypeArgument forConcreateType(JavaType type) {
		return new ConcreateTypeTypeArgument(type);
	}

	public static TypeArgument forBoundedWildcard(BoundType boundType, JavaType bound) {
		return new BoundedWildcardTypeArgument(boundType, bound);
	}

	public abstract String toJavaString();
}
