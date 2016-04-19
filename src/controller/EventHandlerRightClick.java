package controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import view.ActionMenu;

public class EventHandlerRightClick implements EventHandler<MouseEvent>
{
	Node targetNoode;
	ActionMenu menuRightClick;
	
	public EventHandlerRightClick(Node b)
	{
		targetNoode = b;
		menuRightClick = new ActionMenu();
	}
	
	@Override
	public void handle(MouseEvent event)
	{
		if (event.getButton() == MouseButton.SECONDARY)
		{
			menuRightClick.show(targetNoode, event.getScreenX(), event.getScreenY());
		}
	}
}
