package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import model.BaseNode;

public class ActionMenu extends ContextMenu
{
	private final MenuItem menuConn;
	private final MenuItem menuDisconn;
	private final MenuItem menuEdit;

	public ActionMenu(BaseNode actionNode)
	{
		super();
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
		this.getItems().addAll(menuConn, menuDisconn, menuEdit);
	}

}
