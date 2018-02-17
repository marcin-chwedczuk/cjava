package pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs;

public class WildcardTypeArgument extends TypeArgument {
	public static WildcardTypeArgument create() {
		return new WildcardTypeArgument();
	}

	private WildcardTypeArgument() { }

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
