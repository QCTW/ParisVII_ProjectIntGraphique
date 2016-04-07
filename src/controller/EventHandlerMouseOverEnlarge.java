package controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class EventHandlerMouseOverEnlarge implements EventHandler<MouseEvent>
{
	Node targetNode;

	public EventHandlerMouseOverEnlarge(Node nActionNode)
	{
		targetNode = nActionNode;
	}

	@Override
	public void handle(MouseEvent event)
	{
		if (event.getEventType() == MouseEvent.MOUSE_ENTERED)
		{
			targetNode.setScaleX(1.2);
			targetNode.setScaleY(1.2);
			targetNode.setScaleZ(1.2);
		} else if (event.getEventType() == MouseEvent.MOUSE_EXITED)
		{
			targetNode.setScaleX(1);
			targetNode.setScaleY(1);
			targetNode.setScaleZ(1);
		}
	}

}
