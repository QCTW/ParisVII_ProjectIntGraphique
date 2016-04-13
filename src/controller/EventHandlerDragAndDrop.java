package controller;

import java.util.Vector;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.Line;
import model.BaseNode;
import view.Ball;
import view.Cube;
import view.MainPane;
import view.Settings;

public class EventHandlerDragAndDrop implements EventHandler<DragEvent>
{

	MainPane targetNode;

	public EventHandlerDragAndDrop(MainPane node)
	{
		targetNode = node;
	}

	@Override
	public void handle(DragEvent event)
	{
		String sEventName = event.getEventType().getName();
		Dragboard db = event.getDragboard();
		switch (sEventName)
		{
		case "DRAG_OVER":
			if (event.getGestureSource() != targetNode && event.getDragboard().hasString())
			{
				event.acceptTransferModes(TransferMode.MOVE);
				double x = event.getX();
				double y = event.getY();
				double z = event.getZ();
				targetNode.displayAllEdgesFrom(x, y, z);
			}
			break;
		case "DRAG_DONE":
			break;
		case "DRAG_DROPPED":
			boolean success = false;
			if (db.hasString())
			{
				String objName = db.getString();
				System.out.println(objName + " dropped at X=" + event.getX() + ", Y=" + event.getY() + ", Z=" + event.getZ());
				if (objName.equals("Cube"))
				{
					Cube c = targetNode.createCube();
					c.moveTo(event.getX(), event.getY(), event.getZ());
				} else if (objName.equals("Ball"))
				{
					Ball b = targetNode.createBall();
					b.moveTo(event.getX(), event.getY(), event.getZ());
				}
				success = true;
			}
			/*
			 * let the source know whether the string was successfully
			 * transferred and used
			 */
			event.setDropCompleted(success);
			break;
		case "DRAG_ENTERED":
			break;
		case "DRAG_EXITED":
			break;
		default:
			break;
		}
		event.consume();
	}

}
