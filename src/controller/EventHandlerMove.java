package controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import model.BaseNode;
import view.Ball;
import view.Cube;

public class EventHandlerMove implements EventHandler<MouseEvent>
{
	BaseNode targetNoode;
	private double orgSceneX;
	private double orgSceneY;
	private double orgTranslateX;
	private double orgTranslateY;

	public EventHandlerMove(Cube c)
	{
		targetNoode = c;
	}

	public EventHandlerMove(Ball b)
	{
		targetNoode = b;
	}

	@Override
	public void handle(MouseEvent event)
	{
		String sEventName = event.getEventType().getName();
		switch (sEventName)
		{
		case "MOUSE_PRESSED":
			orgSceneX = event.getSceneX();
			orgSceneY = event.getSceneY();
			orgTranslateX = ((Node) event.getSource()).getTranslateX();
			orgTranslateY = ((Node) event.getSource()).getTranslateY();
			break;

		case "MOUSE_DRAGGED":
			double offsetX = event.getSceneX() - orgSceneX;
			double offsetY = event.getSceneY() - orgSceneY;
			double newTranslateX = orgTranslateX + offsetX;
			double newTranslateY = orgTranslateY + offsetY;
			targetNoode.moveTo(newTranslateX, newTranslateY, 0);
			break;
		}
	}

}
