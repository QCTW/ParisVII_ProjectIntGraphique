package view;

import controller.EventHandlerMove;
import controller.EventHandlerStartDrag;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import model.BaseNode;

public class Cube extends Box implements BaseNode
{
	private static final long serialVersionUID = 1L;
	private int nodeId;
	private double posX = 0; // Always = layoutX
	private double posY = 0; // Always = layoutY
	private double posZ = 0; // Always = layoutZ
	private String nodeLabel = "";

	public Cube(double size)
	{
		super(size, size, size);
		initGraphicSetting();
		if (size == Settings.ICON_WIDTH_SIZE) // If it is icon
			this.setOnDragDetected(new EventHandlerStartDrag(this));
		else // If this is a node in the graph
		{
			EventHandlerMove ehm = new EventHandlerMove(this);
			this.setOnMousePressed(ehm);
			this.setOnMouseDragged(ehm);
		}
	}

	private void initGraphicSetting()
	{
		nodeId = Utility.generateId();
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Settings.DIFFUSE_COLOR);
		material.setSpecularColor(Settings.SPECULAR_COLOR);
		this.setMaterial(material);
		// this.getTransforms().add(new Rotate(30, 0, 0, 0, Rotate.X_AXIS));
		// this.getTransforms().add(new Rotate(30, 0, 0, 0, Rotate.Y_AXIS));
		// this.setCullFace(CullFace.NONE);
		// this.setDrawMode(DrawMode.LINE);
	}

	@Override
	public int getNodeId()
	{
		return this.nodeId;
	}

	@Override
	public void moveTo(double x, double y, double z)
	{
		this.setTranslateX(x);
		this.setTranslateY(y);
		posX = x;
		posY = y;
		posZ = z;
		System.out.println("Cube moveTo(" + x + "," + y + "," + z + ") Layout(" + this.getLayoutX() + "," + this.getLayoutY() + ") Translate(" + this.getTranslateX() + "," + this.getTranslateY());
	}

	@Override
	public String getNodeLabel()
	{
		return nodeLabel;
	}

	@Override
	public void setNodeLabel(String sLabel)
	{
		nodeLabel = sLabel;
	}

	@Override
	public double getPosX()
	{
		return posX;
	}

	@Override
	public double getPosY()
	{
		return posY;
	}

	@Override
	public double getPosZ()
	{
		return posZ;
	}

}
