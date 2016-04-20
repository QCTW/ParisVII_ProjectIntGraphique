package view;

import controller.EventHandlerMove;
import controller.EventHandlerRightClick;
import controller.EventHandlerStartDrag;
import controller.LabelTextChangeListener;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import model.BaseNode;

public class Ball extends Group implements BaseNode
{
	private static final long serialVersionUID = 1L;
	private int nodeId = 0;
	private double posX = 0;
	private double posY = 0;
	private double posZ = 0;
	private String nodeLabel;
	private final MainPane contentArea;
	private final Sphere sphere;
	private final Label label;

	public Ball(double size)
	{
		this(size, null);
	}

	public Ball(double size, MainPane mp)
	{
		contentArea = mp;
		sphere = new Sphere(size / 2);
		this.getChildren().add(sphere);
		label = new Label();
		this.getChildren().add(label);
		initGraphicSetting(size);
		if (size == Settings.ICON_WIDTH_SIZE)
			this.setOnDragDetected(new EventHandlerStartDrag(this));
		else
		{
			EventHandlerMove ehm = new EventHandlerMove(this);
			this.setOnMousePressed(ehm);
			this.setOnMouseDragged(ehm);
			this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandlerRightClick(this));
		}
	}

	private void initGraphicSetting(double size)
	{
		if (size != Settings.ICON_WIDTH_SIZE)
		{
			nodeId = Utility.generateId();
			nodeLabel = "Ball-" + nodeId;
			label.setText(nodeLabel);
			label.widthProperty().addListener(new LabelTextChangeListener(label));
		}
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Settings.DIFFUSE_COLOR);
		material.setSpecularColor(Settings.SPECULAR_COLOR);
		sphere.setMaterial(material);
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
		posX = x;
		posY = y;
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
		label.setText(nodeLabel);
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

	@Override
	public Node getFXNode()
	{
		return this;
	}

	@Override
	public void displaySelected()
	{
		SelectedRing sr = new SelectedRing(Settings.NODE_SIZE / 2 + 3);
		contentArea.getChildren().add(sr);
		sr.relocate(getPosX() - Settings.NODE_SIZE / 2, getPosY() - Settings.NODE_SIZE / 2);
	}

}
