package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.BaseNode;
import view.ActionMenu;

public class EventHandlerRightClick implements EventHandler<MouseEvent>
{
	BaseNode targetNode;
	ActionMenu menuRightClick;

	public EventHandlerRightClick(BaseNode b)
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
				menuRightClick.show(targetNode.getFXNode(), event.getScreenX(), event.getScreenY());
			}
		}
	}
}
