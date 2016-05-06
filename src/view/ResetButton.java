package view;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

public class ResetButton extends Button
{
	private final ViewableGraph contentArea;

	public ResetButton(ViewableGraph mp)
	{
		super();
		contentArea = mp;
		Icon iconStop = new Icon(Settings.IMAGE_BRESET);
		this.setTooltip(new Tooltip("Back to first step"));
		this.setGraphic(iconStop);
		this.focusTraversableProperty().setValue(false);
		this.setDisable(true);
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			contentArea.rewindAlgo();

		});
	}
}
