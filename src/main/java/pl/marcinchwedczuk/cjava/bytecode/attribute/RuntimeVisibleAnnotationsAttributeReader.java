package pl.marcinchwedczuk.cjava.bytecode.attribute;

import pl.marcinchwedczuk.cjava.bytecode.attribute.RuntimeVisibleAnnotationsAttribute.*;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPool;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;
import pl.marcinchwedczuk.cjava.bytecode.utils.ClassFileReader;
import pl.marcinchwedczuk.cjava.decompiler.ConstantPoolHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex.readFrom;

public class RuntimeVisibleAnnotationsAttributeReader {
	private final ClassFileReader classFileReader;
	private final ConstantPoolHelper cp;

	public RuntimeVisibleAnnotationsAttributeReader(ClassFileReader classFileReader, ConstantPool constantPool) {
		this.classFileReader = requireNonNull(classFileReader);
		this.cp = new ConstantPoolHelper(constantPool);
	}

	public RuntimeVisibleAnnotationsAttribute read() throws IOException {
		// Read attribute length (u4 field) - we ignore this value in the decompiler.
		classFileReader.readInt();

		int numberOfAnnotations = classFileReader.readUnsignedShort();

		List<Annotation> annotations = new ArrayList<>();
		for (int i = 0; i < numberOfAnnotations; i++) {
			annotations.add(readAnnotation());
		}

		return new RuntimeVisibleAnnotationsAttribute(annotations);
	}

	private Annotation readAnnotation() throws IOException {
		ConstantPoolIndex type = readFrom(classFileReader);

		int numberOfElementValuePairs = classFileReader.readUnsignedShort();
		List<ElementValuePair> pairs = new ArrayList<>();

		for (int i = 0; i < numberOfElementValuePairs; i++) {
			ElementValuePair pair = readElementValuePair();
			pairs.add(pair);
		}

		return new Annotation(type, pairs);
	}

	private ElementValuePair readElementValuePair() throws IOException {
		ConstantPoolIndex elementName = readFrom(classFileReader);
		ElementValue elementValue = readElementValue();

		return new ElementValuePair(elementName, elementValue);
	}

	private ElementValue readElementValue() throws IOException {
		char bytecodeTag = classFileReader.readAsciiCharacter();
		ElementValueTag tag = ElementValueTag.fromBytecodeTag(bytecodeTag);

		switch (tag) {
			case BYTE: case CHAR:
			case DOUBLE: case FLOAT:
			case INT: case LONG:
			case SHORT: case BOOLEAN:
			case STRING: {
				ConstantPoolIndex constant = readFrom(classFileReader);
				return ElementValue.forConstant(tag, constant);
			}

			case CLASS: {
				ConstantPoolIndex classInfo = readFrom(classFileReader);
				return ElementValue.forClassConstant(classInfo);
			}

			case ANNOTATION: {
				Annotation annotation = readAnnotation();
				return ElementValue.forAnnotation(annotation);
			}

			case ARRAY:
				return ElementValue.forArray(readArrayElementValue());

			case ENUM: {
				ConstantPoolIndex enumTypeName = readFrom(classFileReader);
				ConstantPoolIndex enumConstantFieldName = readFrom(classFileReader);

				return ElementValue.forEnum(new EnumConstValue(enumTypeName, enumConstantFieldName));
			}

		}

		throw new AssertionError("Unknown element_value tag: " + bytecodeTag + ".");
	}

	private ArrayValue readArrayElementValue() throws IOException {
		int count = classFileReader.readUnsignedShort();

		List<ElementValue> values = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			ElementValue value = readElementValue();
			values.add(value);
		}


		return new ArrayValue(values);
	}
}
