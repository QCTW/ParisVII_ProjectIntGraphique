package view;

import java.util.Optional;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;

public class ActionMenu extends ContextMenu
{
	private final ViewableNode targetNode;
	private final MenuItem menuConn;
	private final MenuItem menuDisconn;
	private final MenuItem menuEdit;
	private final MenuItem menuDelete;
	private final MenuItem menuStartPoint;
	private final MenuItem menuEndPoint;

	public ActionMenu(ViewableNode actionNode)
	{
		super();
		targetNode = actionNode;
		Icon iconConnect = new Icon(Settings.IMAGE_MITEM_CONNECT);
		menuConn = new MenuItem("Connect to", iconConnect);
		menuConn.setOnAction(event -> {
			actionNode.setAction(ActionType.ADD_CONNECTION);
			actionNode.displaySelected();
			actionNode.selectMode(true);
		});

		Icon iconDisconnect = new Icon(Settings.IMAGE_MITEM_DISCONNECT);
		menuDisconn = new MenuItem("Disconnect with", iconDisconnect);
		menuDisconn.setOnAction(event -> {
			actionNode.setAction(ActionType.REMOVE_CONNECTION);
			actionNode.displaySelected();
			actionNode.selectMode(true);

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

		Icon iconDelete = new Icon(Settings.IMAGE_MITEM_DELETE);
		menuDelete = new MenuItem("Delete node", iconDelete);
		menuDelete.setOnAction(event -> {
			actionNode.delete();
		});

		Icon iconSetStartPoint = new Icon(Settings.IMAGE_MITEM_STARTPOINT);
		menuStartPoint = new MenuItem("Set as START", iconSetStartPoint);
		menuStartPoint.setOnAction(event -> {
			actionNode.setStartNode(true);
		});

		Icon iconSetEndPoint = new Icon(Settings.IMAGE_MITEM_ENDPOINT);
		menuEndPoint = new MenuItem("Set as END", iconSetEndPoint);
		menuEndPoint.setOnAction(event -> {
			actionNode.setEndNode(true);
		});
		this.getItems().addAll(menuConn, menuDisconn, menuEdit, menuDelete, menuStartPoint, menuEndPoint);
	}

	public void refreshMenuItems()
	{
		if (!targetNode.hasNodeToConnect())
			menuConn.setDisable(true);
		else
			menuConn.setDisable(false);

		if (!targetNode.hasEdgeToDisconnect())
			menuDisconn.setDisable(true);
		else
			menuDisconn.setDisable(false);
	}

}
