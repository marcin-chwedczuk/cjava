package pl.marcinchwedczuk.cjava.bytecode.interfaces;

import com.google.common.collect.Lists;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;

import java.util.Collections;
import java.util.List;

public class Interfaces {
	private final int count;

	// Indexes should point to ClassConstants
	private final List<ConstantPoolIndex> classes;

	public Interfaces(int count, List<ConstantPoolIndex> classes) {
		this.count = count;
		this.classes =
				Collections.unmodifiableList(Lists.newArrayList(classes));
	}

	public int getCount() {
		return count;
	}

	public List<ConstantPoolIndex> getClasses() {
		return classes;
	}
}
