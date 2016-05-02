package model;

import view.ViewableEdge;

public class Edge implements BaseEdge
{
	private static final long serialVersionUID = 1L;
	private final Noeud nodeFrom, nodeTo;
	private int weight;

	public Edge(Noeud from, Noeud to)
	{
		nodeFrom = from;
		nodeTo = to;
		from.addEdge(this);
		to.addEdge(this);
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
		return "Connection " + nodeFrom.getNodeLabel() + " <-> " + nodeTo.getNodeLabel() + " (length:" + weight + ")";
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
		ViewableEdge other = (ViewableEdge) obj;
		if (nodeFrom == null)
		{
			if (other.getStartPoint() != null)
				return false;
		} else if (nodeFrom.getNodeId() != other.getStartPoint().getNodeId() && nodeFrom.getNodeId() != other.getEndPoint().getNodeId())
			return false;
		if (nodeTo == null)
		{
			if (other.getEndPoint() != null)
				return false;
		} else if (nodeTo.getNodeId() != other.getEndPoint().getNodeId() && nodeTo.getNodeId() != other.getStartPoint().getNodeId())
			return false;
		return true;
	}

}
