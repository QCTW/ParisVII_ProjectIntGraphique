package view;

import javafx.scene.shape.Box;

public class Cube extends ViewableNode
{
	private static final long serialVersionUID = 1L;
	private final Box box;

	public Cube(double size)
	{
		this(size, null);
	}

	public Cube(double size, ViewableGraph mp)
	{
		super(size, mp);
		box = new Box(size, size, size);
		box.setMaterial(super.getMaterial());
		super.getFXNode().getChildren().add(0, box);
		super.setNodeLabel("Cube-" + this.getNodeId());
		// this.getTransforms().add(new Rotate(30, 0, 0, 0, Rotate.X_AXIS));
		// this.getTransforms().add(new Rotate(30, 0, 0, 0, Rotate.Y_AXIS));
		// this.setCullFace(CullFace.NONE);
		// this.setDrawMode(DrawMode.LINE);
	}
}
