package pl.marcinchwedczuk.cjava.bytecode.constantpool;

public abstract class Constant {
	private final ConstantTag tag;

	protected Constant(ConstantTag tag) {
		this.tag = tag;
	}

	public ConstantTag getTag() {
		return tag;
	}
}
