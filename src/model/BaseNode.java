package model;

import java.io.Serializable;
import java.util.Vector;

public interface BaseNode extends Serializable
{
	public static long INFINITY = Long.MAX_VALUE;

	public int getNodeId();

	public long getVertexValue();

	public void setVertexValue(long newValue);

	public String getNodeLabel();

	public void setNodeLabel(String sLabel);

	public double getPosX();

	public double getPosY();

	public double getPosZ();

	public boolean isStartNode();

	public boolean isEndNode();

	public void setStartNode(boolean isOrNot); // Both view and model

	public void setEndNode(boolean isOrNot); // Both view and model

	public Vector<Edge> getEdges();

	public void addEdge(Edge conn);

	public void removeEdge(Edge conn);

	public boolean hasEdgeToDisconnect();

	public void setPosX(double x);

	public void setPosY(double y);

	public void setPosZ(double z);

}
