package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import view.Connection;

public class AlgoDijkstra
{
	BaseNode startNode;
	BaseNode endNode;
	private Vector<BaseNode> algoNodes;
	private Vector<Connection> algoConne;
	private HashMap<BaseNode,Integer> hmPathNode = new HashMap<BaseNode,Integer>();
	private ArrayList<BaseNode> listNode = new ArrayList();
	private ArrayList<Connection> listConne = new ArrayList();
	
	
	public AlgoDijkstra(Vector<BaseNode> allNodes, Vector<Connection> allConnections)
	{
		algoNodes = allNodes;
		algoConne = allConnections;
		for(BaseNode n: allNodes)
		{
			if(n.isStartNode())
			{
				startNode = n;
				listNode.add(n);
			}
			else if(n.isEndNode())
			{
				endNode = n;
			}
		}
		
		for(Connection c: allConnections)
		{
			listConne.add(c);
		}
		
		
		while(!listConne.isEmpty())
		{	
			long min = Long.MAX_VALUE;
			BaseNode minPath = startNode;
			Connection minConne = listConne.get(0);
			for(BaseNode b: listNode)
			{	
				int idBegin = b.getNodeId();
				for(Connection c: b.getEdges())
				{
					if (listConne.contains(c))
					{	
						BaseNode other;
						if(idBegin !=c.getStartPoint().getNodeId()){
							other = c.getEndPoint();
						}
						else
						{
							other = c.getStartPoint();
						}
						
						if((c.getWeight()+b.getVertexValue()) < other.getVertexValue())
						{
							 other.setVertexValue(c.getWeight()+b.getVertexValue());
						}
						
						if(other.getVertexValue()< min)
						{
							min = other.getVertexValue();
							minPath = other;
							minConne = c;
						}
					}	
				}
			}
			listNode.add(minPath);
			for(int i = 0; i<listConne.size(); i++)
			{
				if(listConne.get(i)== minConne)
				{
					listConne.remove(i);
				}
			}
			hmPathNode.put(minPath, (int) min);
		}
		
	}

	public static void main(String[] args)
	{
		System.out.println("Algo Dijkstra");
	}

}
