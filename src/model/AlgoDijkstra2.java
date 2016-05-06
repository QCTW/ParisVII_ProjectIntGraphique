package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class AlgoDijkstra2
{
	private Noeud startNode;
	private Noeud endNode;
	private static Vector<Noeud> givenNodes;
	private final Vector<Snapshot> animationSteps = new Vector<Snapshot>();
	private static Vector<Edge> givenEdges;
	private static HashMap<Integer, Edge> hmRestEdge = new HashMap<Integer, Edge>();
	private static HashMap<Integer, Noeud> hmDiscoveredNode = new HashMap<Integer, Noeud>();

	public AlgoDijkstra2(Vector<Noeud> allNodes, Vector<Edge> allConnections)
	{
		givenNodes = allNodes;
		givenEdges = allConnections;
		for (Noeud n : allNodes)
		{
			if (n.isStartNode())
			{
				startNode = n;
				hmDiscoveredNode.put(n.getNodeId(), n);

			} else if (n.isEndNode())
			{
				endNode = n;
			}
		}

		for (Edge c : allConnections)
		{
			hmRestEdge.put(c.getEdgeId(), c);
			System.out.println("add une connections " + c.getEdgeId());
		}
	}

	public void start()
	{
		while (hmDiscoveredNode.get(endNode.getNodeId()) == null)
		{
			long min = Long.MAX_VALUE;
			Noeud minPath = null;
			Edge minConne = null;
			for (Map.Entry<Integer, Noeud> entry : hmDiscoveredNode.entrySet())
			{
				Noeud begin = entry.getValue();
				int idBegin = entry.getKey();
				System.out.println("Node Id " + idBegin);
				begin.setStatus(NoeudStatus.DISCOVERING);
				animationSteps.add(new Snapshot(givenNodes));
				for (Edge e : begin.getEdges())
				{
					if (hmRestEdge.get(e.getEdgeId()) != null)
					{
						Noeud target;
						if (idBegin == e.getStartPoint().getNodeId())
						{
							target = e.getEndPoint();
						} else
						{
							target = e.getStartPoint();
						}

						System.out.println("targetnode is (" + target.getNodeId() + ") and VertexValue old is " + target.getVertexValue());
						if ((e.getWeight() + begin.getVertexValue()) < target.getVertexValue())
						{
							System.out.println("Path: " + target.getNodeId() + " >>> " + begin.getNodeId() + " >>> " + startNode.getNodeId());
							target.setVertexValue(e.getWeight() + begin.getVertexValue());
							animationSteps.add(new Snapshot(givenNodes));
							System.out.println("Node Id is (" + target.getNodeId() + ") and VertexValue is " + target.getVertexValue());
						}

						if ((e.getWeight() + begin.getVertexValue()) < min)
						{
							min = (e.getWeight() + begin.getVertexValue());
							minPath = target;
							minConne = e;
						}
					}
				}
			}
			System.out.println("the shortest node is " + minPath.getNodeId() + " --cancel the edge of distance " + minConne.getWeight());
			hmDiscoveredNode.put(minPath.getNodeId(), minPath);
			minPath.setStatus(NoeudStatus.SHORTEST);
			animationSteps.add(new Snapshot(givenNodes));
			hmRestEdge.remove(minConne.getEdgeId());
			System.out.println("=================");
		}

		System.out.println("Algo Dijkstra");
		for (Map.Entry<Integer, Noeud> entry : hmDiscoveredNode.entrySet())
		{
			System.out.println("Node = " + entry.getKey() + ", Distance = " + entry.getValue().getVertexValue());
		}
	}

	public Vector<Snapshot> getAnimationSteps()
	{
		return animationSteps;
	}

	public static void main(String[] args)
	{
		System.out.println("Algo Dijkstra");
		Noeud b0 = new Noeud();
		b0.setStartNode(true);
		Noeud b1 = new Noeud();
		Noeud b2 = new Noeud();
		Noeud b3 = new Noeud();
		Noeud b4 = new Noeud();
		Noeud c5 = new Noeud();
		c5.setEndNode(true);

		Edge con2 = new Edge(b0, b1);
		con2.setWeight(7);
		Edge con3 = new Edge(b0, b2);
		con3.setWeight(9);
		Edge con1 = new Edge(b0, c5);
		con1.setWeight(14);
		Edge con4 = new Edge(b1, b2);
		con4.setWeight(10);
		Edge con5 = new Edge(b1, b3);
		con5.setWeight(1);
		Edge con6 = new Edge(b2, c5);
		con6.setWeight(2);
		Edge con7 = new Edge(b2, b3);
		con7.setWeight(11);
		Edge con8 = new Edge(b3, b4);
		con8.setWeight(6);
		Edge con9 = new Edge(c5, b4);
		con9.setWeight(9);

		Vector<Noeud> vNodes = new Vector<Noeud>();
		vNodes.add(b0);
		vNodes.add(b1);
		vNodes.add(b2);
		vNodes.add(b3);
		vNodes.add(b4);
		vNodes.add(c5);

		Vector<Edge> vEdges = new Vector<Edge>();
		vEdges.add(con1);
		vEdges.add(con2);
		vEdges.add(con3);
		vEdges.add(con4);
		vEdges.add(con5);
		vEdges.add(con6);
		vEdges.add(con7);
		vEdges.add(con8);
		vEdges.add(con9);

		AlgoDijkstra2 algo1 = new AlgoDijkstra2(vNodes, vEdges);
		algo1.start();
	}
}
