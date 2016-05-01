package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import view.Connection;

public class AlgoDijkstra
{
	BaseNode startNode;
	BaseNode endNode;
	private Vector<BaseNode> algoNodes;
	private Vector<Connection> algoConne;
	private HashMap<BaseNode,Long> hmPathNode = new HashMap<BaseNode,Long>();
	private ArrayList<BaseNode> listNode = new ArrayList();
	private ArrayList<Connection> listConne = new ArrayList();
	
	
	public AlgoDijkstra(Vector<BaseNode> allNodes, Vector<Connection> allConnections)
	{
		algoNodes = allNodes;
		algoConne = allConnections;
		for(BaseNode n: algoNodes)
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
		
		for(Connection c: algoConne)
		{
			listConne.add(c);
			System.out.println("add une connections");
		}
		
		
		while(!listConne.isEmpty())
		{	
			long min = Long.MAX_VALUE;
			BaseNode minPath = startNode;
			Connection minConne = listConne.get(0);
			for(BaseNode b: listNode)
			{	
				int idBegin = b.getNodeId();
				System.out.println("Node Id "+idBegin);
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
			hmPathNode.put(minPath, min);
		}
		
		
		System.out.println("Algo Dijkstra");
		for(Map.Entry<BaseNode, Long> entry : hmPathNode.entrySet()) {  
			  
		    System.out.println("Key = " + entry.getKey().getNodeLabel() + ", Value = " + entry.getValue());  
		  
		}  
	}

//	public static void main(String[] args)
//	{
//		System.out.println("Algo Dijkstra");
//	}

}
