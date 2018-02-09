package pl.marcinchwedczuk.cjava.bytecode.annotation;

import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;
import pl.marcinchwedczuk.cjava.bytecode.utils.ClassFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex.readFrom;

public class AttributesReader {

	public Attributes readAttributes(ClassFileReader classFileReader) throws IOException {
		int attributesCount = classFileReader.readUnsignedShort();

		List<Attribute> attributes = readAttributes(classFileReader, attributesCount);

		return new Attributes(attributes);
	}

	private List<Attribute> readAttributes(ClassFileReader classFileReader, int count) throws IOException {
		List<Attribute> fields = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			fields.add(readAttribute(classFileReader));
		}

		return fields;
	}

	private Attribute readAttribute(ClassFileReader classFileReader) throws IOException {
		ConstantPoolIndex attributeName = readFrom(classFileReader);

		int attributeLength = classFileReader.readInt();
		byte[] info = classFileReader.readBytes(attributeLength);

		return new Attribute(attributeName, info);
	}
}
