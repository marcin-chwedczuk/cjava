package pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
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

	public abstract ImmutableList<JavaType> decomposeToRawTypes();
}
