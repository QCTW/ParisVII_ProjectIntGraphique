package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import view.Settings;

public class VertexValueChangeListener implements ChangeListener<Number>
{
	private final Label targetLabel;

	public VertexValueChangeListener(Label label)
	{
		targetLabel = label;
	}

	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
	{
		targetLabel.relocate(targetLabel.getTranslateX() - newValue.doubleValue() / 2, targetLabel.getTranslateY() - Settings.NODE_SIZE);
	}

}
