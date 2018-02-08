package pl.marcinchwedczuk.cjava.bytecode.fields;

import pl.marcinchwedczuk.cjava.bytecode.FlagsEnumMapper;
import pl.marcinchwedczuk.cjava.bytecode.annotation.Attributes;
import pl.marcinchwedczuk.cjava.bytecode.annotation.AttributesReader;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex.fromUnsignedShort;

public class FieldsReader {
	private final AttributesReader attributeReader;
	private final FlagsEnumMapper flagsEnumMapper;

	public FieldsReader() {
		this.attributeReader = new AttributesReader();
		this.flagsEnumMapper = new FlagsEnumMapper();
	}

	public Fields readFields(DataInputStream classFileBytes) throws IOException {
		int fieldsCount = Short.toUnsignedInt(classFileBytes.readShort());

		List<FieldInfo> fields = readFields(classFileBytes, fieldsCount);

		return new Fields(fieldsCount, fields);
	}

	private List<FieldInfo> readFields(DataInputStream classFileBytes, int count) throws IOException {
		List<FieldInfo> fields = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			fields.add(readField(classFileBytes));
		}

		return fields;
	}

	private FieldInfo readField(DataInputStream classFileBytes) throws IOException {
		EnumSet<FieldAccessFlag> accessFlags =
				flagsEnumMapper.mapToFlags(classFileBytes.readShort(), FieldAccessFlag.class);

		ConstantPoolIndex name = fromUnsignedShort(classFileBytes.readShort());
		ConstantPoolIndex descriptor = fromUnsignedShort(classFileBytes.readShort());

		Attributes attributes = attributeReader.readAttributes(classFileBytes);

		return new FieldInfo(accessFlags, name, descriptor, attributes);
	}
}
