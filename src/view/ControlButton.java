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
				setPause();
				contentArea.startAlgo();
			} else
			{
				setPlay();
				contentArea.stopAlgo();
			}
		});
	}

	public void setPause()
	{
		isPlay = false;
		this.getTooltip().setText("Pause current demonstration");
		this.setGraphic(iconPause);
	}

	public void setPlay()
	{
		isPlay = true;
		this.getTooltip().setText("Start to demonstrate the algorithm");
		this.setGraphic(iconPlay);
	}

	public void end()
	{
		isPlay = true;
		this.getTooltip().setText("Start to demonstrate the algorithm");
		this.setGraphic(iconPlay);
		this.setDisable(true);
	}

}
