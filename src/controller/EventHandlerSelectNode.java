package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.BaseNode;
import view.MainPane;

public class EventHandlerSelectNode implements EventHandler<MouseEvent>
{
	BaseNode targetNode;
	MainPane parentPane;

	public EventHandlerSelectNode(BaseNode node, MainPane pane)
	{
		targetNode = node;
		parentPane = pane;
	}

	@Override
	public void handle(MouseEvent event)
	{
		if (targetNode.isSelectMode())
		{
			String sEventName = event.getEventType().getName();
			switch (sEventName)
			{
			case "MOUSE_PRESSED":
				parentPane.selectNodeTo(targetNode);
				break;
			case "MOUSE_ENTERED":
				targetNode.displaySelected();
				break;
			case "MOUSE_EXITED":
				targetNode.removeSelected();
				break;
			}
		}
	}

}
