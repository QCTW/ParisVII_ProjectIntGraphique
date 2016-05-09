package model;

import java.io.Serializable;
import java.util.Vector;

import view.Utility;
import view.ViewableEdge;
import view.ViewableNode;

public class Graph implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final Vector<Noeud> vAllNodes = new Vector<Noeud>();
	private final Vector<Edge> vConnections = new Vector<Edge>();

	public Graph()
	{
	}

	public Vector<Noeud> getAllNodes()
	{
		return vAllNodes;
	}

	public Vector<Edge> getAllEdges()
	{
		return vConnections;
	}

	public Vector<ViewableNode> getAllViewableNodes()
	{
		return Utility.convertNodeModelToView(vAllNodes);
	}

	public Vector<ViewableEdge> getAllViewableEdges()
	{
		return Utility.convertEdgeModelToView(vConnections);
	}

}
