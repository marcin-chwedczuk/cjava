package pl.marcinchwedczuk.cjava.decompiler.controlflow;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class FlowGraph {
	public final FlowBlock start;
	public final FlowBlock stop;

	public final List<FlowBlock> blocks;

	public FlowGraph(FlowBlock start, FlowBlock stop) {
		this.start = start;
		this.stop = stop;

		this.blocks = Lists.newArrayList(start, stop);
	}

	public void addBlock(FlowBlock block) {
		blocks.add(block);
	}
}
