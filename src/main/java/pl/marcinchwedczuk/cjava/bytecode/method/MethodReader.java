package pl.marcinchwedczuk.cjava.bytecode.method;

import pl.marcinchwedczuk.cjava.bytecode.FlagsEnumMapper;
import pl.marcinchwedczuk.cjava.bytecode.annotation.Attributes;
import pl.marcinchwedczuk.cjava.bytecode.annotation.AttributesReader;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;
import pl.marcinchwedczuk.cjava.bytecode.fields.FieldAccessFlag;
import pl.marcinchwedczuk.cjava.bytecode.fields.FieldInfo;
import pl.marcinchwedczuk.cjava.bytecode.utils.ClassFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex.readFrom;

public class MethodReader {
	private final AttributesReader attributeReader;
	private final FlagsEnumMapper flagsEnumMapper;

	public MethodReader() {
		this.attributeReader = new AttributesReader();
		this.flagsEnumMapper = new FlagsEnumMapper();
	}

	public Methods readMethods(ClassFileReader classFileReader) throws IOException {
		int count = classFileReader.readUnsignedShort();

		List<MethodInfo> methods = readMethods(classFileReader, count);

		return new Methods(count, methods);
	}

	private List<MethodInfo> readMethods(ClassFileReader classFileReader, int count) throws IOException {
		List<MethodInfo> methods = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			MethodInfo method = readMethod(classFileReader);
			methods.add(method);
		}

		return methods;
	}

	private MethodInfo readMethod(ClassFileReader classFileReader) throws IOException {
		EnumSet<MethodAccessFlag> accessFlags =
				flagsEnumMapper.mapToFlags(classFileReader.readUnsignedShort(), MethodAccessFlag.class);

		ConstantPoolIndex name = readFrom(classFileReader);
		ConstantPoolIndex descriptor = readFrom(classFileReader);

		Attributes attributes = attributeReader.readAttributes(classFileReader);

		return new MethodInfo(accessFlags, name, descriptor, attributes);
	}
}
