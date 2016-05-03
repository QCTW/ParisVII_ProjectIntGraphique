package model;

import view.Utility;

public class Edge implements BaseEdge
{
	private static final long serialVersionUID = 1L;
	private final Noeud nodeFrom, nodeTo;
	private final int edgeId;
	private int weight;

	public Edge(Noeud from, Noeud to)
	{
		nodeFrom = from;
		nodeTo = to;
		from.addEdge(this);
		to.addEdge(this);
		edgeId = Utility.generateId();
	}
	public int getEdgeId()
	{
		return this.edgeId;
	}

	@Override
	public Noeud getStartPoint()
	{
		return nodeFrom;
	}

	@Override
	public Noeud getEndPoint()
	{
		return nodeTo;
	}

	@Override
	public void delete()
	{
		nodeFrom.removeEdge(this);
		nodeTo.removeEdge(this);
	}

	@Override
	public int getWeight()
	{
		return weight;
	}

	@Override
	public void setWeight(int newWeight)
	{
		weight = newWeight;
	}

	@Override
	public String toString()
	{
		return nodeFrom.getNodeLabel() + " <-[" + weight + "]-> " + nodeTo.getNodeLabel();
	}

	@Override
	public int hashCode()
	{
		// final int prime = 31;
		// int result = 1;
		// result = prime * result + ((nodeFrom == null) ? 0 : nodeFrom.getNodeId());
		// result = prime * result + ((nodeTo == null) ? 0 : nodeTo.getNodeId());
		int result = 1;
		String strFrom = String.valueOf((nodeFrom == null) ? 0 : nodeFrom.getNodeId());
		String strTo = String.valueOf((nodeTo == null) ? 0 : nodeTo.getNodeId());
		for (char c : strFrom.toCharArray())
		{
			result += c;
		}
		for (char c : strTo.toCharArray())
		{
			result += c;
		}
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Edge other = (Edge) obj;
		int nSameNodesCount = 0;
		if (nodeFrom.getNodeId() == other.getStartPoint().getNodeId())
			nSameNodesCount++;
		else if (nodeFrom.getNodeId() == other.getEndPoint().getNodeId())
			nSameNodesCount++;

		if (nodeTo.getNodeId() == other.getEndPoint().getNodeId())
			nSameNodesCount++;
		else if (nodeTo.getNodeId() == other.getStartPoint().getNodeId())
			nSameNodesCount++;

		return (nSameNodesCount == 2) ? true : false;
	}

}
