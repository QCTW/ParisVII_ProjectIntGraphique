package view;

import controller.EventHandlerMove;
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

	public Ball(double size)
	{
		super(size / 2);
		initGraphicSetting();
		if (size == Settings.ICON_WIDTH_SIZE)
			this.setOnDragDetected(new EventHandlerStartDrag(this));
		else
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
		// this.setCullFace(CullFace.BACK);
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
		posX = this.getLayoutX();
		posY = this.getLayoutY();
		posZ = z;
		System.out.println("Ball moveTo(" + x + "," + y + "," + z + ") Layout(" + this.getLayoutX() + "," + this.getLayoutY() + ") Translate(" + this.getTranslateX() + "," + this.getTranslateY());
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
