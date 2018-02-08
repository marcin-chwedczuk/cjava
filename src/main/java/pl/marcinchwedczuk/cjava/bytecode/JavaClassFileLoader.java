package pl.marcinchwedczuk.cjava.bytecode;

import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPool;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolReader;
import pl.marcinchwedczuk.cjava.bytecode.fields.Fields;
import pl.marcinchwedczuk.cjava.bytecode.fields.FieldsReader;
import pl.marcinchwedczuk.cjava.bytecode.interfaces.Interfaces;
import pl.marcinchwedczuk.cjava.bytecode.interfaces.InterfacesReader;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.EnumSet;

import static pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex.fromUnsignedShort;

public class JavaClassFileLoader {
	private final ConstantPoolReader constantPoolReader;
	private final FlagsEnumMapper accessFlagMapper;
	private final InterfacesReader interfacesReader;
	private final FieldsReader fieldsReader;

	public JavaClassFileLoader() {
		this.constantPoolReader = new ConstantPoolReader();
		this.accessFlagMapper = new FlagsEnumMapper();
		this.interfacesReader = new InterfacesReader();
		this.fieldsReader = new FieldsReader();
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

		Interfaces interfaces =
			interfacesReader.readInterfaces(classFileBytesIS);
		classFile.setInterfaces(interfaces);

		Fields classFields = fieldsReader.readFields(classFileBytesIS);
		classFile.setClassFields(classFields);

		return classFile;
	}

	private void readHeaders(DataInputStream bytes, JavaClassFile classFile) throws IOException {
		classFile.setMagicNumber(bytes.readInt());

		classFile.setMinorVersion(bytes.readShort());
		classFile.setMajorVersion(bytes.readShort());
	}

	private void readAccessFlags(DataInputStream classFileBytesIS, JavaClassFile classFile) throws IOException {
		short bitFieldsAccessFlags = classFileBytesIS.readShort();

		EnumSet<AccessFlag> accessFlags =
				accessFlagMapper.mapToFlags(bitFieldsAccessFlags, AccessFlag.class);

		classFile.setAccessFlags(accessFlags);
	}

	private void readThisAndSuperClass(DataInputStream classFileBytesIS, JavaClassFile classFile) throws IOException {
		ConstantPoolIndex thisClass = fromUnsignedShort(classFileBytesIS.readShort());
		classFile.setThisClass(thisClass);

		ConstantPoolIndex superClass = fromUnsignedShort(classFileBytesIS.readShort());
		classFile.setSuperClass(superClass);
	}
}
