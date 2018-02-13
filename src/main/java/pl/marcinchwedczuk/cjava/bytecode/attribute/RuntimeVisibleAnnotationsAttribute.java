package pl.marcinchwedczuk.cjava.bytecode.attribute;

import pl.marcinchwedczuk.cjava.bytecode.InvalidJavaClassFileException;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPool;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static pl.marcinchwedczuk.cjava.util.ListUtils.readOnlyCopy;

public class RuntimeVisibleAnnotationsAttribute extends Attribute {
	private final List<Annotation> annotations;

	public RuntimeVisibleAnnotationsAttribute(List<Annotation> annotations) {
		super(AttributeType.RUNTIME_VISIBLE_ANNOTATIONS);

		this.annotations = readOnlyCopy(annotations);
	}

	public List<Annotation> getAnnotations() {
		return annotations;
	}

	public static class Annotation {
		private final ConstantPoolIndex type;
		private final List<ElementValuePair> elementValuePairs;

		public Annotation(ConstantPoolIndex type, List<ElementValuePair> elementValuePairs) {
			this.type = requireNonNull(type);
			this.elementValuePairs = readOnlyCopy(elementValuePairs);
		}

		public ConstantPoolIndex getType() {
			return type;
		}

		public List<ElementValuePair> getElementValuePairs() {
			return elementValuePairs;
		}
	}

	public static class ElementValuePair {
		private final ConstantPoolIndex elementName;
		private final ElementValue value;

		public ElementValuePair(ConstantPoolIndex elementName, ElementValue value) {
			this.elementName = elementName;
			this.value = value;
		}

		public ConstantPoolIndex getElementName() {
			return elementName;
		}

		public ElementValue getValue() {
			return value;
		}
	}

	public static class ElementValue {
		public static ElementValue forConstant(ElementValueTag tag, ConstantPoolIndex constantValue) {
			return new ElementValue(tag, constantValue, null, null, null, null);
		}

		public static ElementValue forEnum(EnumConstValue enumConstnant) {
			return new ElementValue(ElementValueTag.ENUM, null, enumConstnant, null, null, null);
		}

		public static ElementValue forClassConstant(ConstantPoolIndex classConstant) {
			return new ElementValue(ElementValueTag.CLASS, null, null, classConstant, null, null);
		}

		public static ElementValue forAnnotation(Annotation annotation) {
			return new ElementValue(ElementValueTag.ANNOTATION, null, null, null, annotation, null);
		}

		public static ElementValue forArray(ArrayValue arrayValue) {
			return new ElementValue(ElementValueTag.ARRAY, null, null, null, null, arrayValue);
		}

		private final ElementValueTag tag;

		private final ConstantPoolIndex constantValue;
		private final EnumConstValue enumConstnant;
		private final ConstantPoolIndex classConstant;
		private final Annotation annotationValue;
		private final ArrayValue arrayValue;

		public ElementValue(
				ElementValueTag tag,
				ConstantPoolIndex constantValue,
				EnumConstValue enumConstnant,
				ConstantPoolIndex classConstant,
				Annotation annotationValue,
				ArrayValue arrayValue) {
			this.tag = tag;
			this.constantValue = constantValue;
			this.enumConstnant = enumConstnant;
			this.classConstant = classConstant;
			this.annotationValue = annotationValue;
			this.arrayValue = arrayValue;
		}

		public ElementValueTag getTag() {
			return tag;
		}

		public ConstantPoolIndex getConstantValue() {
			return constantValue;
		}

		public EnumConstValue getEnumConstnant() {
			return enumConstnant;
		}

		public ConstantPoolIndex getClassConstant() {
			return classConstant;
		}

		public Annotation getAnnotationValue() {
			return annotationValue;
		}

		public ArrayValue getArrayValue() {
			return arrayValue;
		}
	}

	public enum ElementValueTag {
		BYTE('B'),
		CHAR('C'),
		DOUBLE('D'),
		FLOAT('F'),
		INT('I'),
		LONG('J'),
		SHORT('S'),
		BOOLEAN('Z'),
		STRING('s'),
		ENUM('e'),
		CLASS('c'),
		ANNOTATION('@'),
		ARRAY('[');

		private final char tag;

		ElementValueTag(char tag) {
			this.tag = tag;
		}

		public static ElementValueTag fromBytecodeTag(char bytecodeTag) {
			for (ElementValueTag elementValue : values()) {
				if (elementValue.tag == bytecodeTag) {
					return elementValue;
				}
			}

			throw new InvalidJavaClassFileException(
					"Unknown annotation element value tag: " + bytecodeTag + ".");
		}
	}

	public static class EnumConstValue {
		private final ConstantPoolIndex typeName;
		private final ConstantPoolIndex constantName;

		public EnumConstValue(ConstantPoolIndex typeName, ConstantPoolIndex constantName) {
			this.typeName = typeName;
			this.constantName = constantName;
		}

		public ConstantPoolIndex getTypeName() {
			return typeName;
		}

		public ConstantPoolIndex getConstantName() {
			return constantName;
		}
	}

	public static class ArrayValue {
		private final List<ElementValue> values;

		public ArrayValue(List<ElementValue> values) {
			this.values = readOnlyCopy(values);
		}

		public List<ElementValue> getValues() {
			return values;
		}
	}
}
