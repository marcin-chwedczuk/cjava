package pl.marcinchwedczuk.cjava.bytecode.interfaces;

import com.google.common.collect.Lists;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;

import java.util.Collections;
import java.util.List;

public class Interfaces {
	private final int count;
	private final List<ConstantPoolIndex> interfaces;

	public Interfaces(int count, List<ConstantPoolIndex> interfaces) {
		this.count = count;
		this.interfaces =
				Collections.unmodifiableList(Lists.newArrayList(interfaces));
	}

	public int getCount() {
		return count;
	}

	public List<ConstantPoolIndex> getList() {
		return interfaces;
	}
}
