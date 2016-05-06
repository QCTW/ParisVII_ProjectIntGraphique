package view;

import javafx.scene.Group;

public class MultiModeIcon extends Group
{
	private final Icon icons[];
	private Icon current = null;

	public MultiModeIcon(int numberOfMode)
	{
		icons = new Icon[numberOfMode];
	}

	public void initImage(String path)
	{
		Icon icon = new Icon(path);
		icons[0] = icon;
		current = icon;
		this.getChildren().add(current);
	}

	public void setImage(int modeIndex, String path)
	{
		Icon icon = new Icon(path);
		icons[modeIndex] = icon;
	}

	public void swtichToMode(int modeNumber)
	{
		if (current != null)
			this.getChildren().remove(current);

		current = icons[modeNumber];
		this.getChildren().add(current);
	}

}
