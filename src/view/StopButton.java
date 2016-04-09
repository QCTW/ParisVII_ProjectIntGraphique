package view;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class StopButton extends Button
{
	
	public StopButton()
	{
		super();
		Icon iconStop = new Icon(Settings.IMAGE_BSTOP);
		this.setTooltip(new Tooltip("Stop demonstrating the algorithm"));
		this.setGraphic(iconStop);
		this.focusTraversableProperty().setValue(false);
	}
}
