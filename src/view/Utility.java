package view;

import java.util.Vector;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Edge;
import model.Noeud;
import view.Settings;
import view.ViewableNode;

public final class Utility
{
	private static int nodeId = 0;
	private static final Effect frostEffect = new BoxBlur(60, 60, 3);

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

	public static StackPane createFreezedNode(Node bgNode)
	{
		DoubleProperty y = new SimpleDoubleProperty(Settings.ICON_WIDTH_SIZE);
		Image frostImage = bgNode.snapshot(new SnapshotParameters(), null);
		ImageView frost = new ImageView(frostImage);

		Rectangle filler = new Rectangle(0, 0, Settings.NODE_SIZE, Settings.NODE_SIZE);
		filler.setFill(Color.AZURE);

		Pane frostPane = new Pane(frost);
		frostPane.setEffect(frostEffect);

		StackPane frostView = new StackPane(filler, frostPane);

		Rectangle clipShape = new Rectangle(0, y.get(), Settings.NODE_SIZE, Settings.NODE_SIZE);
		frostView.setClip(clipShape);

		clipShape.yProperty().bind(y);

		return frostView;
	}

	public static Vector<Noeud> convertViewToModel(Vector<ViewableNode> vAllNodes)
	{
		Vector<Noeud> vNoeuds = new Vector<Noeud>();
		for (ViewableNode one : vAllNodes)
		{
			vNoeuds.add(one);
		}
		return vNoeuds;
	}

	public static Noeud findTargetNodeFromSource(Edge conn, Noeud source)
	{
		Noeud target = null;
		Noeud from = conn.getStartPoint();
		Noeud to = conn.getEndPoint();
		if (from.getNodeId() != source.getNodeId())
		{
			target = from;
		} else if (to.getNodeId() != source.getNodeId())
		{
			target = to;
		}
		return target;
	}

}
