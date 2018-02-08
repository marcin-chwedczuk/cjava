package pl.marcinchwedczuk.cjava.bytecode.constantpool;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex.fromUnsignedShort;

public class ConstantPoolIndexes {
	private ConstantPoolIndexes() { }

	public static List<ConstantPoolIndex> read(DataInputStream classFileBytes, int count) throws IOException {
		List<ConstantPoolIndex> indexes = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			ConstantPoolIndex interfaceType =
					fromUnsignedShort(classFileBytes.readShort());

			indexes.add(interfaceType);
		}

		return indexes;
	}
}
