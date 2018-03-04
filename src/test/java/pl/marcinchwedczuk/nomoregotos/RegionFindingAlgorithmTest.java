package pl.marcinchwedczuk.nomoregotos;

import org.junit.Test;

import static pl.marcinchwedczuk.nomoregotos.Condition.ALWAYS;
import static pl.marcinchwedczuk.nomoregotos.Condition.WHEN_FALSE;
import static pl.marcinchwedczuk.nomoregotos.Condition.WHEN_TRUE;

public class RegionFindingAlgorithmTest {
	@Test
	public void canFindRegions() throws Exception {
		ControlFlowGraph g = buildR2Graph();

		DfsBackEdgeDetectionAndTopologicalSort tmp = new DfsBackEdgeDetectionAndTopologicalSort(g);
		tmp.start();

		ComputeDominatedNodes d = new ComputeDominatedNodes(g, g.findNode("b1"));
		d.compute();

		d.getDominatedNodes().forEach(node -> {
			node.color = "green";
		});

		d.getRegionSuccessor().forEach(node -> {
			node.color = "red";
		});

		/*for (Node node : tmp.getTopologicalOrder()) {
			System.out.println(node);
		}*/

		new GraphVizHelper(g, "/home/mc/tmp/graph.graphviz").saveToDotFile();
	}

	private ControlFlowGraph buildR2Graph() {
		ControlFlowGraph g = new ControlFlowGraph();

		Node A = g.addConditionNode("A");
		g.addEdge(g.start, A, ALWAYS);

		Node n9 = g.addCodeNode("n9");
		g.addEdge(n9, g.stop, ALWAYS);

		// Region R2
		{
			Node b1 = g.addConditionNode("b1");
			Node b2 = g.addConditionNode("b2");
			Node n4 = g.addCodeNode("n4");
			Node n5 = g.addCodeNode("n5");
			Node n6 = g.addCodeNode("n6");
			Node n7 = g.addCodeNode("n7");

			g.addEdge(b1, b2, WHEN_TRUE);
			g.addEdge(b1, n4, WHEN_FALSE);
			g.addEdge(n4, n5, ALWAYS);
			g.addEdge(b2, n6, WHEN_TRUE);
			g.addEdge(b2, n5, WHEN_FALSE);
			g.addEdge(n5, n7, ALWAYS);
			g.addEdge(n6, n7, ALWAYS);
			g.addEdge(n7, n9, ALWAYS);

			g.addEdge(A, b1, WHEN_FALSE);
		}

		return g;
	}

	private ControlFlowGraph buildGraph() {
		ControlFlowGraph g = new ControlFlowGraph();

		Node A = g.addConditionNode("A");
		g.addEdge(g.start, A, ALWAYS);

		Node n9 = g.addCodeNode("n9");
		g.addEdge(n9, g.stop, ALWAYS);

		{
			// Region R1
			Node c1 = g.addConditionNode("c1");
			Node c2 = g.addConditionNode("c2");
			Node c3 = g.addConditionNode("c3");

			Node n1 = g.addCodeNode("n1");
			Node n2 = g.addCodeNode("n2");
			Node n3 = g.addCodeNode("n3");

			g.addEdge(A, c1, WHEN_TRUE);
			g.addEdge(c1, n1, WHEN_TRUE);
			g.addEdge(n1, c1, ALWAYS);
			g.addEdge(c1, c2, WHEN_FALSE);
			g.addEdge(c2, n2, WHEN_TRUE);
			g.addEdge(n2, n9, ALWAYS);
			g.addEdge(c2, n3, WHEN_FALSE);
			g.addEdge(n3, c3, ALWAYS);
			g.addEdge(c3, c1, WHEN_TRUE);
			g.addEdge(c3, n9, WHEN_FALSE);
		}

		// Region R3
		Node d1 = g.addConditionNode("d1");
		{
			Node d2 = g.addConditionNode("d2");
			Node d3 = g.addConditionNode("d3");
			Node n8 = g.addCodeNode("n8");

			g.addEdge(d1, d3, WHEN_TRUE);
			g.addEdge(d1, d2, WHEN_FALSE);
			g.addEdge(d3, n8, WHEN_TRUE);
			g.addEdge(d3, n9, WHEN_FALSE);
			g.addEdge(d2, n8, WHEN_TRUE);
			g.addEdge(d2, n9, WHEN_FALSE);
			g.addEdge(n8, d1, ALWAYS);
		}

		// Region R2
		{
			Node b1 = g.addConditionNode("b1");
			Node b2 = g.addConditionNode("b2");
			Node n4 = g.addCodeNode("n4");
			Node n5 = g.addCodeNode("n5");
			Node n6 = g.addCodeNode("n6");
			Node n7 = g.addCodeNode("n7");

			g.addEdge(b1, b2, WHEN_TRUE);
			g.addEdge(b1, n4, WHEN_FALSE);
			g.addEdge(n4, n5, ALWAYS);
			g.addEdge(b2, n6, WHEN_TRUE);
			g.addEdge(b2, n5, WHEN_FALSE);
			g.addEdge(n5, n7, ALWAYS);
			g.addEdge(n6, n7, ALWAYS);
			g.addEdge(n7, d1, ALWAYS);

			g.addEdge(A, b1, WHEN_FALSE);
		}

		return g;
	}
}
