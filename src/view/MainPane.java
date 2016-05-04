package view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Point3D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.AlgoDijkstra;
import model.BaseNode;
import model.Edge;
import model.Noeud;
import model.NoeudStatus;
import model.Step;

public class MainPane extends Pane implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final Vector<ViewableNode> vAllNodes = new Vector<ViewableNode>();
	private final Vector<ViewableNode> vGroupSelectedNodes = new Vector<ViewableNode>();
	private final Vector<EdgeHint> vDisplayLines = new Vector<EdgeHint>();
	private final Vector<ViewableEdge> vConnections = new Vector<ViewableEdge>();
	private volatile boolean isPlayingAlgo = false;
	private Vector<Step> vAlgoSteps = null;
	private ViewableNode connectFrom;
	private boolean isSelectMode = false;
	private ActionType action = ActionType.NONE;
	private Rectangle groupSelection = null;
	private AlgoDijkstra algoDijkstra = null;
	private int algoPlayIndex = 0;

	public MainPane()
	{
		super();
		this.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
			if (isSelectMode && action == ActionType.ADD_CONNECTION)
			{
				displayEdgesHintsTo(event.getX(), event.getY(), event.getZ());
			}
		});
	}

	public Cube createCube()
	{
		Cube c = new Cube(Settings.NODE_SIZE, this);
		this.getChildren().add(c.getFXNode());
		vAllNodes.add(c);
		return c;
	}

	public Ball createBall()
	{
		Ball b = new Ball(Settings.NODE_SIZE, this);
		this.getChildren().add(b.getFXNode());
		vAllNodes.add(b);
		return b;
	}

	public Vector<ViewableNode> getAllNodes()
	{
		return vAllNodes;
	}

	public Vector<ViewableEdge> getAllConnection()
	{
		return vConnections;
	}

	public void displayEdgesHintsFrom(double x, double y, double z)
	{
		removeEdgesHints();
		for (ViewableNode n : vAllNodes)
		{
			EdgeHint line = new EdgeHint(n.getPosX(), n.getPosY(), x, y);
			this.getChildren().add(line);
			vDisplayLines.add(line);
		}
	}

	public void displayEdgesHintsTo(double x, double y, double z)
	{
		removeEdgesHints();
		EdgeHint line = new EdgeHint(connectFrom.getPosX(), connectFrom.getPosY(), x, y);
		this.getChildren().add(line);
		vDisplayLines.add(line);
	}

	public void removeEdgesHints()
	{
		if (vDisplayLines.size() > 0)
		{
			for (EdgeHint l : vDisplayLines)
			{
				this.getChildren().remove(l);
			}
			vDisplayLines.removeAllElements();
		}
	}

	public void startNodeSelectMode(ViewableNode source)
	{
		connectFrom = source;
		isSelectMode = true;
		HashMap<Integer, BaseNode> hmRelatedNodes = new HashMap<Integer, BaseNode>();
		for (Edge conn : source.getEdges())
		{
			BaseNode start = conn.getStartPoint();
			BaseNode end = conn.getEndPoint();
			hmRelatedNodes.put(start.getNodeId(), start);
			hmRelatedNodes.put(end.getNodeId(), end);
		}

		for (ViewableNode node : vAllNodes)
		{
			if (action == ActionType.ADD_CONNECTION)
			{
				hmRelatedNodes.remove(source.getNodeId());
				if (hmRelatedNodes.get(node.getNodeId()) != null)
				{
					node.setDisabled();
					for (Edge con : node.getEdges())
					{
						((ViewableEdge) con).setDisabled();
					}
				} else
				{
					if (node != source)
						node.setSelectMode(isSelectMode);
				}

			} else
			{
				if (hmRelatedNodes.get(node.getNodeId()) == null)
				{
					node.setDisabled();
					for (Edge con : node.getEdges())
					{
						((ViewableEdge) con).setDisabled();
					}
				} else
				{
					if (node != source)
						node.setSelectMode(isSelectMode);
				}
			}
		}
	}

	private void renableAllObjects()
	{
		for (ViewableNode node : vAllNodes)
		{
			node.setSelectMode(isSelectMode);
			node.setEnabled();
			for (Edge oneConn : node.getEdges())
			{
				((ViewableEdge) oneConn).setEnabled();
			}
		}
	}

	public void stopNodeSelectMode(ViewableNode source)
	{
		isSelectMode = false;
		ViewableEdge conn = new ViewableEdge(connectFrom, source);
		if (action == ActionType.ADD_CONNECTION)
		{
			conn.initGraphic(connectFrom, source);
			this.getChildren().add(0, conn.getFXNode());
			vConnections.add(conn);
		} else
		{

			int nFound = vConnections.indexOf(conn);
			conn = vConnections.get(nFound);
			this.getChildren().remove(conn.getFXNode());
			vConnections.remove(conn);
			conn.delete();
		}
		renableAllObjects();
		source.removeSelected();
		connectFrom.removeSelected();
		connectFrom.resetSelectedStartingNode();
		action = ActionType.NONE;
	}

	public boolean isNodeSelectMode()
	{
		return isSelectMode;
	}

	public void updateEdgesDisplay()
	{
		for (ViewableEdge conn : vConnections)
		{
			ViewableNode from = (ViewableNode) conn.getStartPoint();
			ViewableNode to = (ViewableNode) conn.getEndPoint();
			conn.moveTo(to.getPoint3D(), from.getPoint3D());
			conn.getFXNode().toBack();
		}
	}

	public void deleteNode(ViewableNode node)
	{
		Vector<ViewableEdge> conn2Remove = new Vector<ViewableEdge>();
		for (ViewableEdge conn : vConnections)
		{
			if (conn.getEndPoint().getNodeId() == node.getNodeId() || conn.getStartPoint().getNodeId() == node.getNodeId())
			{
				conn2Remove.add(conn);
			}
		}

		for (ViewableEdge conn : conn2Remove)
		{
			conn.delete();
			vConnections.remove(conn);
			this.getChildren().remove(conn.getFXNode());
		}

		vAllNodes.remove(node);
		this.getChildren().remove(node.getFXNode());
	}

	public void setAction(ActionType atype)
	{
		action = atype;
	}

	public void resetOtherStartNodes(ViewableNode node)
	{
		for (ViewableNode one : vAllNodes)
		{
			if (one.getNodeId() != node.getNodeId())
				one.setStartNode(false);
		}
	}

	public void resetOtherEndNodes(ViewableNode node)
	{
		for (ViewableNode one : vAllNodes)
		{
			if (one.getNodeId() != node.getNodeId())
				one.setEndNode(false);
		}
	}

	public void displayGroupSelection(double clickedX, double clickedY, double currentX, double currentY)
	{
		if (groupSelection == null)
		{
			groupSelection = new Rectangle();
			groupSelection.setArcWidth(1);
			groupSelection.setArcHeight(5);
			groupSelection.setStroke(Settings.SPECULAR_COLOR);
			groupSelection.setFill(null);
			groupSelection.setStrokeWidth(1);
		} else
		{
			this.getChildren().remove(groupSelection);
		}

		Point3D pointClicked = new Point3D(clickedX, clickedY, 0);
		// Point3D pointCurrent = new Point3D(currentX, currentY, 0);
		Point3D convertClicked = this.sceneToLocal(pointClicked);
		groupSelection.setX(convertClicked.getX());
		groupSelection.setY(convertClicked.getY());
		groupSelection.setWidth(currentX - clickedX);
		groupSelection.setHeight(currentY - clickedY);
		this.getChildren().add(groupSelection);

	}

	public void removeSelectedGroup()
	{
		for (ViewableNode node : vAllNodes)
		{
			node.removeSelected();
		}
		vGroupSelectedNodes.clear();
		ensureAllEdgesAtBack();
	}

	public void detectSelectedNodes(double clickedX, double clickedY, double currentX, double currentY)
	{
		for (ViewableNode node : vAllNodes)
		{
			Point3D p = new Point3D(node.getPosX(), node.getPosY(), node.getPosZ());
			Point3D pScene = this.localToScene(p);
			if (pScene.getX() > clickedX && pScene.getX() < currentX && pScene.getY() > clickedY && pScene.getY() < currentY)
			{
				addSelected(node);
			}
		}
		this.getChildren().remove(groupSelection);
	}

	public void addSelected(ViewableNode nodeToAdd)
	{
		if (!vGroupSelectedNodes.contains(nodeToAdd))
		{
			vGroupSelectedNodes.add(nodeToAdd);
			nodeToAdd.displaySelected();
		}
		ensureAllEdgesAtBack();
	}

	public void moveSelectedNodes(double deltaX, double deltaY)
	{
		for (ViewableNode selected : vGroupSelectedNodes)
		{
			selected.moveTo(deltaX, deltaY, 0);
		}
		updateEdgesDisplay();
		ensureAllEdgesAtBack();
	}

	public void startAlgo(ControlButton controlButton)
	{
		isPlayingAlgo = true;
		if (vAlgoSteps == null)
		{
			algoDijkstra = new AlgoDijkstra(Utility.convertViewToModel(vAllNodes));
			algoDijkstra.start();
			vAlgoSteps = algoDijkstra.getAnimationSteps();
		}

		if (vAlgoSteps.size() > 0)
			playAlgo(controlButton);
	}

	private void playAlgo(ControlButton controlButton)
	{
		Task<Void> longTask = new Task<Void>()
		{
			@Override
			protected Void call() throws Exception
			{
				while (algoPlayIndex < vAlgoSteps.size())
				{
					if (!isPlayingAlgo || isCancelled())
						break;

					Platform.runLater(new Runnable()
					{
						@Override
						public void run()
						{
							playOneStepAlgo(controlButton);
						}
					});
					Thread.sleep(1000);
				}

				return null;
			}
		};
		new Thread(longTask).start();
	}

	public void playOneStepAlgo(ControlButton controlButton)
	{
		displayOneStep(vAlgoSteps.get(algoPlayIndex));
		algoPlayIndex++;
		if (algoPlayIndex >= vAlgoSteps.size())
			controlButton.playEnd();
	}

	private void displayOneStep(Step step)
	{
		Noeud src = null;
		Noeud dest = null;
		for (ViewableEdge e : vConnections)
			e.setAlgoSource(false);

		Vector<Noeud> snapShot = step.getSnapShot();
		for (Noeud oneNode : snapShot)
		{
			ViewableNode snap = (ViewableNode) oneNode;
			if (snap.getStatus() == NoeudStatus.COMPARE_SRC)
				src = snap;
			else if (snap.getStatus() == NoeudStatus.COMPARE_DEST)
				dest = snap;
			else if (snap.getStatus() == NoeudStatus.SHORTEST)
			{
				markShortestEdges(snap);
			}

			int targetIndex = vAllNodes.indexOf(snap);
			if (targetIndex == -1)
			{
				System.out.println("Unable to find node:" + snap.toString());
			} else
			{
				ViewableNode target = vAllNodes.get(targetIndex);
				target.displaySnapShot(snap);
			}
		}

		if (src != null && dest != null)
		{
			Edge edge = new Edge(src, dest);
			int edgeIndex = vConnections.indexOf(edge);
			if (edgeIndex == -1)
			{
				System.out.println("Unable to find edge:" + edge.toString());
			} else
			{
				ViewableEdge hasEdge = vConnections.get(edgeIndex);
				hasEdge.setAlgoSource(true);
			}
		}

		ensureAllEdgesAtBack();
	}

	private void markShortestEdges(ViewableNode snap)
	{
		for (Edge e : snap.getEdges())
		{
			Noeud dest = Utility.findTargetNodeFromSource(e, snap);
			Edge edge = new Edge(snap, dest);
			if (dest.getStatus() == NoeudStatus.SHORTEST || dest.isStartNode() || dest.isEndNode())
			{
				int edgeIndex = vConnections.indexOf(edge);
				if (edgeIndex == -1)
				{
					System.out.println("Unable to find edge:" + edge.toString());
				} else
				{
					ViewableEdge hasEdge = vConnections.get(edgeIndex);
					hasEdge.setAlgoShortest(true);
				}
			}
		}
	}

	public void stopAlgo(ControlButton controlButton)
	{
		isPlayingAlgo = false;
	}

	public void ensureAllEdgesAtBack()
	{
		for (ViewableEdge e : vConnections)
		{
			e.getFXNode().toBack();
		}
	}

}
