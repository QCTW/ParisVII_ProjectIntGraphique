package view;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

public class StepForwardButton extends Button
{
	private final ViewableGraph contentArea;

	public StepForwardButton(ViewableGraph mp)
	{
		super();
		contentArea = mp;
		Icon iconControl = new Icon(Settings.IMAGE_BSTEP_FORWARD);
		this.setTooltip(new Tooltip("Play one step forward"));
		this.setGraphic(iconControl);
		this.focusTraversableProperty().setValue(false);
		this.setDisable(true);
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			contentArea.startAlgoOneStepForward();
		});
	}

}
