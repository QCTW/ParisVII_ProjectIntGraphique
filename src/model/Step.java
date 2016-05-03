package model;

import java.util.HashMap;

public class Step
{
	private static int idCount = 0;

	private final int stepId;
	private final HashMap<NoeudStatus, Noeud> noeudsToStatus = new HashMap<NoeudStatus, Noeud>();

	public Step()
	{
		idCount++;
		stepId = idCount;
	}

	public Step(Noeud nodeToCopy, NoeudStatus status)
	{
		this();
		register(nodeToCopy, status);
	}

	public void register(Noeud nodeToCopy, NoeudStatus status)
	{
		try
		{
			Noeud copy = (Noeud) nodeToCopy.clone();
			noeudsToStatus.put(status, copy);
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
	}

	public int getStepId()
	{
		return stepId;
	}

}
