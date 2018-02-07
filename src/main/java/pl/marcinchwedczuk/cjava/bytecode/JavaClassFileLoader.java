package pl.marcinchwedczuk.cjava.bytecode;

import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPool;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolReader;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.EnumSet;

public class JavaClassFileLoader {
	private final ConstantPoolReader constantPoolReader;
	private final AccessFlagMapper accessFlagMapper;

	public JavaClassFileLoader() {
		this.constantPoolReader = new ConstantPoolReader();
		this.accessFlagMapper = new AccessFlagMapper();
	}

	public JavaClassFile load(byte[] classFileBytes) throws IOException {
		JavaClassFile classFile = new JavaClassFile();

		DataInputStream classFileBytesIS = new DataInputStream(
				new ByteArrayInputStream(classFileBytes));

		readHeaders(classFileBytesIS, classFile);

		ConstantPool constantPool =
				constantPoolReader.readConstantPool(classFileBytesIS);
		classFile.setConstantPool(constantPool);

		readAccessFlags(classFileBytesIS, classFile);

		return classFile;
	}

	private void readAccessFlags(DataInputStream classFileBytesIS, JavaClassFile classFile) throws IOException {
		short bitFieldsAccessFlags = classFileBytesIS.readShort();

		EnumSet<AccessFlag> accessFlags =
				accessFlagMapper.mapBitFieldsAccessFlagsToAccessFlagEnumSet(bitFieldsAccessFlags);

		classFile.setAccessFlags(accessFlags);
	}

	private void readHeaders(DataInputStream bytes, JavaClassFile classFile) throws IOException {
		classFile.setMagicNumber(bytes.readInt());

		classFile.setMinorVersion(bytes.readShort());
		classFile.setMajorVersion(bytes.readShort());
	}

}
