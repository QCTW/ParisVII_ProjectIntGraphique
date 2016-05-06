package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import view.ViewableGraph;
import view.ViewableNode;

public class EventHandlerSelectNode implements EventHandler<MouseEvent>
{
	ViewableNode targetNode;
	ViewableGraph parentPane;

	public EventHandlerSelectNode(ViewableNode node, ViewableGraph pane)
	{
		targetNode = node;
		parentPane = pane;
	}

	@Override
	public void handle(MouseEvent event)
	{
		if (targetNode.isSelectMode() && !targetNode.isSelectedStartingNode())
		{
			String sEventName = event.getEventType().getName();
			switch (sEventName)
			{
			case "MOUSE_CLICKED":
				parentPane.stopNodeSelectMode(targetNode);
				parentPane.removeEdgesHints();
				break;
			case "MOUSE_ENTERED":
				targetNode.displaySelected();
				break;
			case "MOUSE_EXITED":
				targetNode.removeSelected();
				break;
			}
		}
		event.consume();
	}

}
