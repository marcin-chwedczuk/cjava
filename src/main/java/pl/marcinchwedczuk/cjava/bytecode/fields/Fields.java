package pl.marcinchwedczuk.cjava.bytecode.fields;

import com.google.common.collect.Lists;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPool;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;

import java.util.Collections;
import java.util.List;

public class Fields {
	private final int count;
	private final List<FieldInfo> fields;

	public Fields(int count, List<FieldInfo> fields) {
		this.count = count;
		this.fields =
				Collections.unmodifiableList(Lists.newArrayList(fields));
	}

	public int getCount() {
		return count;
	}

	public FieldInfo get(int index) {
		return fields.get(index);
	}
}
