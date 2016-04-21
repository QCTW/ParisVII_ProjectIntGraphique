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

	public ActionMenu(BaseNode actionNode)
	{
		super();
		Icon iconConnect = new Icon(Settings.IMAGE_MITEM_CONNECT);
		menuConn = new MenuItem("Connect to", iconConnect);
		menuConn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				actionNode.displaySelected();
				actionNode.selectMode(true);
			}
		});
		Icon iconDisconnect = new Icon(Settings.IMAGE_MITEM_DISCONNECT);
		menuDisconn = new MenuItem("Disconnect with", iconDisconnect);
		menuDisconn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				actionNode.displaySelected();
				// actionNode.selectMode(false);
			}
		});
		Icon iconEdit = new Icon(Settings.IMAGE_MITEM_EDIT);
		menuEdit = new MenuItem("Edit label", iconEdit);
		menuEdit.setOnAction(event -> {
			TextInputDialog rename = new TextInputDialog(actionNode.getNodeLabel());
			rename.setTitle("Edit Label");
			rename.setHeaderText("Rename this node");
			rename.setContentText("Edit the label of this node to ");
			Optional<String> result = rename.showAndWait();
			result.ifPresent(name -> actionNode.setNodeLabel(result.get()));
		});
		this.getItems().addAll(menuConn, menuDisconn, menuEdit);
	}

}
