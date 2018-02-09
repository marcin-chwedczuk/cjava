package pl.marcinchwedczuk.cjava.bytecode.fields;

import pl.marcinchwedczuk.cjava.bytecode.FlagsEnumMapper;
import pl.marcinchwedczuk.cjava.bytecode.annotation.Attributes;
import pl.marcinchwedczuk.cjava.bytecode.annotation.AttributesReader;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;
import pl.marcinchwedczuk.cjava.bytecode.utils.ClassFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex.readFrom;

public class FieldsReader {
	private final AttributesReader attributeReader;
	private final FlagsEnumMapper flagsEnumMapper;

	public FieldsReader() {
		this.attributeReader = new AttributesReader();
		this.flagsEnumMapper = new FlagsEnumMapper();
	}

	public Fields readFields(ClassFileReader classFileReader) throws IOException {
		int fieldsCount = classFileReader.readUnsignedShort();

		List<FieldInfo> fields = readFields(classFileReader, fieldsCount);

		return new Fields(fieldsCount, fields);
	}

	private List<FieldInfo> readFields(ClassFileReader classFileBytes, int count) throws IOException {
		List<FieldInfo> fields = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			fields.add(readField(classFileBytes));
		}

		return fields;
	}

	private FieldInfo readField(ClassFileReader classFileReader) throws IOException {
		EnumSet<FieldAccessFlag> accessFlags =
				flagsEnumMapper.mapToFlags(classFileReader.readUnsignedShort(), FieldAccessFlag.class);

		ConstantPoolIndex name = readFrom(classFileReader);
		ConstantPoolIndex descriptor = readFrom(classFileReader);

		Attributes attributes = attributeReader.readAttributes(classFileReader);

		return new FieldInfo(accessFlags, name, descriptor, attributes);
	}
}
