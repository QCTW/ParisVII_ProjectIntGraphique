package view;

import controller.EventHandlerStartDrag;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import model.BaseNode;

public class Ball extends Sphere implements BaseNode
{
	private static final long serialVersionUID = 1L;
	private int nodeId;
	private double posX = 0;
	private double posY = 0;
	private double posZ = 0;
	private String nodeLabel = "";

	// Call this constructor only for icon/graphic usage
	public Ball()
	{
		super(Settings.NODE_SIZE / 2);
		initGraphicSetting();
		this.setOnDragDetected(new EventHandlerStartDrag(this));
	}

	// Call this constructor when put a new node in the MainPane
	public Ball(double x, double y, double z)
	{
		super(Settings.NODE_SIZE / 2);
		initGraphicSetting();
		posX = x;
		posY = y;
		posZ = z;
		this.relocate(x, y);
		System.out.println("Ball layout: X=" + this.getLayoutX() + " Y=" + this.getLayoutY());
	}

	private void initGraphicSetting()
	{
		nodeId = Utility.generateId();
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Settings.DIFFUSE_COLOR);
		material.setSpecularColor(Settings.SPECULAR_COLOR);
		this.setMaterial(material);
		// this.setCullFace(CullFace.BACK);
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
