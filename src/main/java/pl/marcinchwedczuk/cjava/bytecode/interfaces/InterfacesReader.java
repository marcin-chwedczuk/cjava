package pl.marcinchwedczuk.cjava.bytecode.interfaces;

import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndexes;
import pl.marcinchwedczuk.cjava.bytecode.utils.ClassFileReader;

import java.io.IOException;
import java.util.List;

public class InterfacesReader {
	public Interfaces readInterfaces(ClassFileReader classFileReader) throws IOException {
		int count = classFileReader.readUnsignedShort();

		List<ConstantPoolIndex> interfaces =
				ConstantPoolIndexes.read(classFileReader, count);

		return new Interfaces(count, interfaces);
	}
}
