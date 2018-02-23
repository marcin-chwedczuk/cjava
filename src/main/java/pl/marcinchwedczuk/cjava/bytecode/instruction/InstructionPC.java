package pl.marcinchwedczuk.cjava.bytecode.instruction;

public class InstructionPC {
	private final int pc;

	public InstructionPC(int pc) {
		this.pc = pc;
	}

	public InstructionPC addOffset(int offset) {
		return new InstructionPC(pc + offset);
	}

	public int asInteger() {
		return pc;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		InstructionPC that = (InstructionPC) o;
		return pc == that.pc;
	}

	@Override
	public int hashCode() {
		return pc;
	}

	@Override
	public String toString() {
		return Integer.toString(pc);
	}
}
