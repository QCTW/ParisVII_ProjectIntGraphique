package view;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class ControlButton extends Button
{

	public ControlButton()
	{
		super();
		Icon iconControl = new Icon(Settings.IMAGE_BPLAY);
		this.setTooltip(new Tooltip("Start to demonstrate the algorithm"));
		this.setGraphic(iconControl);
		this.focusTraversableProperty().setValue(false);
	}

}
