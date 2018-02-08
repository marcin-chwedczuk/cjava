package pl.marcinchwedczuk.cjava.bytecode.interfaces;

import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndexes;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex.fromUnsignedShort;

public class InterfacesReader {
	public Interfaces readInterfaces(DataInputStream classFileBytes) throws IOException {
		int count = Short.toUnsignedInt(classFileBytes.readShort());

		List<ConstantPoolIndex> interfaces =
				ConstantPoolIndexes.read(classFileBytes, count);

		return new Interfaces(count, interfaces);
	}
}
