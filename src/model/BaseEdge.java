package model;

import java.io.Serializable;

public interface BaseEdge extends Serializable
{
	public BaseNode getStartPoint();

	public BaseNode getEndPoint();

	public void delete();

	public int getWeight();

	public void setWeight(int newWeight);
}
