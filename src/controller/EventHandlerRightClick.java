package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.BaseNode;
import view.ActionMenu;

public class EventHandlerRightClick implements EventHandler<MouseEvent>
{
	BaseNode targetNoode;
	ActionMenu menuRightClick;

	public EventHandlerRightClick(BaseNode b)
	{
		targetNoode = b;
		menuRightClick = new ActionMenu(b);
	}

	@Override
	public void handle(MouseEvent event)
	{
		if (event.getButton() == MouseButton.SECONDARY)
		{
			menuRightClick.show(targetNoode.getFXNode(), event.getScreenX(), event.getScreenY());
		}
	}
}
