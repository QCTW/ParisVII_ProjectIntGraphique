package view;

import controller.EventHandlerStartDrag;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import model.BaseNode;

public class Cube extends Box implements BaseNode
{
	private static final long serialVersionUID = 1L;
	private int nodeId;
	private double posX = 0;
	private double posY = 0;
	private double posZ = 0;
	private String nodeLabel = "";

	// Call this constructor only for icon/graphic usage
	public Cube()
	{
		super(Settings.NODE_SIZE, Settings.NODE_SIZE, Settings.NODE_SIZE);
		initGraphicSetting();
		this.setOnDragDetected(new EventHandlerStartDrag(this));
	}

	// Call this constructor when put a new node in the MainPane
	public Cube(double x, double y, double z)
	{
		super(Settings.NODE_SIZE, Settings.NODE_SIZE, Settings.NODE_SIZE);
		initGraphicSetting();
		posX = x;
		posY = y;
		posZ = z;
		this.relocate(x, y);
		System.out.println("Cube layout: X=" + this.getLayoutX() + " Y=" + this.getLayoutY());
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
	public void moveTo(int x, int y, int z)
	{
		posX = x;
		posY = y;
		posZ = z;
		this.setLayoutX(x);
		this.setLayoutY(y);
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
