package model;

import java.util.Vector;

public class Snapshot
{
	private static int idCount = 0;

	private final int stepId;
	private Vector<Noeud> clonedNodes = null;

	public Snapshot(Vector<Noeud> allNodes)
	{
		clonedNodes = zap(allNodes);
		idCount++;
		stepId = idCount;
	}

	public Vector<Noeud> getSnapShot()
	{
		return clonedNodes;
	}

	public int getStepId()
	{
		return stepId;
	}

	public Vector<Noeud> zap(Vector<Noeud> allNodes)
	{
		Vector<Noeud> newVector = new Vector<Noeud>();
		for (Noeud one : allNodes)
		{
			Noeud clone = new Noeud(one);
			if (clone != null)
				newVector.add(clone);
		}
		return newVector;
	}

}
