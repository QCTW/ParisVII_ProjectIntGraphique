package view;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

public class ControlButton extends Button
{
	private final MainPane contentArea;
	private boolean isPlay = true;
	Icon iconPause = new Icon(Settings.IMAGE_BPAUSE);
	Icon iconPlay = new Icon(Settings.IMAGE_BPLAY);

	public ControlButton(MainPane mp)
	{
		super();
		contentArea = mp;
		this.setTooltip(new Tooltip("Start to demonstrate the algorithm"));
		this.setGraphic(iconPlay);
		this.focusTraversableProperty().setValue(false);
		this.setDisable(true);
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			if (isPlay)
			{
				isPlay = false;
				this.getTooltip().setText("Pause");
				this.setGraphic(iconPause);
				contentArea.startAlgo(this);
			} else
			{
				isPlay = true;
				this.getTooltip().setText("Start to demonstrate the algorithm");
				this.setGraphic(iconPlay);
				contentArea.stopAlgo(this);
			}

		});
	}

	public void playEnd()
	{
		isPlay = true;
		this.getTooltip().setText("Start to demonstrate the algorithm");
		this.setGraphic(iconPlay);
	}

}
