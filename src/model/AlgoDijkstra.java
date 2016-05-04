package model;

import java.util.HashMap;
import java.util.Map.Entry;

import java.util.Vector;

import view.Utility;

public class AlgoDijkstra
{
	private final Noeud startNode;
	private final Noeud endNode;
	private final Vector<Noeud> givenNodes;
	private final Vector<Noeud> shortestPathNodes = new Vector<Noeud>();
	private final Vector<Step> animationSteps = new Vector<Step>();
	private final HashMap<Integer, Noeud> hmDiscoveredNodes = new HashMap<Integer, Noeud>();

	public AlgoDijkstra(Vector<Noeud> allNodes)
	{
		givenNodes = allNodes;
		startNode = findStartNode(givenNodes);
		endNode = findEndNode(givenNodes);
	}

	public void start()
	{
		System.out.println("Algo Dijkstra started");
		animationSteps.add(new Step(givenNodes));
		discoverShortestPath(startNode);
		findShortestPath(startNode, endNode);
		for (Noeud one : shortestPathNodes)
			one.setStatus(NoeudStatus.SHORTEST);

		startNode.setStatus(NoeudStatus.NONE);
		endNode.setStatus(NoeudStatus.NONE);
		animationSteps.add(new Step(givenNodes));

		System.out.println("Algo Dijkstra finished with " + animationSteps.size() + " steps");
	}

	private void findShortestPath(Noeud start, Noeud end)
	{
		Noeud minValueNode = null;
		long valueEnd = end.getVertexValue();
		for (Edge conn : end.getEdges())
		{
			Noeud target = Utility.findTargetNodeFromSource(conn, end);
			if (target.getVertexValue() == valueEnd - conn.getWeight())
			{
				minValueNode = target;
				break;
			}
		}

		if (minValueNode.getNodeId() != start.getNodeId())
		{
			findShortestPath(start, minValueNode);
			shortestPathNodes.add(minValueNode);
		}
	}

	public Vector<Noeud> getShortestPathNodes()
	{
		return shortestPathNodes;
	}

	private void discoverShortestPath(Noeud oneNode)
	{
		if (hmDiscoveredNodes.get(oneNode.getNodeId()) == null)
		{
			System.out.println("Discovering node: " + oneNode.getNodeId());
			hmDiscoveredNodes.put(oneNode.getNodeId(), oneNode);
			oneNode.setStatus(NoeudStatus.DISCOVERING);
			animationSteps.add(new Step(givenNodes));
			Vector<Noeud> vUndiscoveredNodes = new Vector<Noeud>();
			for (Edge conn : oneNode.getEdges())
			{
				Noeud target = Utility.findTargetNodeFromSource(conn, oneNode);
				int weight = conn.getWeight();

				System.out.println(oneNode.getNodeId() + " <-[" + weight + "]-> " + target.getNodeId());
				long lSourceValuePlusWeight = escapeInfinity(oneNode) + weight;
				oneNode.setStatus(NoeudStatus.COMPARE_SRC);
				target.setStatus(NoeudStatus.COMPARE_DEST);
				animationSteps.add(new Step(givenNodes));
				if (lSourceValuePlusWeight < target.getVertexValue())
				{
					System.out.println(target.getNodeId() + "'s value changes from " + target.getVertexValue() + " to " + lSourceValuePlusWeight);
					target.setVertexValue(lSourceValuePlusWeight);
					target.setStatus(NoeudStatus.CHANGEDVALUE);
					animationSteps.add(new Step(givenNodes));

				}

				if (hmDiscoveredNodes.get(target.getNodeId()) == null)
					vUndiscoveredNodes.add(target);

			}

			oneNode.setStatus(NoeudStatus.DISCOVERED);
			animationSteps.add(new Step(givenNodes));
			printHashMap(hmDiscoveredNodes);
			resetStatusExceptDiscovered();
			animationSteps.add(new Step(givenNodes));

			for (Noeud node : vUndiscoveredNodes)
			{
				discoverShortestPath(node);
			}
		}
	}

	private void resetStatusExceptDiscovered()
	{
		for (Noeud one : givenNodes)
		{
			if (hmDiscoveredNodes.get(one.getNodeId()) != null)
				one.setStatus(NoeudStatus.DISCOVERED);
			else
				one.setStatus(NoeudStatus.NONE);
		}
	}

	public Vector<Step> getAnimationSteps()
	{
		return animationSteps;
	}

	private long escapeInfinity(Noeud oneNode)
	{
		if (oneNode.getVertexValue() == BaseNode.INFINITY)
			return 0;
		return oneNode.getVertexValue();
	}

	private void printHashMap(HashMap<Integer, Noeud> hmNodes)
	{
		StringBuffer sb = new StringBuffer("====\n");
		for (Entry<Integer, Noeud> e : hmNodes.entrySet())
		{
			sb.append(e.getKey() + "(" + e.getValue().getVertexValue() + ")\n");
		}
		sb.append("====");
		System.out.println(sb.toString());
	}

	private Noeud findStartNode(Vector<Noeud> nodes)
	{
		for (Noeud n : nodes)
		{
			if (n.isStartNode())
			{
				return n;
			}
		}
		return null;
	}

	private Noeud findEndNode(Vector<Noeud> nodes)
	{
		for (Noeud n : nodes)
		{
			if (n.isEndNode())
			{
				return n;
			}
		}
		return null;
	}

	public Noeud getStartNode()
	{
		return startNode;
	}

	public Noeud getEndNode()
	{
		return endNode;
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
		con4.setWeight(1);
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

		AlgoDijkstra algo1 = new AlgoDijkstra(vNodes);
		algo1.start();
	}
}
