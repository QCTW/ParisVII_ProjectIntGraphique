package model;

import java.util.Vector;

import view.Utility;

public class AlgoDijkstra
{
	private Noeud startNode = null;
	private Noeud endNode = null;
	private final Vector<Noeud> givenNodes;
	private final Vector<Noeud> nodesExamed = new Vector<Noeud>();
	private final Vector<Noeud> shortestPathNodes = new Vector<Noeud>();
	private final Vector<Snapshot> animationSteps = new Vector<Snapshot>();
	// private final HashMap<Integer, Noeud> hmDiscoveredNodes = new HashMap<Integer, Noeud>();

	public AlgoDijkstra(Vector<Noeud> allNodes)
	{
		givenNodes = new Snapshot(allNodes).getSnapShot();
		startNode = findStartNode(givenNodes);
		endNode = findEndNode(givenNodes);
	}

	public void start()
	{
		System.out.println("Algo Dijkstra started");
		animationSteps.add(new Snapshot(givenNodes));
		nodesExamed.add(startNode);
		discoverShortestPath(startNode);

		System.out.println("startNode: " + startNode.getNodeId() + "(" + startNode.getVertexValue() + ")");
		System.out.println("endNode: " + endNode.getNodeId() + "(" + endNode.getVertexValue() + ")");
		findShortestPath(startNode, endNode);

		for (Noeud one : shortestPathNodes)
			one.setStatus(NoeudStatus.SHORTEST);

		startNode.setStatus(NoeudStatus.NONE);
		endNode.setStatus(NoeudStatus.NONE);
		animationSteps.add(new Snapshot(givenNodes));

		System.out.println("Algo Dijkstra finished with " + animationSteps.size() + " steps");
	}

	private void findShortestPath(Noeud start, Noeud end)
	{
		Noeud minValueNode = null;
		long valueEnd = end.getVertexValue();
		for (Edge conn : end.getEdges())
		{
			Noeud target = Utility.findTargetNodeFromSource(conn, end);
			target = getCorrectClonedObject(target);
			long valueTarget = target.getVertexValue();
			if (valueTarget == valueEnd - conn.getWeight())
			{
				minValueNode = target;
				break;
			}
		}

		if (minValueNode != null && minValueNode.getNodeId() != start.getNodeId())
		{
			findShortestPath(start, minValueNode);
			shortestPathNodes.add(minValueNode);
			System.out.println("Shortest node " + minValueNode.getNodeId() + " added");
		}
	}

	public Vector<Noeud> getShortestPathNodes()
	{
		return shortestPathNodes;
	}

	private void discoverShortestPath(Noeud oneNode)
	{
		System.out.println("Discovering node: " + oneNode.getNodeId() + "(" + oneNode.getVertexValue() + ")");
		oneNode.setStatus(NoeudStatus.DISCOVERING);
		animationSteps.add(new Snapshot(givenNodes));

		for (Edge conn : oneNode.getEdges())
		{
			Noeud target = Utility.findTargetNodeFromSource(conn, oneNode);
			target = getCorrectClonedObject(target);
			int weight = conn.getWeight();
			System.out.println(oneNode.getNodeId() + " checks its edge:" + oneNode.getNodeId() + " <-[" + weight + "]-> " + target.getNodeId());
			long lSourceValuePlusWeight = escapeInfinity(oneNode) + weight;
			oneNode.setStatus(NoeudStatus.COMPARE_SRC);
			if (!target.isDiscovered())
			{
				target.setStatus(NoeudStatus.COMPARE_DEST);
				animationSteps.add(new Snapshot(givenNodes));
				if (lSourceValuePlusWeight < target.getVertexValue())
				{
					System.out.println(target.getNodeId() + "'s value changes from " + target.getVertexValue() + " to " + lSourceValuePlusWeight);
					target.setVertexValue(lSourceValuePlusWeight);
					target.setStatus(NoeudStatus.CHANGEDVALUE);
					animationSteps.add(new Snapshot(givenNodes));
					if (!nodesExamed.contains(target))
						nodesExamed.add(target);
				}
			}
		}

		oneNode.setIsDiscovered(true);
		oneNode.setStatus(NoeudStatus.DISCOVERED);
		animationSteps.add(new Snapshot(givenNodes));
		printVector(nodesExamed);
		Noeud smallest = findSmallestValueNode(nodesExamed);
		if (smallest != null)
			discoverShortestPath(smallest);
		else
			System.out.println("All nodes has been discovered");
	}

	private Noeud getCorrectClonedObject(Noeud original)
	{
		int index = givenNodes.indexOf(original);
		return givenNodes.get(index);
	}

	private Noeud findSmallestValueNode(Vector<Noeud> nodes)
	{
		long smallestValue = BaseNode.INFINITY;
		Noeud smallestNode = null;
		Vector<Noeud> vUpatedNodes = new Vector<Noeud>();
		for (Noeud one : nodes)
		{
			if (!one.isDiscovered()) // Only check not-discovered node
			{
				one.setStatus(NoeudStatus.FINDSMALLEST);
				vUpatedNodes.add(one);
				if (one.getVertexValue() < smallestValue)
				{
					smallestValue = one.getVertexValue();
					smallestNode = one;
				}
			}
		}
		if (smallestNode != null)
		{
			animationSteps.add(new Snapshot(givenNodes));
			smallestNode.setStatus(NoeudStatus.SMALLEST);
			animationSteps.add(new Snapshot(givenNodes));
		}

		for (Noeud one : vUpatedNodes)
		{
			if (one.getNodeId() != smallestNode.getNodeId())
				one.setStatus(NoeudStatus.NONE);
		}
		animationSteps.add(new Snapshot(givenNodes));
		return smallestNode;
	}

	public Vector<Snapshot> getAnimationSteps()
	{
		return animationSteps;
	}

	private long escapeInfinity(Noeud oneNode)
	{
		if (oneNode.getVertexValue() == BaseNode.INFINITY)
			return 0;
		return oneNode.getVertexValue();
	}

	private void printVector(Vector<Noeud> vNodes)
	{
		StringBuffer sb = new StringBuffer("====\n");
		for (Noeud e : vNodes)
		{
			sb.append(e.getNodeId() + "(" + e.getVertexValue() + ")\n");
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
