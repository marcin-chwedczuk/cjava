package pl.marcinchwedczuk.cjava.bytecode.constantpool;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConstantPool {
	private final int constantPoolCount;
	private final List<Constant> constants;

	public ConstantPool(int constantPoolCount, List<Constant> constants) {
		this.constantPoolCount = constantPoolCount;

		this.constants =
				Collections.unmodifiableList(Lists.newArrayList(constants));
	}

	public int getCount() {
		return constantPoolCount;
	}

	public List<Constant> getConstants() {
		return constants;
	}

	public <T extends Constant> T get(int index, Class<T> klass) {
		// Constant #0 is not present among constants
		// but count when computing index
		Constant constant = constants.get(index - 1);
		return klass.cast(constant);
	}

	public MethodRefConstant getMethodRef(int index) {
		return get(index, MethodRefConstant.class);
	}

	public ClassConstant getClass(int index) {
		return get(index, ClassConstant.class);
	}

	public Utf8Constant getUtf8(int index) {
		return get(index, Utf8Constant.class);
	}

	public NameAndTypeConstant getNameAndType(int index) {
		return get(index, NameAndTypeConstant.class);
	}
}
