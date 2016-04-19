package view;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class ActionMenu extends ContextMenu
{
	private final MenuItem menuConn;
	private final MenuItem menuDisconn;
	private final MenuItem menuEdit;

	public ActionMenu()
	{
		super();
		menuConn = new MenuItem("Connects to");
		menuDisconn = new MenuItem("Disconnect with");
		menuEdit = new MenuItem("Edit label");
		this.getItems().addAll(menuConn, menuDisconn, menuEdit);
	}

}
