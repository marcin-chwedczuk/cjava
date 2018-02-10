package pl.marcinchwedczuk.cjava.bytecode.fields;

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

public class FieldsReader {
	private final ClassFileReader classFileReader;
	private final ConstantPool constantPool;
	private final FlagsEnumMapper flagsEnumMapper;

	public FieldsReader(ClassFileReader classFileReader, ConstantPool constantPool) {
		Preconditions.checkNotNull(classFileReader);
		Preconditions.checkNotNull(constantPool);

		this.classFileReader = classFileReader;
		this.constantPool = constantPool;
		this.flagsEnumMapper = new FlagsEnumMapper();
	}

	public Fields readFields() throws IOException {
		int fieldsCount = classFileReader.readUnsignedShort();

		List<FieldInfo> fields = readFields(fieldsCount);

		return new Fields(fieldsCount, fields);
	}

	private List<FieldInfo> readFields(int count) throws IOException {
		List<FieldInfo> fields = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			fields.add(readField());
		}

		return fields;
	}

	private FieldInfo readField() throws IOException {
		EnumSet<FieldAccessFlag> accessFlags =
				flagsEnumMapper.mapToFlags(classFileReader.readUnsignedShort(), FieldAccessFlag.class);

		ConstantPoolIndex name = readFrom(classFileReader);
		ConstantPoolIndex descriptor = readFrom(classFileReader);

		AttributesReader attributesReader =
				new AttributesReader(classFileReader, constantPool);
		Attributes attributes = attributesReader.readAttributes();

		return new FieldInfo(accessFlags, name, descriptor, attributes);
	}
}
