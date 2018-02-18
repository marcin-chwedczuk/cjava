package pl.marcinchwedczuk.cjava.decompiler.signature;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class LocalVariable {

	public static LocalVariable create(JavaType type, String name) {
		return new AutoValue_LocalVariable(type, name);
	}

	public abstract JavaType getType();
	public abstract String getName();
}
