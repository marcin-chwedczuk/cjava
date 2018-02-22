package pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class ConcreteTypeTypeArgument extends TypeArgument {
	public static ConcreteTypeTypeArgument create(JavaType type) {
		return new AutoValue_ConcreteTypeTypeArgument(type);
	}

	public abstract JavaType getType();

	@Override
	public ImmutableList<JavaType> decomposeToRawTypes() {
		return getType().decomposeToRawTypes();
	}

	public String toJavaString() {
		return getType().asSourceCodeString();
	}
}
