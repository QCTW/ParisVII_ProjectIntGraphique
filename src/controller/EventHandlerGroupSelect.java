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
			targetNode.removeSelectedGroup();
			break;

		case "MOUSE_DRAGGED":
			targetNode.displayGroupSelection(clickedX, clickedY, event.getSceneX(), event.getSceneY());
			break;

		case "MOUSE_RELEASED":
			targetNode.detectSelectedNodes(clickedX, clickedY, event.getSceneX(), event.getSceneY());
			break;
		}
		event.consume();
	}
}
