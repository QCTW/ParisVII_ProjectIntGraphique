package controller;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import view.Ball;
import view.Cube;
import view.MainPane;
import view.Settings;
import view.Utility;

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
		System.out.println(sEventName + " triggered");
		Dragboard db = event.getDragboard();
		switch (sEventName)
		{
		case "DRAG_OVER":
			if (event.getGestureSource() != targetNode && event.getDragboard().hasString())
			{
				event.acceptTransferModes(TransferMode.MOVE);
			}
			break;
		case "DRAG_DONE":
			break;
		case "DRAG_DROPPED":
			boolean success = false;
			if (db.hasString())
			{
				String objName = db.getString();
				System.out.println(objName + "/X=" + event.getX() + ", Y=" + event.getY() + ", Z=" + event.getZ());
				if (objName.equals("Cube"))
				{
					Cube c = new Cube(Settings.NODE_SIZE);
					targetNode.getChildren().add(c);
					c.moveTo(Utility.adjustPosFromCenter2TopLeft(event.getX()), Utility.adjustPosFromCenter2TopLeft(event.getY()), event.getZ());
				} else if (objName.equals("Ball"))
				{
					Ball b = new Ball(Settings.NODE_SIZE);
					targetNode.getChildren().add(b);
					b.moveTo(Utility.adjustPosFromCenter2TopLeft(event.getX()), Utility.adjustPosFromCenter2TopLeft(event.getY()), event.getZ());
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
