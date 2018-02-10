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

	public Constant readConstant(ClassFileReader bytes) throws IOException {
		byte byteCodeTagConstant = bytes.readByte();

		ConstantTag tag = constantTagMapper
				.mapByteCodeTagConstantToConstantTag(byteCodeTagConstant);

		switch (tag) {
			case CLASS:
				return readClassConstant(bytes);

			case FIELD_REF:
				return readFieldRefConstant(bytes);

			case METHOD_REF:
				return readMethodRefConstant(bytes);

			case INTERFACE_METHOD_REF:
				break;
			case STRING:
				return readStringConstant(bytes);
			case INTEGER:
				break;
			case FLOAT:
				break;
			case LONG:
				break;
			case DOUBLE:
				break;

			case NAME_AND_TYPE:
				return readNameAndTypeConstant(bytes);

			case UTF8:
				return readUtf8Constant(bytes);

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
