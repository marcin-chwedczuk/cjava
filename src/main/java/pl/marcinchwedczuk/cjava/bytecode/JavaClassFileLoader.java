package pl.marcinchwedczuk.cjava.bytecode;

import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPool;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolReader;
import pl.marcinchwedczuk.cjava.bytecode.fields.Fields;
import pl.marcinchwedczuk.cjava.bytecode.fields.FieldsReader;
import pl.marcinchwedczuk.cjava.bytecode.interfaces.Interfaces;
import pl.marcinchwedczuk.cjava.bytecode.interfaces.InterfacesReader;
import pl.marcinchwedczuk.cjava.bytecode.utils.ClassFileReader;

import java.io.IOException;
import java.util.EnumSet;

import static pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex.readFrom;

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

		ClassFileReader classFileReader = new ClassFileReader(classFileBytes);

		readHeaders(classFileReader, classFile);

		ConstantPool constantPool =
				constantPoolReader.readConstantPool(classFileReader);
		classFile.setConstantPool(constantPool);

		readAccessFlags(classFileReader, classFile);
		readThisAndSuperClass(classFileReader, classFile);

		Interfaces interfaces =
			interfacesReader.readInterfaces(classFileReader);
		classFile.setInterfaces(interfaces);

		Fields classFields = fieldsReader.readFields(classFileReader);
		classFile.setClassFields(classFields);

		return classFile;
	}

	private void readHeaders(ClassFileReader classFileReader, JavaClassFile classFile) throws IOException {
		classFile.setMagicNumber(classFileReader.readInt());

		classFile.setMinorVersion(classFileReader.readShort());
		classFile.setMajorVersion(classFileReader.readShort());
	}

	private void readAccessFlags(ClassFileReader classFileReader, JavaClassFile classFile) throws IOException {
		int bitFieldsAccessFlags = classFileReader.readUnsignedShort();

		EnumSet<AccessFlag> accessFlags =
				accessFlagMapper.mapToFlags(bitFieldsAccessFlags, AccessFlag.class);

		classFile.setAccessFlags(accessFlags);
	}

	private void readThisAndSuperClass(ClassFileReader classFileReader, JavaClassFile classFile) throws IOException {
		ConstantPoolIndex thisClass = readFrom(classFileReader);
		classFile.setThisClass(thisClass);

		ConstantPoolIndex superClass = readFrom(classFileReader);
		classFile.setSuperClass(superClass);
	}
}
