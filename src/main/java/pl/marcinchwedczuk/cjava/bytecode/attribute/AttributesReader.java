package pl.marcinchwedczuk.cjava.bytecode.attribute;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import pl.marcinchwedczuk.cjava.bytecode.attribute.RuntimeVisibleAnnotationsAttribute.Annotation;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPool;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.Utf8Constant;
import pl.marcinchwedczuk.cjava.bytecode.utils.ClassFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex.readFrom;

public class AttributesReader {
	private final ClassFileReader classFileReader;
	private final ConstantPool constantPool;

	public AttributesReader(ClassFileReader classFileReader, ConstantPool constantPool) {
		this.classFileReader = Objects.requireNonNull(classFileReader);
		this.constantPool = Objects.requireNonNull(constantPool);
	}

	public Attributes readAttributes() throws IOException {
		int attributesCount = classFileReader.readUnsignedShort();

		List<Attribute> attributes = readAttributes(attributesCount);

		return new Attributes(attributes);
	}

	private List<Attribute> readAttributes(int count) throws IOException {
		List<Attribute> fields = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			fields.add(readAttribute());
		}

		return fields;
	}

	private Attribute readAttribute() throws IOException {
		ConstantPoolIndex attributeNameIndex = readFrom(classFileReader);
		Utf8Constant attributeName = constantPool.getUtf8(attributeNameIndex);

		AttributeType type = AttributeType.fromAttributeName(attributeName.asString());

		switch (type) {
			case SIGNATURE:
				return readSignatureAttribute();

			case RUNTIME_VISIBLE_ANNOTATIONS:
				return readRuntimeVisibleAnnotationsAttribute();

			case SYNTHETIC:
			case UNKNOWN:
				return readUnknownAttribute(attributeNameIndex);
		}

		throw new AssertionError("Unsupported attribute type: " + type + ".");
	}

	private Attribute readRuntimeVisibleAnnotationsAttribute() throws IOException {
		// Read attribute length (u4 field) - we ignore this value in the decompiler.
		classFileReader.readInt();

		int numberOfAnnotations = classFileReader.readUnsignedShort();

		List<Annotation> annotations = new ArrayList<>();
		for (int i = 0; i < numberOfAnnotations; i++) {
			annotations.add(readAnnotation());
		}

		return new RuntimeVisibleAnnotationsAttribute(annotations);
	}

	private Annotation readAnnotation() {
		return new Annotation();
	}

	private SignatureAttribute readSignatureAttribute() throws IOException {
		int attributeLength = classFileReader.readInt();
		Preconditions.checkState(attributeLength == 2,
				"Signature attribute data must be a u2 constant pool index.");

		ConstantPoolIndex utf8SignatureIndex = readFrom(classFileReader);

		return new SignatureAttribute(utf8SignatureIndex);
	}

	private UnknownAttribute readUnknownAttribute(ConstantPoolIndex attributeNameIndex) throws IOException {
		int attributeLength = classFileReader.readInt();
		byte[] attributeData = classFileReader.readBytes(attributeLength);

		return new UnknownAttribute(attributeNameIndex, attributeData);
	}
}
