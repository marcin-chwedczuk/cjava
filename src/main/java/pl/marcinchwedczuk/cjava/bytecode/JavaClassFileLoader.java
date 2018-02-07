package pl.marcinchwedczuk.cjava.bytecode;

import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPool;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolReader;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class JavaClassFileLoader {
	private final ConstantPoolReader constantPoolReader;

	public JavaClassFileLoader() {
		this.constantPoolReader = new ConstantPoolReader();
	}

	public JavaClassFile load(byte[] classFileBytes) throws IOException {
		JavaClassFile classFile = new JavaClassFile();

		DataInputStream classFileBytesIS = new DataInputStream(
				new ByteArrayInputStream(classFileBytes));

		readHeaders(classFileBytesIS, classFile);

		ConstantPool constantPool =
				constantPoolReader.readConstantPool(classFileBytesIS);
		classFile.setConstantPool(constantPool);

		return classFile;
	}

	private void readHeaders(DataInputStream bytes, JavaClassFile classFile) throws IOException {
		classFile.setMagicNumber(bytes.readInt());

		classFile.setMinorVersion(bytes.readShort());
		classFile.setMajorVersion(bytes.readShort());
	}

}
