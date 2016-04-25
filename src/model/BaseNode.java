package model;

import java.io.Serializable;
import java.util.Vector;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import view.ActionType;
import view.Connection;

public interface BaseNode extends Serializable
{
	public int getNodeId();

	public String getNodeLabel();

	public void setNodeLabel(String sLabel);

	public double getPosX();

	public double getPosY();

	public double getPosZ();

	public Point3D getPoint3D();

	public void moveTo(double x, double y, double z);

	public void displaySelected();

	public void removeSelected();

	public Node getFXNode();

	public void delete();

	public void setDisabled();

	public void setEnabled();

	public boolean isStartNode();

	public boolean isEndNode();

	public void setStartNode(boolean isOrNot);

	public void setEndNode(boolean isOrNot);

	/**
	 * This method is used to start/end a selection process and is different from setSelectMode
	 * 
	 * @param onOrOff
	 */
	public void selectMode(boolean onOrOff);

	/**
	 * This method is used to set this node to select mode so that it will react to the mouse event
	 * 
	 * @param trueOrFalse
	 */
	public void setSelectMode(boolean trueOrFalse);

	public boolean isSelectedStartingNode();

	public void resetSelectedStartingNode();

	public boolean isSelectMode();

	public Vector<Connection> getEdges();

	public void addEdge(Connection conn);

	public void removeEdge(Connection conn);

	public void updateEdgesDisplay();

	public void setAction(ActionType atype);

	public boolean hasNodeToConnect();

	public boolean hasEdgeToDisconnect();

}
