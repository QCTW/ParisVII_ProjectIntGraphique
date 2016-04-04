package view;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class StepButton extends Button
{

	public StepButton()
	{
		super();
		Icon iconControl = new Icon(Settings.IMAGE_BSTEP);
		this.setTooltip(new Tooltip("Play one step"));
		this.setGraphic(iconControl);
		this.focusTraversableProperty().setValue(false);
	}

}
