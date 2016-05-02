package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import view.ActionMenu;
import view.ViewableNode;

public class EventHandlerRightClick implements EventHandler<MouseEvent>
{
	ViewableNode targetNode;
	ActionMenu menuRightClick;

	public EventHandlerRightClick(ViewableNode b)
	{
		targetNode = b;
		menuRightClick = new ActionMenu(b);
	}

	@Override
	public void handle(MouseEvent event)
	{
		if (!targetNode.isSelectMode())
		{
			if (event.getButton() == MouseButton.SECONDARY)
			{
				menuRightClick.refreshMenuItems();
				menuRightClick.show(targetNode.getFXNode(), event.getScreenX(), event.getScreenY());
			}
		}
	}
}
