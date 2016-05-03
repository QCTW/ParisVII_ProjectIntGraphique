package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import view.MainPane;
import view.ViewableNode;

public class EventHandlerMove implements EventHandler<MouseEvent>
{
	private final MainPane targetMainPane;
	private final ViewableNode targetNode;
	private double orgSceneX;
	private double orgSceneY;
	private boolean unlock = true;

	public EventHandlerMove(ViewableNode node, MainPane mp)
	{
		targetNode = node;
		targetMainPane = mp;
	}

	@Override
	public void handle(MouseEvent event)
	{
		if (!targetNode.isSelectMode())
		{
			if(unlock){
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
					break;
	
				case "MOUSE_RELEASED":
					targetMainPane.removeSelectedGroup();
					break;
				}
			}
		}
		event.consume();
	}
	
	public void setListener(boolean onOrOff)
	{
		unlock = onOrOff;
	}

}
