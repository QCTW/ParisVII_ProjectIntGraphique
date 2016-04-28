package view;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SelectedRing extends Group
{
	RotateTransition rt;

	public SelectedRing(double radious)
	{
		super();
		// circle = new Circle(radious / 1.5 + 5);
		// circle.setStroke(Settings.SPECULAR_COLOR);
		// circle.setFill(null);
		// circle.setStrokeWidth(3);

		double designedR = radious * 2 + 6;
		Image img = new Image(getClass().getResourceAsStream(Settings.IMAGE_SELECT));
		ImageView iv = new ImageView(img);

		rt = new RotateTransition(Duration.millis(3000), iv);
		rt.setByAngle(180);
		rt.setCycleCount(Animation.INDEFINITE);

		iv.preserveRatioProperty().set(true);
		iv.setFitWidth(designedR);
		// iv.setOpacity(0.5);

		this.getChildren().add(iv);
		iv.relocate(-(designedR / 2), -(designedR / 2));
		rt.play();
	}

	@Override
	public ObservableList<Node> getChildren()
	{
		rt.jumpTo(Duration.ZERO);
		return super.getChildren();
	}

}
