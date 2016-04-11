package controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class EventHandlerStartDrag implements EventHandler<MouseEvent>
{
	Node targetNode;

	public EventHandlerStartDrag(Node node)
	{
		targetNode = node;
	}

	@Override
	public void handle(MouseEvent event)
	{
		Dragboard db = targetNode.startDragAndDrop(TransferMode.COPY_OR_MOVE);
		WritableImage img = targetNode.snapshot(new SnapshotParameters(), null);
		ClipboardContent content = new ClipboardContent();
		content.putString(targetNode.getClass().getSimpleName());
		db.setContent(content);
		db.setDragView(img);
		;
		event.consume();
	}

}
