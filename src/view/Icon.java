package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Icon extends ImageView
{

	public Icon(String url)
	{
		super();
		Image img = new Image(getClass().getResourceAsStream(url));
		this.setImage(img);
		this.preserveRatioProperty().set(true);
		this.setFitWidth(Settings.ICON_WIDTH_SIZE);
	}

	public void setSize(double width)
	{
		this.setFitWidth(width);
	}
}
