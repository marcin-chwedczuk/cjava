package pl.marcinchwedczuk.cjava.bytecode.method;

import com.google.common.base.Preconditions;
import pl.marcinchwedczuk.cjava.bytecode.FlagsEnumMapper;
import pl.marcinchwedczuk.cjava.bytecode.attribute.Attributes;
import pl.marcinchwedczuk.cjava.bytecode.attribute.AttributesReader;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPool;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;
import pl.marcinchwedczuk.cjava.bytecode.utils.ClassFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex.readFrom;

public class MethodReader {
	private final ClassFileReader classFileReader;
	private final ConstantPool constantPool;
	private final FlagsEnumMapper flagsEnumMapper;

	public MethodReader(ClassFileReader classFileReader, ConstantPool constantPool) {
		Preconditions.checkNotNull(classFileReader);
		Preconditions.checkNotNull(constantPool);

		this.classFileReader = classFileReader;
		this.constantPool = constantPool;

		this.flagsEnumMapper = new FlagsEnumMapper();
	}

	public Methods readMethods() throws IOException {
		int count = classFileReader.readUnsignedShort();

		List<MethodInfo> methods = readMethods(count);

		return new Methods(count, methods);
	}

	private List<MethodInfo> readMethods(int count) throws IOException {
		List<MethodInfo> methods = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			MethodInfo method = readMethod();
			methods.add(method);
		}

		return methods;
	}

	private MethodInfo readMethod() throws IOException {
		EnumSet<MethodAccessFlag> accessFlags =
				flagsEnumMapper.mapToFlags(classFileReader.readUnsignedShort(), MethodAccessFlag.class);

		ConstantPoolIndex name = readFrom(classFileReader);
		ConstantPoolIndex descriptor = readFrom(classFileReader);

		AttributesReader attributesReader =
				new AttributesReader(classFileReader, constantPool);
		Attributes attributes = attributesReader.readAttributes();

		return new MethodInfo(accessFlags, name, descriptor, attributes);
	}
}
