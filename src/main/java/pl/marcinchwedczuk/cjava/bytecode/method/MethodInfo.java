package pl.marcinchwedczuk.cjava.bytecode.method;

import com.google.common.collect.Sets;
import pl.marcinchwedczuk.cjava.bytecode.attribute.Attributes;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class MethodInfo {
	private final Set<MethodAccessFlag> accessFlags;
	private final ConstantPoolIndex name;
	private final ConstantPoolIndex descriptor;
	private final Attributes attributes;

	public MethodInfo(
			EnumSet<MethodAccessFlag> accessFlags,
			ConstantPoolIndex name,
			ConstantPoolIndex descriptor,
			Attributes attributes)
	{
		this.accessFlags =
				Sets.immutableEnumSet(EnumSet.copyOf(accessFlags));

		this.name = Objects.requireNonNull(name);
		this.descriptor = Objects.requireNonNull(descriptor);
		this.attributes = Objects.requireNonNull(attributes);
	}

	public Set<MethodAccessFlag> getAccessFlags() {
		return accessFlags;
	}

	public ConstantPoolIndex getName() {
		return name;
	}

	public ConstantPoolIndex getDescriptor() {
		return descriptor;
	}

	public Attributes getAttributes() {
		return attributes;
	}
}
