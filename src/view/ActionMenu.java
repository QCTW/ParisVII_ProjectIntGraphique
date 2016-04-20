package view;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import model.BaseNode;

public class ActionMenu extends ContextMenu
{
	private final MenuItem menuConn;
	private final MenuItem menuDisconn;
	private final MenuItem menuEdit;
	private BaseNode nodeParent;

	public ActionMenu(BaseNode actionNode)
	{
		super();
		nodeParent = actionNode;
		menuConn = new MenuItem("Connects to");
		menuConn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				actionNode.displaySelected();
			}
		});
		menuDisconn = new MenuItem("Disconnect with");
		menuEdit = new MenuItem("Edit label");
		menuEdit.setOnAction(event->
		{	
			TextInputDialog rename = new TextInputDialog(nodeParent.getNodeLabel());
			rename.setTitle("Rename the point");
			rename.setHeaderText("Rename your point");
			rename.setContentText("Please enter the new name of the point:");
			Optional<String> result = rename.showAndWait();
			result.ifPresent(name -> nodeParent.setNodeLabel(result.get()));
		});
		this.getItems().addAll(menuConn, menuDisconn, menuEdit);
	}

}
