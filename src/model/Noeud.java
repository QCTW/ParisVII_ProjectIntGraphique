package model;

import java.util.Vector;

import view.Utility;

public class Noeud implements BaseNode
{
	private static final long serialVersionUID = 1L;
	private final Vector<Edge> vEdges;
	private final int nodeId;
	private String nodeLabel;
	private long vertexValue = BaseNode.INFINITY;
	private double posX = 0;
	private double posY = 0;
	private double posZ = 0;
	private boolean isStartNode = false;
	private boolean isEndNode = false;
	private boolean hasExamed = false;
	private NoeudStatus nodeStatus = NoeudStatus.NONE;

	public Noeud(Noeud copy)
	{
		nodeId = copy.nodeId;
		nodeLabel = copy.nodeLabel;
		vertexValue = copy.vertexValue;
		posX = copy.posX;
		posY = copy.posY;
		posZ = copy.posZ;
		isStartNode = copy.isStartNode;
		isEndNode = copy.isEndNode;
		nodeStatus = copy.nodeStatus;
		vEdges = copy.getEdges(); // Note that the edge in this Vector still points to the old, not-cloned node
	}

	public Noeud()
	{
		vEdges = new Vector<Edge>();
		nodeId = Utility.generateId();
		nodeLabel = Integer.toString(nodeId);
	}

	@Override
	public String toString()
	{
		return nodeLabel + "(" + vertexValue + ")" + "@" + this.hashCode();
	}

	@Override
	public int getNodeId()
	{
		return this.nodeId;
	}

	@Override
	public String getNodeLabel()
	{
		return nodeLabel;
	}

	@Override
	public void setNodeLabel(String sLabel)
	{
		nodeLabel = sLabel;
	}

	@Override
	public double getPosX()
	{
		return posX;
	}

	@Override
	public double getPosY()
	{
		return posY;
	}

	@Override
	public double getPosZ()
	{
		return posZ;
	}

	@Override
	public void setPosX(double x)
	{
		posX = x;
	}

	@Override
	public void setPosY(double y)
	{
		posY = y;
	}

	@Override
	public void setPosZ(double z)
	{
		posZ = z;
	}

	@Override
	public void addEdge(Edge conn)
	{
		if (!vEdges.contains(conn))
			vEdges.add(conn);
	}

	@Override
	public void removeEdge(Edge conn)
	{
		vEdges.remove(conn);
	}

	@Override
	public Vector<Edge> getEdges()
	{
		return vEdges;
	}

	@Override
	public boolean isStartNode()
	{
		return isStartNode;
	}

	@Override
	public boolean isEndNode()
	{
		return isEndNode;
	}

	@Override
	public void setStartNode(boolean isOrNot)
	{
		if (isOrNot)
		{
			this.setVertexValue(0);
		} else
		{
			this.setVertexValue(BaseNode.INFINITY);
		}

		isStartNode = isOrNot;
	}

	@Override
	public void setEndNode(boolean isOrNot)
	{
		isEndNode = isOrNot;
	}

	@Override
	public boolean hasEdgeToDisconnect()
	{
		if (vEdges.size() > 0)
			return true;

		return false;
	}

	@Override
	public long getVertexValue()
	{
		return vertexValue;
	}

	@Override
	public void setVertexValue(long newValue)
	{
		vertexValue = newValue;
	}

	public NoeudStatus getStatus()
	{
		return nodeStatus;
	}

	public void setStatus(NoeudStatus status)
	{
		nodeStatus = status;
	}

	@Override
	public boolean isDiscovered()
	{
		return hasExamed;
	}

	@Override
	public void setIsDiscovered(boolean trueOrFalse)
	{
		hasExamed = trueOrFalse;
	}

	@Override
	public Object clone()
	{
		try
		{
			return super.clone();
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int hashCode()
	{
		return nodeId;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;

		Noeud node = (Noeud) obj;

		return (nodeId == node.hashCode());
	}

}
