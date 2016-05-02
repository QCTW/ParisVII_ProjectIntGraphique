package view;

import javafx.scene.shape.Sphere;

public class Ball extends ViewableNode
{
	private static final long serialVersionUID = 1L;
	private final Sphere sphere;

	public Ball(double size)
	{
		this(size, null);
	}

	public Ball(double size, MainPane mp)
	{
		super(size, mp);
		sphere = new Sphere(size / 2);
		sphere.setMaterial(super.getMaterial());
		super.getFXNode().getChildren().add(0, sphere);
		super.setNodeLabel("Ball-" + this.getNodeId());
		// this.setCullFace(CullFace.BACK);
		// this.setDrawMode(DrawMode.LINE);
	}

}
