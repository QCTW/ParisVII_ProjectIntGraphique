package model;

import java.io.Serializable;

public interface BaseEdge extends Serializable
{
	public BaseNode getStartPoint();

	public BaseNode getEndPoint();

	public void delete();

	public void setDisabled();

	public void setEnabled();
}
