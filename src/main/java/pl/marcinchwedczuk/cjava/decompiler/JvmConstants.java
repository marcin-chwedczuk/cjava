package pl.marcinchwedczuk.cjava.decompiler;

import pl.marcinchwedczuk.cjava.decompiler.typesystem.PackageName;

public class JvmConstants {
	private static final String CONSTRUCTOR_NAME = "<init>";
	private static final String CLASS_INITIALIZER_NAME = "<clinit>";

	public static final PackageName JAVA_LANG_PACKAGE = PackageName.fromString("java.lang");

	public static boolean isConstructorName(String methodName) {
		return CONSTRUCTOR_NAME.equals(methodName);
	}
}
