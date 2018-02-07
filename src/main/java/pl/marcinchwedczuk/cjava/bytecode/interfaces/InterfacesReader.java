package pl.marcinchwedczuk.cjava.bytecode.interfaces;

import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex.fromUnsignedShort;

public class InterfacesReader {
	public Interfaces readInterfaces(DataInputStream classFileBytes) throws IOException {
		int count = Short.toUnsignedInt(classFileBytes.readShort());
		List<ConstantPoolIndex> interfaces = readInterfaces(classFileBytes, count);

		return new Interfaces(count, interfaces);
	}

	private List<ConstantPoolIndex> readInterfaces(DataInputStream classFileBytes, int count) throws IOException {
		List<ConstantPoolIndex> interfaces = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			ConstantPoolIndex interfaceType = fromUnsignedShort(classFileBytes.readShort());
			interfaces.add(interfaceType);
		}

		return interfaces;
	}
}
