package pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class BoundedWildcardTypeArgument extends TypeArgument {
	public static BoundedWildcardTypeArgument create(BoundType boundType, JavaType type) {
		return new AutoValue_BoundedWildcardTypeArgument(boundType, type);
	}

	public abstract BoundType getBoundType();
	public abstract JavaType getType();

	@Override
	public ImmutableList<JavaType> decomposeToRawTypes() {
		return getType().decomposeToRawTypes();
	}

	public String toJavaString() {
		StringBuilder javaString = new StringBuilder();

		javaString.append("? ");

		switch (getBoundType()) {
			case EXTENDS:
				javaString.append("extends ");
				break;
			case SUPER:
				javaString.append("super ");
				break;
		}

		javaString.append(getType().asSourceCodeString());

		return javaString.toString();
	}
}
