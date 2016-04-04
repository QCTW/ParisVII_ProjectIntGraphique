package controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class EventHandlerMouseOverGlow implements EventHandler<MouseEvent>
{
	Node targetNode;

	public EventHandlerMouseOverGlow(Node nActionNode)
	{
		targetNode = nActionNode;
	}

	@Override
	public void handle(MouseEvent event)
	{
		if (event.getEventType() == MouseEvent.MOUSE_ENTERED)
		{

		}
	}

}
