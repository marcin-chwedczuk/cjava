package pl.marcinchwedczuk.cjava.bytecode.constantpool;

import pl.marcinchwedczuk.cjava.bytecode.utils.ClassFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex.readFrom;

public class ConstantPoolIndexes {
	private ConstantPoolIndexes() { }

	public static List<ConstantPoolIndex> read(ClassFileReader classFileReader, int count) throws IOException {
		List<ConstantPoolIndex> indexes = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			ConstantPoolIndex interfaceType = readFrom(classFileReader);
			indexes.add(interfaceType);
		}

		return indexes;
	}
}
