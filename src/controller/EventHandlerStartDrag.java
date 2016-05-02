package controller;

import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import view.ViewableNode;

public class EventHandlerStartDrag implements EventHandler<MouseEvent>
{
	ViewableNode targetNode;

	public EventHandlerStartDrag(ViewableNode node)
	{
		targetNode = node;
	}

	@Override
	public void handle(MouseEvent event)
	{
		Dragboard db = targetNode.getFXNode().startDragAndDrop(TransferMode.COPY_OR_MOVE);
		SnapshotParameters spParams = new SnapshotParameters();
		spParams.setFill(Color.TRANSPARENT);
		WritableImage img = targetNode.getFXNode().snapshot(spParams, null);
		ClipboardContent content = new ClipboardContent();
		content.putString(targetNode.getClass().getSimpleName());
		db.setContent(content);
		db.setDragView(img);
		event.consume();
	}

}
