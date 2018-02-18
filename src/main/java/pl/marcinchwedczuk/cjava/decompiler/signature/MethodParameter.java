package pl.marcinchwedczuk.cjava.decompiler.signature;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class MethodParameter {

	public static MethodParameter create(JavaType type, String name) {
		return new AutoValue_MethodParameter(type, name);
	}

	public abstract JavaType getType();
	public abstract String getName();
}
