package view;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

public class StopButton extends Button
{
	public StopButton(MainPane mp,ControlButton controlButton)
	{
		super();
		Icon iconStop = new Icon(Settings.IMAGE_BSTOP);
		this.setTooltip(new Tooltip("Stop demonstrating the algorithm"));
		this.setGraphic(iconStop);
		this.focusTraversableProperty().setValue(false);
		this.setDisable(true);
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
		mp.stopAlgo(controlButton);
		controlButton.playEnd();
		});
	}
}
