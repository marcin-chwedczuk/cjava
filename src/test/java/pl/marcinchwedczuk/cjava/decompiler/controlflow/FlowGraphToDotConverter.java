package pl.marcinchwedczuk.cjava.decompiler.controlflow;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FlowGraphToDotConverter {
	private final FlowGraph graph;
	private final String fileName;

	public FlowGraphToDotConverter(FlowGraph graph, String fileName) {
		this.graph = graph;
		this.fileName = fileName;
	}

	public void saveToDotFile() throws IOException {
		try (BufferedWriter writer = Files.newWriter(new File(fileName), Charsets.UTF_8)) {
			writer.write("digraph G {");
			writer.newLine();

			// write vertices
			for (FlowBlock block : graph.blocks) {
				writer.write(getName(block));

				if (block != graph.start && block != graph.stop) {
					writer.write("[label=\"" + getName(block) + "\\n\\n");
					for (FlowElement element : block.getElements()) {
						writer.write(element.toString());
						writer.write("\\n");
					}
					writer.write("\"]");
				}

				writer.newLine();
			}

			// write edges
			for (FlowBlock block : graph.blocks) {
				for (FlowTransition transition : block.getOutgoing()) {
					writer.write(getName(block));
					writer.write(" -> ");
					writer.write(getName(transition.getToBlock()));

					writer.write("[label=\"" + transition.getCondition() + "\"]");

					writer.newLine();
				}
			}

			writer.write("}");
			writer.newLine();
		}
	}

	private String getName(FlowBlock block) {
		if (block == graph.start) {
			return "START";
		}

		if (block == graph.stop) {
			return "STOP";
		}

		String blockStart = block.getFirstInstruction().getPC().toString();
		String blockEnd = block.getLastInstruction().getPC().toString();

		return "B" + blockStart + "_" + blockEnd;
	}
}
