package model;

import java.util.HashMap;
import java.util.Map.Entry;

import java.util.Vector;

public class AlgoDijkstra
{
	private final Noeud startNode;
	private final Noeud endNode;
	private final Vector<Noeud> givenNodes;
	private final HashMap<Integer, Noeud> hmDiscoveredNodes = new HashMap<Integer, Noeud>();

	public AlgoDijkstra(Vector<Noeud> allNodes)
	{
		givenNodes = allNodes;
		startNode = findStartNode(givenNodes);
		endNode = findEndNode(givenNodes);
	}

	public void start()
	{
		discoverShortestPath(startNode);
	}

	private void discoverShortestPath(Noeud oneNode)
	{
		if (hmDiscoveredNodes.get(oneNode.getNodeId()) == null)
		{
			System.out.println("Discovering node: " + oneNode.getNodeId());
			hmDiscoveredNodes.put(oneNode.getNodeId(), oneNode);
			Vector<Noeud> vUndiscoveredNodes = new Vector<Noeud>();
			for (Edge conn : oneNode.getEdges())
			{
				Noeud target = null;
				Noeud from = conn.getStartPoint();
				Noeud to = conn.getEndPoint();
				int weight = conn.getWeight();
				if (from.getNodeId() != oneNode.getNodeId())
				{
					target = from;
				} else if (to.getNodeId() != oneNode.getNodeId())
				{
					target = to;
				}

				long lSourceValuePlusWeight = oneNode.getVertexValue() + weight;
				if (target.getVertexValue() == 0 || lSourceValuePlusWeight < target.getVertexValue())
					target.setVertexValue(lSourceValuePlusWeight);

				if (hmDiscoveredNodes.get(target.getNodeId()) == null)
					vUndiscoveredNodes.add(target);
			}

			printHashMap(hmDiscoveredNodes);

			for (Noeud node : vUndiscoveredNodes)
			{
				discoverShortestPath(node);
			}
		}
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

	private Noeud findEndNode(Vector<Noeud> nodes)
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

	private Noeud findStartNode(Vector<Noeud> nodes)
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

		Edge con1 = new Edge(b0, c5);
		con1.setWeight(14);
		Edge con2 = new Edge(b0, b1);
		con2.setWeight(7);
		Edge con3 = new Edge(b0, b2);
		con3.setWeight(9);
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

		AlgoDijkstra algo1 = new AlgoDijkstra(vNodes);
		algo1.start();
	}

	// public AlgoDijkstra(Vector<BaseNode> allNodes, Vector<Connection> allConnections)
	// {
	// algoNodes = allNodes;
	// algoConne = allConnections;
	// for(BaseNode n: algoNodes)
	// {
	// if(n.isStartNode())
	// {
	// startNode = n;
	// listNode.add(n);
	// }
	// else if(n.isEndNode())
	// {
	// endNode = n;
	// }
	// }
	//
	// for(Connection c: algoConne)
	// {
	// listConne.add(c);
	// System.out.println("add une connections");
	// }
	//
	//
	// while(!listConne.isEmpty())
	// {
	// long min = Long.MAX_VALUE;
	// BaseNode minPath = startNode;
	// Connection minConne = listConne.get(0);
	// for(BaseNode b: listNode)
	// {
	// int idBegin = b.getNodeId();
	// System.out.println("Node Id "+idBegin);
	// for(Connection c: b.getEdges())
	// {
	// if (listConne.contains(c))
	// {
	// BaseNode other;
	// if(idBegin !=c.getStartPoint().getNodeId()){
	// other = c.getEndPoint();
	// }
	// else
	// {
	// other = c.getStartPoint();
	// }
	//
	// if((c.getWeight()+b.getVertexValue()) < other.getVertexValue())
	// {
	// other.setVertexValue(c.getWeight()+b.getVertexValue());
	// }
	//
	// if(other.getVertexValue()< min)
	// {
	// min = other.getVertexValue();
	// minPath = other;
	// minConne = c;
	// }
	// }
	// }
	// }
	// listNode.add(minPath);
	// for(int i = 0; i<listConne.size(); i++)
	// {
	// if(listConne.get(i)== minConne)
	// {
	// listConne.remove(i);
	// }
	// }
	// hmPathNode.put(minPath, min);
	// }
	//
	//
	// System.out.println("Algo Dijkstra");
	// for(Map.Entry<BaseNode, Long> entry : hmPathNode.entrySet()) {
	//
	// System.out.println("Key = " + entry.getKey().getNodeLabel() + ", Value = " + entry.getValue());
	//
	// }
	// }
}
