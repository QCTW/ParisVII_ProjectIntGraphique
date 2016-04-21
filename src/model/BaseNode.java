package model;

import java.io.Serializable;
import java.util.Vector;

import javafx.scene.Node;
import view.Edge;

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

	public void removeSelected();

	public Node getFXNode();

	public void selectMode(boolean onOrOff);

	public void setSelectMode(boolean trueOrFalse);

	public boolean isCurrentlySelected();

	public void setSelected(boolean isOrNot);

	public boolean isSelectMode();

	public Vector<Edge> getEdgeList();

}
