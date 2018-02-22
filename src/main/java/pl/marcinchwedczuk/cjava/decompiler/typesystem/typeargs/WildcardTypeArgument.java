package pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

public class WildcardTypeArgument extends TypeArgument {
	public static WildcardTypeArgument create() {
		return new WildcardTypeArgument();
	}

	private WildcardTypeArgument() { }

	@Override
	public ImmutableList<JavaType> decomposeToRawTypes() {
		return ImmutableList.of();
	}

	@Override
	public String toJavaString() {
		return "?";
	}

	@Override
	public int hashCode() {
		// some random number
		return 843298;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof WildcardTypeArgument);
	}

	@Override
	public String toString() {
		return toJavaString();
	}
}
