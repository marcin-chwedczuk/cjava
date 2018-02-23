package pl.marcinchwedczuk.cjava.decompiler.controlflow;

public class FlowGraph {
	public final FlowBlock start;
	public final FlowBlock stop;

	public FlowGraph(FlowBlock start, FlowBlock stop) {
		this.start = start;
		this.stop = stop;
	}
}
