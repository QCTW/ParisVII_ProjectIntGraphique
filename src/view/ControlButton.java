package view;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

public class ControlButton extends Button
{
	private final MainPane contentArea;

	public ControlButton(MainPane mp)
	{
		super();
		contentArea = mp;
		Icon iconPause = new Icon(Settings.IMAGE_BPAUSE);
		Icon iconControl = new Icon(Settings.IMAGE_BPLAY);
		this.setTooltip(new Tooltip("Start to demonstrate the algorithm"));
		this.setGraphic(iconControl);
		this.focusTraversableProperty().setValue(false);
		this.setDisable(true);
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			if (this.getGraphic() == iconControl)
			{
				this.getTooltip().setText("Pause");
				this.setGraphic(iconPause);
				contentArea.stopAlgo();
			} else
			{
				this.getTooltip().setText("Start to demonstrate the algorithm");
				this.setGraphic(iconControl);
				contentArea.startAlgo();
			}

		});
	}

}
