package pl.marcinchwedczuk.cjava.bytecode.method;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class Methods {
	private final int count;
	private final List<MethodInfo> methods;

	public Methods(int count, List<MethodInfo> methods) {
		this.count = count;
		this.methods = Collections.unmodifiableList(Lists.newArrayList(methods));
	}

	public int getCount() {
		return count;
	}

	public MethodInfo get(int index) {
		return methods.get(index);
	}
}
