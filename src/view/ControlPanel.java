package view;

import java.util.Vector;

import javafx.scene.layout.HBox;
import model.Step;

public class ControlPanel extends HBox
{
	private final ControlButton bControlButton;
	private final StepForwardButton bStepForwardButton;
	private final StepBackButton bStepBackButton;
	private final ResetButton bResetButton;

	public ControlPanel(MainPane contentArea)
	{
		super();
		bControlButton = new ControlButton(contentArea);
		bStepForwardButton = new StepForwardButton(contentArea);
		bStepBackButton = new StepBackButton(contentArea);
		bResetButton = new ResetButton(contentArea);
		this.setSpacing(Settings.PADDING_IN_BAR);
		this.getChildren().addAll(bControlButton, bStepBackButton, bResetButton, bStepForwardButton);
	}

	public void setDisableAll(boolean newValue)
	{
		bControlButton.setDisable(newValue);
		bStepForwardButton.setDisable(newValue);
		bStepBackButton.setDisable(newValue);
		bResetButton.setDisable(newValue);
	}

	public ControlButton getControlButton()
	{
		return bControlButton;
	}

	public StepForwardButton getStepForwardButton()
	{
		return bStepForwardButton;
	}

	public StepBackButton getStepBackButton()
	{
		return bStepBackButton;
	}

	public ResetButton getStopButton()
	{
		return bResetButton;
	}

	public void update(int algoPlayIndex, Vector<Step> vAlgoSteps)
	{
		if (algoPlayIndex >= vAlgoSteps.size())
		{
			bControlButton.end();
			bStepForwardButton.setDisable(true);
		} else if (algoPlayIndex == 0)
		{
			bStepBackButton.setDisable(true);
			bResetButton.setDisable(true);
		} else
		{
			bStepForwardButton.setDisable(false);
			bStepBackButton.setDisable(false);
			bResetButton.setDisable(false);
		}
	}

}
