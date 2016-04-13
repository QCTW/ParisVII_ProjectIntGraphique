package view;

public final class Utility
{
	private static int nodeId = 0;

	private Utility()
	{
	}

	public static int generateId()
	{
		nodeId++;
		return nodeId;
	}

	// Call this method when deserialized the last object
	public static void setFinalId(int id)
	{
		nodeId = id;
	}

	public static double adjustPosFromCenter2TopLeft(double d)
	{
		return d - (Settings.NODE_SIZE / 2);
	}
}
