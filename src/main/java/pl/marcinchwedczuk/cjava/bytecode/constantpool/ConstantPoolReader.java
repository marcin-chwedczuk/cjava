package pl.marcinchwedczuk.cjava.bytecode.constantpool;

import pl.marcinchwedczuk.cjava.bytecode.utils.ClassFileReader;
import pl.marcinchwedczuk.cjava.bytecode.utils.JavaClassFileUtf8Decoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex.readFrom;

public class ConstantPoolReader {

	private final ConstantTagMapper constantTagMapper;

	public ConstantPoolReader() {
		this.constantTagMapper = new ConstantTagMapper();
	}

	public ConstantPool readConstantPool(ClassFileReader classFileBytes) throws IOException {
		int constantPoolCount = Short.toUnsignedInt(classFileBytes.readShort());
		List<Constant> constants = readConstants(classFileBytes, constantPoolCount);

		return new ConstantPool(constantPoolCount, constants);
	}

	private List<Constant> readConstants(ClassFileReader classFileBytes, int constantPoolCount) throws IOException {
		List<Constant> constants = new ArrayList<>();

		// Constant #0 is reserved and is not present in class file
		for (int i = 1; i < constantPoolCount; i++) {
			constants.add(readConstant(classFileBytes));
		}

		return constants;
	}

	public Constant readConstant(ClassFileReader classFileReader) throws IOException {
		byte byteCodeTag = classFileReader.readByte();

		ConstantTag tag = constantTagMapper
				.mapByteCodeTagToConstantTag(byteCodeTag);

		switch (tag) {
			case CLASS:
				return readClassConstant(classFileReader);

			case FIELD_REF:
				return readFieldRefConstant(classFileReader);

			case METHOD_REF:
				return readMethodRefConstant(classFileReader);

			case INTERFACE_METHOD_REF:
				break;
			case STRING:
				return readStringConstant(classFileReader);

			case INTEGER:
				return readIntegerConstant(classFileReader);

			case FLOAT:
				return readFloatConstant(classFileReader);

			case LONG:
				break;
			case DOUBLE:
				break;

			case NAME_AND_TYPE:
				return readNameAndTypeConstant(classFileReader);

			case UTF8:
				return readUtf8Constant(classFileReader);

			case METHOD_HANDLE:
				break;
			case METHOD_TYPE:
				break;
			case INVOKE_DYNAMIC:
				break;
		}

		throw new AssertionError("Unsupported tag value: " + tag + ".");
	}

	private Constant readClassConstant(ClassFileReader classFileReader) throws IOException {
		ConstantPoolIndex nameIndex = readFrom(classFileReader);
		return new ClassConstant(nameIndex);
	}

	private Constant readFieldRefConstant(ClassFileReader classFileReader) throws IOException {
		ConstantPoolIndex classIndex = readFrom(classFileReader);
		ConstantPoolIndex nameAndTypeIndex = readFrom(classFileReader);

		return new FieldRefConstant(classIndex, nameAndTypeIndex);
	}

	private Constant readMethodRefConstant(ClassFileReader classFileReader) throws IOException {
		ConstantPoolIndex classIndex = readFrom(classFileReader);
		ConstantPoolIndex nameAndTypeIndex = readFrom(classFileReader);

		return new MethodRefConstant(classIndex, nameAndTypeIndex);
	}

	private Constant readStringConstant(ClassFileReader bytes) throws IOException {
		ConstantPoolIndex utf8Index = readFrom(bytes);

		return new StringConstant(utf8Index);
	}

	private Constant readIntegerConstant(ClassFileReader classFileReader) throws IOException {
		int value = classFileReader.readInt();
		return new IntegerConstant(value);
	}

	private Constant readFloatConstant(ClassFileReader classFileReader) throws IOException {
		float value = classFileReader.readFloat();
		return new FloatConstant(value);
	}

	private Constant readNameAndTypeConstant(ClassFileReader classFileReader) throws IOException {
		ConstantPoolIndex nameIndex = readFrom(classFileReader);
		ConstantPoolIndex descriptorIndex = readFrom(classFileReader);

		return new NameAndTypeConstant(nameIndex, descriptorIndex);
	}

	private Constant readUtf8Constant(ClassFileReader classFileReader) throws IOException {
		int length = classFileReader.readUnsignedShort();

		byte[] bytes = classFileReader.readBytes(length);
		String decodedString = JavaClassFileUtf8Decoder.decode(bytes);

		return new Utf8Constant(bytes, decodedString);
	}
}
