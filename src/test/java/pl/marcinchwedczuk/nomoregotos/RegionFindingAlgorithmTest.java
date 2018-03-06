package pl.marcinchwedczuk.nomoregotos;

import org.junit.Test;
import pl.marcinchwedczuk.nomoregotos.condexpr.Condition;
import pl.marcinchwedczuk.nomoregotos.graph.slicegraph.SliceEdge;
import pl.marcinchwedczuk.nomoregotos.graph.slicegraph.SliceGraph;
import pl.marcinchwedczuk.nomoregotos.graph.slicegraph.SliceNode;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static pl.marcinchwedczuk.nomoregotos.CfgEdgeCondition.ALWAYS;
import static pl.marcinchwedczuk.nomoregotos.CfgEdgeCondition.WHEN_FALSE;
import static pl.marcinchwedczuk.nomoregotos.CfgEdgeCondition.WHEN_TRUE;

public class RegionFindingAlgorithmTest {
	@Test
	public void canFindRegions() throws Exception {
		ControlFlowGraph g = buildGraph();

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

		/*for (CfgNode node : tmp.getTopologicalOrder()) {
			System.out.println(node);
		}*/

		BuildSliceGraphAlgorithm alg =
				new BuildSliceGraphAlgorithm(g, g.findNode("A"), g.findNode("n9"));
		SliceGraph sliceGraph = alg.createSliceGraph();

		List<SliceNode> top =
				sliceGraph.peformTopologicalSort(sliceGraph.findNode(g.findNode("A")));

		Collections.reverse(top);

		top.forEach(node -> {
			Condition c = null;

			for (SliceEdge edge : node.getIncoming()) {
				Condition predCond = edge.getFrom().cfgNode.condition;
				if (predCond == null) {
					predCond = Condition.alwaysTrue();
				}

				CfgEdge cfgEdge = edge.getFrom().cfgNode.findEdgeTo(edge.getTo().cfgNode);

				if (cfgEdge.condition == WHEN_TRUE) {
					Condition var = Condition.variable(edge.getFrom().cfgNode.toString());
					predCond = predCond.and(var);
				} else if (cfgEdge.condition == WHEN_FALSE) {
					Condition var = Condition.negatedVariable(edge.getFrom().cfgNode.toString());
					predCond = predCond.and(var);
				}

				if (c == null) { c = predCond; }
				else {
					c = c.or(predCond);
				}
			}

			if (c != null) {
				node.cfgNode.condition = c;
				node.cfgNode.extraLabel = c.toString();
			}
		});

		new GraphVizHelper(g, "/home/mc/tmp/graph.graphviz").saveToDotFile();
	}

	private ControlFlowGraph buildR2Graph() {
		ControlFlowGraph g = new ControlFlowGraph();

		CfgNode A = g.addConditionNode("A");
		g.addEdge(g.start, A, ALWAYS);

		CfgNode n9 = g.addCodeNode("n9");
		g.addEdge(n9, g.stop, ALWAYS);

		// Region R2
		{
			CfgNode b1 = g.addConditionNode("b1");
			CfgNode b2 = g.addConditionNode("b2");
			CfgNode n4 = g.addCodeNode("n4");
			CfgNode n5 = g.addCodeNode("n5");
			CfgNode n6 = g.addCodeNode("n6");
			CfgNode n7 = g.addCodeNode("n7");

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

		CfgNode A = g.addConditionNode("A");
		g.addEdge(g.start, A, ALWAYS);

		CfgNode n9 = g.addCodeNode("n9");
		g.addEdge(n9, g.stop, ALWAYS);

		{
			// Region R1
			CfgNode c1 = g.addConditionNode("c1");
			CfgNode c2 = g.addConditionNode("c2");
			CfgNode c3 = g.addConditionNode("c3");

			CfgNode n1 = g.addCodeNode("n1");
			CfgNode n2 = g.addCodeNode("n2");
			CfgNode n3 = g.addCodeNode("n3");

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
		CfgNode d1 = g.addConditionNode("d1");
		{
			CfgNode d2 = g.addConditionNode("d2");
			CfgNode d3 = g.addConditionNode("d3");
			CfgNode n8 = g.addCodeNode("n8");

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
			CfgNode b1 = g.addConditionNode("b1");
			CfgNode b2 = g.addConditionNode("b2");
			CfgNode n4 = g.addCodeNode("n4");
			CfgNode n5 = g.addCodeNode("n5");
			CfgNode n6 = g.addCodeNode("n6");
			CfgNode n7 = g.addCodeNode("n7");

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
