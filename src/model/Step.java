package model;

import java.util.Vector;

public class Step
{
	private static int idCount = 0;

	private final int stepId;
	private Vector<Noeud> clonedNodes = null;

	public Step(Vector<Noeud> allNodes)
	{
		clonedNodes = snapShot(allNodes);
		idCount++;
		stepId = idCount;
	}

	public Step(Vector<Noeud> allNodes, Noeud nodeToCopy, NoeudStatus status)
	{
		this(allNodes);
		register(nodeToCopy, status);
	}

	public void register(Noeud deltaNode, NoeudStatus status)
	{
		int index = clonedNodes.indexOf(deltaNode);
		Noeud cloned = clonedNodes.get(index);
		cloned.setStatus(status);
	}

	public Vector<Noeud> getSnapShot()
	{
		return clonedNodes;
	}

	public int getStepId()
	{
		return stepId;
	}

	private Vector<Noeud> snapShot(Vector<Noeud> allNodes)
	{
		Vector<Noeud> newVector = new Vector<Noeud>();
		for (Noeud one : allNodes)
		{
			Noeud clone = (Noeud) one.clone();
			if (clone != null)
				newVector.add(clone);
		}
		return newVector;
	}

}
