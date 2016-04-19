package model;

import java.io.Serializable;

import javafx.scene.Node;

public interface BaseNode extends Serializable
{
	public int getNodeId();

	public String getNodeLabel();

	public void setNodeLabel(String sLabel);

	public double getPosX();

	public double getPosY();

	public double getPosZ();

	public void moveTo(double x, double y, double z);

	public void displaySelected();

	public Node getFXNode();

}
