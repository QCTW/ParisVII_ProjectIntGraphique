package controller;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import view.Ball;
import view.Cube;

public class EventHandlerDragAndDrop implements EventHandler<DragEvent>
{

	StackPane targetNode;
	EDragOperation onOperation;

	public EventHandlerDragAndDrop(StackPane node, EDragOperation o)
	{
		targetNode = node;
		onOperation = o;
	}

	@Override
	public void handle(DragEvent event)
	{
		System.out.println(onOperation + " triggered");
		Dragboard db = event.getDragboard();
		switch (onOperation)
		{
		case DragOver:
			if (event.getGestureSource() != targetNode && event.getDragboard().hasString())
			{
				event.acceptTransferModes(TransferMode.MOVE);
			}
			break;
		case DragDone:
			break;
		case DragDropped:
			boolean success = false;
			if (db.hasString())
			{
				String objName = db.getString();
				if (objName.equals(Cube.class.getClass().getSimpleName()))
				{
					Cube c = new Cube();
					targetNode.getChildren().add(c);
				} else if (objName.equals(Ball.class.getClass().getSimpleName()))
				{
					Ball b = new Ball();
					targetNode.getChildren().add(b);
				}
				success = true;
			}
			/*
			 * let the source know whether the string was successfully
			 * transferred and used
			 */
			event.setDropCompleted(success);
			break;
		case DragEntered:
			if (db.hasString())
			{
				String objName = db.getString();
				if (objName.equals(Cube.class.getClass().getSimpleName()))
				{
					Cube c = new Cube();
					targetNode.getChildren().add(c);
				} else if (objName.equals(Ball.class.getClass().getSimpleName()))
				{
					Ball b = new Ball();
					targetNode.getChildren().add(b);
				}
			}
			break;
		case DragExited:
			break;
		default:
			break;
		}
		event.consume();
	}

}
