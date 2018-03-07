package pl.marcinchwedczuk.nomoregotos.graph;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import pl.marcinchwedczuk.nomoregotos.CfgEdge;
import pl.marcinchwedczuk.nomoregotos.CfgNode;
import pl.marcinchwedczuk.nomoregotos.ConditionNode;
import pl.marcinchwedczuk.nomoregotos.ControlFlowGraph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import static com.google.common.base.Charsets.UTF_8;

public class DirectedGraphVizHelper {
	private final DirectedGraph<?,?> graph;
	private final String fileName;

	public DirectedGraphVizHelper(DirectedGraph<?,?> graph, String fileName) {
		this.graph = graph;
		this.fileName = fileName;
	}

	public void saveToDotFile() throws IOException {
		try (BufferedWriter writer = Files.newWriter(new File(fileName), UTF_8)) {
			writer.write("digraph G {");
			writer.newLine();

			// write vertices
			for (Node block : graph.getNodes()) {
				writer.write(block.toString());
				writer.write("[label=\"" + block.toString() + "\", style=filled, fillcolor=\"" + "gray" + "\"]");
				writer.newLine();
			}

			// write edges
			for (Edge e : graph.getEdges()) {
					writer.write(e.getFrom().toString());
					writer.write(" -> ");
					writer.write(e.getTo().toString());

					writer.write("[label=\"" + e.toString() + "\"]");
					writer.newLine();
			}

			writer.write("}");
			writer.newLine();
		}
	}
}
