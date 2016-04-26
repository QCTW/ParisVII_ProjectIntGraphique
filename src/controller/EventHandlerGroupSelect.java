package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import view.MainPane;

public class EventHandlerGroupSelect implements EventHandler<MouseEvent>
{

	MainPane targetNode;
	double clickedX, clickedY;

	public EventHandlerGroupSelect(MainPane node)
	{
		targetNode = node;
	}

	@Override
	public void handle(MouseEvent event)
	{
		String sEventName = event.getEventType().getName();
		switch (sEventName)
		{
		case "MOUSE_PRESSED":
			clickedX = event.getSceneX();
			clickedY = event.getSceneY();
			break;

		case "MOUSE_DRAGGED":
			double currentX = event.getSceneX();
			double currentY = event.getSceneY();
			targetNode.displayGroupSelection(clickedX, clickedY, currentX, currentY);
			break;

		case "MOUSE_RELEASED":
			targetNode.displaySelectedGroup();
			break;
		}
		event.consume();
	}
}
