package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import view.MainPane;

public class EventHandlerGroupSelect implements EventHandler<MouseEvent>
{

	private final MainPane targetNode;
	private double clickedX, clickedY;
	private boolean bStartGrouping = false;

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
			bStartGrouping = true;
			break;

		case "MOUSE_DRAGGED":
			if (bStartGrouping)
				targetNode.displayGroupSelection(clickedX, clickedY, event.getSceneX(), event.getSceneY());
			break;

		case "MOUSE_RELEASED":
			targetNode.detectSelectedNodes(clickedX, clickedY, event.getSceneX(), event.getSceneY());
			bStartGrouping = false;
			break;
		}
		event.consume();
	}
}
