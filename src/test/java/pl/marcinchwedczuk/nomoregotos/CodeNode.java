package pl.marcinchwedczuk.nomoregotos;

public class CodeNode extends CfgNode {
	public final String instruction;

	public CodeNode(String instruction) {
		this.instruction = instruction;
	}

	@Override
	public String toString() {
		return instruction;
	}
}
