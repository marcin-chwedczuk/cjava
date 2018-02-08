package pl.marcinchwedczuk.cjava.bytecode.annotation;

import com.google.common.io.ByteStreams;
import pl.marcinchwedczuk.cjava.bytecode.InvalidJavaClassFileException;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.Constant;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;
import pl.marcinchwedczuk.cjava.bytecode.fields.FieldAccessFlag;
import pl.marcinchwedczuk.cjava.bytecode.fields.FieldInfo;
import pl.marcinchwedczuk.cjava.bytecode.fields.Fields;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex.fromUnsignedShort;

public class AttributesReader {

	public Attributes readAttributes(DataInputStream classFileBytes) throws IOException {
		int attributesCount = Short.toUnsignedInt(classFileBytes.readShort());

		List<Attribute> attributes = readAttributes(classFileBytes, attributesCount);

		return new Attributes(attributes);
	}

	private List<Attribute> readAttributes(DataInputStream classFileBytes, int count) throws IOException {
		List<Attribute> fields = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			fields.add(readAttribute(classFileBytes));
		}

		return fields;
	}

	private Attribute readAttribute(DataInputStream classFileBytes) throws IOException {
		ConstantPoolIndex attributeName = fromUnsignedShort(classFileBytes.readShort());

		int attributeLength = classFileBytes.readInt();
		byte[] info = new byte[attributeLength];

		if (ByteStreams.read(classFileBytes, info, 0, info.length) != info.length) {
			throw new InvalidJavaClassFileException(
					"Unexpected end of class file while reading " +
					"attribute.");
		}

		return new Attribute(attributeName, info);
	}
}
