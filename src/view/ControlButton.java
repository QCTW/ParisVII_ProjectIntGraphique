package view;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class ControlButton extends Button
{

	public ControlButton()
	{
		super();
		Icon iconControl = new Icon(Settings.IMAGE_BPLAY);
		this.setTooltip(new Tooltip("Save current graph to a file"));
		this.setGraphic(iconControl);
	}

}
