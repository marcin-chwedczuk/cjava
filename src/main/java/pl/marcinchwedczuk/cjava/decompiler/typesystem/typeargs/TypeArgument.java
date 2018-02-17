package pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs;

import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

public abstract class TypeArgument {
	public static TypeArgument forWildcard() {
		return WildcardTypeArgument.create();
	}

	public static TypeArgument forConcreateType(JavaType type) {
		return ConcreteTypeTypeArgument.create(type);
	}

	public static TypeArgument forBoundedWildcard(BoundType boundType, JavaType bound) {
		return BoundedWildcardTypeArgument.create(boundType, bound);
	}

	public abstract String toJavaString();
}
