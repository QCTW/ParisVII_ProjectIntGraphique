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

	/**
	 * Used for 2D only
	 */
	public static double calculateDestination(double startX, double startY, double endX, double endY)
	{
		return Math.sqrt(Math.pow((endX - startX), 2) + Math.pow((endY - startY), 2));
	}
}
