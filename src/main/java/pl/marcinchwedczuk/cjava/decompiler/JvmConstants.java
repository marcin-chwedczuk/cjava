package pl.marcinchwedczuk.cjava.decompiler;

public class JvmConstants {
	private static final String CONSTRUCTOR_NAME = "<init>";
	private static final String CLASS_INITIALIZER_NAME = "<clinit>";

	public static boolean isConstructorName(String methodName) {
		return CONSTRUCTOR_NAME.equals(methodName);
	}
}
