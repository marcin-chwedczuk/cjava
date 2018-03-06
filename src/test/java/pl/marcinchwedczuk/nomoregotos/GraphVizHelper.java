package pl.marcinchwedczuk.nomoregotos;

import com.google.common.base.Strings;
import com.google.common.io.Files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import static com.google.common.base.Charsets.UTF_8;

public class GraphVizHelper {
	private final ControlFlowGraph graph;
	private final String fileName;

	public GraphVizHelper(ControlFlowGraph graph, String fileName) {
		this.graph = graph;
		this.fileName = fileName;
	}

	public void saveToDotFile() throws IOException {
		try (BufferedWriter writer = Files.newWriter(new File(fileName), UTF_8)) {
			writer.write("digraph G {");
			writer.newLine();

			// write vertices
			for (CfgNode block : graph.getVeriticesWithStartStopMarks()) {
				writer.write(block.toString());

				String color = (block instanceof ConditionNode)
						? "#bbbbbb"
						: "#ffffff";

				color = block.color == null
						? color
						: block.color;

				String extraLabel = Strings.isNullOrEmpty(block.extraLabel)
						? ""
						: "\\n" + block.extraLabel;

				writer.write("[label=\"" + block.toString() + extraLabel + "\", style=filled, fillcolor=\"" + color + "\"]");
				writer.newLine();
			}

			// write edges
			for (CfgEdge e : graph.getEdges()) {
					writer.write(e.from.toString());
					writer.write(" -> ");
					writer.write(e.to.toString());

					String color = e.backedge
							? "red"
							: "black";

					writer.write("[label=\"" + e.toString() + "\", color=\"" + color + "\"]");

					writer.newLine();
			}

			writer.write("}");
			writer.newLine();
		}
	}
}
