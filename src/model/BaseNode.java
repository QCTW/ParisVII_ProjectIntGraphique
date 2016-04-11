package model;

import java.io.Serializable;

public interface BaseNode extends Serializable
{
	static final long serialVersionUID = 1L;

	public int getNodeId();

	public String getNodeLabel();

	public void setNodeLabel(String sLabel);

	public double getPosX();

	public double getPosY();

	public double getPosZ();

	public void moveTo(int x, int y, int z);

}
