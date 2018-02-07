package pl.marcinchwedczuk.cjava.bytecode.constantpool;

import com.google.common.io.ByteStreams;
import pl.marcinchwedczuk.cjava.bytecode.InvalidJavaClassFileException;
import pl.marcinchwedczuk.cjava.bytecode.utils.JavaClassFileUtf8Decoder;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConstantPoolReader {

	private final ConstantTagMapper constantTagMapper;

	public ConstantPoolReader() {
		this.constantTagMapper = new ConstantTagMapper();
	}

	public ConstantPool readConstantPool(DataInputStream classFileBytes) throws IOException {
		short constantPoolCount = classFileBytes.readShort();
		List<Constant> constants = readConstants(classFileBytes, constantPoolCount);

		return new ConstantPool(constantPoolCount, constants);
	}

	private List<Constant> readConstants(DataInputStream classFileBytes, short constantPoolCount) throws IOException {
		List<Constant> constants = new ArrayList<>();

		// Constant #0 is reserved and is not present in class file
		for (int i = 1; i < constantPoolCount; i++) {
			constants.add(readConstant(classFileBytes));
		}

		return constants;
	}

	public Constant readConstant(DataInputStream bytes) throws IOException {
		byte byteCodeTagConstant = bytes.readByte();

		ConstantTag tag = constantTagMapper
				.mapByteCodeTagConstantToConstantTag(byteCodeTagConstant);

		switch (tag) {
			case CLASS:
				return readClassConstant(bytes);

			case FIELD_REF:
				break;

			case METHOD_REF:
				return readMethodRefConstant(bytes);

			case INTERFACE_METHOD_REF:
				break;
			case STRING:
				break;
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

		throw new AssertionError("Cannot happen.");
	}

	private Constant readNameAndTypeConstant(DataInputStream bytes) throws IOException {
		short nameIndex = bytes.readShort();
		short descriptorIndex = bytes.readShort();

		return new NameAndTypeConstant(nameIndex, descriptorIndex);
	}

	private Constant readUtf8Constant(DataInputStream bytes) throws IOException {
		short length = bytes.readShort();

		byte[] buffer = new byte[length];
		int bytesRead = ByteStreams.read(bytes, buffer, 0, buffer.length);

		if (bytesRead != length) {
			throw new InvalidJavaClassFileException(
					"Unexpected end of class file while reading " +
					"constant pool UTF8 constant.");
		}

		String decodedString = JavaClassFileUtf8Decoder.decode(buffer);

		return new Utf8Constant(buffer, decodedString);
	}

	private Constant readClassConstant(DataInputStream bytes) throws IOException {
		short nameIndex = bytes.readShort();
		return new ClassConstant(nameIndex);
	}

	private Constant readMethodRefConstant(DataInputStream bytes) throws IOException {
		short classIndex = bytes.readShort();
		short nameAndTypeIndex = bytes.readShort();

		return new MethodRefConstant(classIndex, nameAndTypeIndex);
	}
}
