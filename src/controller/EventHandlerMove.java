package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.BaseNode;
import view.MainPane;

public class EventHandlerMove implements EventHandler<MouseEvent>
{
	private final MainPane targetMainPane;
	private final BaseNode targetNode;
	private double orgSceneX;
	private double orgSceneY;

	public EventHandlerMove(BaseNode node, MainPane mp)
	{
		targetNode = node;
		targetMainPane = mp;
	}

	@Override
	public void handle(MouseEvent event)
	{
		if (!targetNode.isSelectMode())
		{
			String sEventName = event.getEventType().getName();
			switch (sEventName)
			{
			case "MOUSE_PRESSED":
				orgSceneX = event.getSceneX();
				orgSceneY = event.getSceneY();
				targetMainPane.addSelected(targetNode);
				break;

			case "MOUSE_DRAGGED":
				double offsetX = event.getSceneX() - orgSceneX;
				double offsetY = event.getSceneY() - orgSceneY;
				targetMainPane.moveSelectedNodes(offsetX, offsetY);
				targetMainPane.updateEdgesDisplay();
				break;

			case "MOUSE_RELEASED":
				targetMainPane.removeSelectedGroup();
				break;
			}
			event.consume();
		}
	}

}
