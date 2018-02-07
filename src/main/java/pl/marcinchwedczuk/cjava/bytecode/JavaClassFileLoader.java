package pl.marcinchwedczuk.cjava.bytecode;

import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPool;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolReader;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.EnumSet;

import static pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex.fromUnsignedShort;

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
		readThisAndSuperClass(classFileBytesIS, classFile);

		return classFile;
	}

	private void readThisAndSuperClass(DataInputStream classFileBytesIS, JavaClassFile classFile) throws IOException {
		ConstantPoolIndex thisClass = fromUnsignedShort(classFileBytesIS.readShort());
		classFile.setThisClass(thisClass);

		ConstantPoolIndex superClass = fromUnsignedShort(classFileBytesIS.readShort());
		classFile.setSuperClass(superClass);
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
