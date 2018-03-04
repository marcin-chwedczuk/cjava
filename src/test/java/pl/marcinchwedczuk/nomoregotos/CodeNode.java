package pl.marcinchwedczuk.nomoregotos;

public class CodeNode extends Node {
	public final String instruction;

	public CodeNode(String instruction) {
		this.instruction = instruction;
	}

	@Override
	public String toString() {
		return instruction;
	}
}
