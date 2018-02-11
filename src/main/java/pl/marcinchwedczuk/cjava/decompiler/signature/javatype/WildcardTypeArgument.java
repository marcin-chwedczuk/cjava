package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

public class WildcardTypeArgument extends TypeArgument {
	@Override
	public String toJavaString() {
		return "?";
	}
}
