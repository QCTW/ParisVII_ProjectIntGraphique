package view;

import java.util.HashMap;
import java.util.Vector;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Point3D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.AlgoDijkstra;
import model.BaseNode;
import model.Edge;
import model.Graph;
import model.Noeud;
import model.NoeudStatus;
import model.Snapshot;

public class ViewableGraph extends Graph
{
	private static final long serialVersionUID = 1L;
	private final Vector<ViewableNode> vGroupSelectedNodes = new Vector<ViewableNode>();
	private final Vector<EdgeHint> vDisplayLines = new Vector<EdgeHint>();
	private volatile boolean isPlayingAlgo = false;
	private volatile int algoPlayIndex = 0;
	private final Pane mainPane = new Pane();
	private Vector<Snapshot> vAlgoSteps = null;
	private ViewableNode connectFrom;
	private boolean isSelectMode = false;
	private ActionType action = ActionType.NONE;
	private Rectangle groupSelection = null;
	private AlgoDijkstra algoDijkstra = null;
	private ControlPanel panel = null;
	private Task<Void> longPlayTask;

	public ViewableGraph()
	{
		super();
		mainPane.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
			if (isSelectMode && action == ActionType.ADD_CONNECTION)
			{
				displayEdgesHintsTo(event.getX(), event.getY(), event.getZ());
			}
		});
	}

	public Pane getFXNode()
	{
		return mainPane;
	}

	public Cube createCube()
	{
		Cube c = new Cube(Settings.NODE_SIZE, this);
		mainPane.getChildren().add(c.getFXNode());
		getAllNodes().add(c);
		return c;
	}

	public Ball createBall()
	{
		Ball b = new Ball(Settings.NODE_SIZE, this);
		mainPane.getChildren().add(b.getFXNode());
		getAllNodes().add(b);
		return b;
	}

	public void displayEdgesHintsFrom(double x, double y, double z)
	{
		removeEdgesHints();
		for (Noeud n : getAllNodes())
		{
			EdgeHint line = new EdgeHint(n.getPosX(), n.getPosY(), x, y);
			mainPane.getChildren().add(line);
			vDisplayLines.add(line);
		}
	}

	public void displayEdgesHintsTo(double x, double y, double z)
	{
		removeEdgesHints();
		EdgeHint line = new EdgeHint(connectFrom.getPosX(), connectFrom.getPosY(), x, y);
		mainPane.getChildren().add(line);
		vDisplayLines.add(line);
	}

	public void removeEdgesHints()
	{
		if (vDisplayLines.size() > 0)
		{
			for (EdgeHint l : vDisplayLines)
			{
				mainPane.getChildren().remove(l);
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

		for (ViewableNode node : getAllViewableNodes())
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
		for (ViewableNode node : getAllViewableNodes())
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
		if (source != null)
		{
			ViewableEdge conn = new ViewableEdge(connectFrom, source);
			if (action == ActionType.ADD_CONNECTION)
			{
				conn.initGraphic(connectFrom, source);
				mainPane.getChildren().add(0, conn.getFXNode());
				getAllEdges().add(conn);
			} else
			{

				int nFound = getAllEdges().indexOf(conn);
				conn = getAllViewableEdges().get(nFound);
				mainPane.getChildren().remove(conn.getFXNode());
				getAllEdges().remove(conn);
				conn.delete();
			}
			source.removeSelected();
		}
		renableAllObjects();
		if (connectFrom != null)
		{
			connectFrom.removeSelected();
			connectFrom.resetSelectedStartingNode();
		}
		action = ActionType.NONE;
	}

	public boolean isNodeSelectMode()
	{
		return isSelectMode;
	}

	public void updateEdgesDisplay()
	{
		for (ViewableNode node : vGroupSelectedNodes)
		{
			for (Edge conn : node.getEdges())
			{
				ViewableEdge edge = (ViewableEdge) conn;
				ViewableNode from = (ViewableNode) conn.getStartPoint();
				ViewableNode to = (ViewableNode) conn.getEndPoint();
				edge.moveTo(to.getPoint3D(), from.getPoint3D());
				edge.getFXNode().toBack();
			}
		}
	}

	public void deleteNode(ViewableNode node)
	{
		Vector<ViewableEdge> conn2Remove = new Vector<ViewableEdge>();
		for (ViewableEdge conn : getAllViewableEdges())
		{
			if (conn.getEndPoint().getNodeId() == node.getNodeId() || conn.getStartPoint().getNodeId() == node.getNodeId())
			{
				conn2Remove.add(conn);
			}
		}

		for (ViewableEdge conn : conn2Remove)
		{
			conn.delete();
			getAllEdges().remove(conn);
			mainPane.getChildren().remove(conn.getFXNode());
		}

		getAllNodes().remove(node);
		mainPane.getChildren().remove(node.getFXNode());
	}

	public void setAction(ActionType atype)
	{
		action = atype;
	}

	public void resetOtherStartNodes(ViewableNode node)
	{
		for (Noeud one : getAllNodes())
		{
			if (one.getNodeId() != node.getNodeId())
				one.setStartNode(false);
		}
	}

	public void resetOtherEndNodes(ViewableNode node)
	{
		for (Noeud one : getAllNodes())
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
			mainPane.getChildren().remove(groupSelection);
		}

		Point3D pointClicked = new Point3D(clickedX, clickedY, 0);
		// Point3D pointCurrent = new Point3D(currentX, currentY, 0);
		Point3D convertClicked = mainPane.sceneToLocal(pointClicked);
		groupSelection.setX(convertClicked.getX());
		groupSelection.setY(convertClicked.getY());
		groupSelection.setWidth(currentX - clickedX);
		groupSelection.setHeight(currentY - clickedY);
		mainPane.getChildren().add(groupSelection);

	}

	public void removeSelectedGroup()
	{
		for (ViewableNode node : getAllViewableNodes())
		{
			node.removeSelected();
		}
		vGroupSelectedNodes.clear();
		ensureAllEdgesAtBack();
	}

	public void detectSelectedNodes(double clickedX, double clickedY, double currentX, double currentY)
	{
		for (ViewableNode node : getAllViewableNodes())
		{
			Point3D p = new Point3D(node.getPosX(), node.getPosY(), node.getPosZ());
			Point3D pScene = mainPane.localToScene(p);
			if (pScene.getX() > clickedX && pScene.getX() < currentX && pScene.getY() > clickedY && pScene.getY() < currentY)
			{
				addSelected(node);
			}
		}
		mainPane.getChildren().remove(groupSelection);
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

	public void rewindAlgo()
	{
		stopAlgo();
		algoPlayIndex = 0;
		playAlgo(false);
	}

	public void startAlgoOneStepForward()
	{
		stopAlgo();
		runAlgoDijkstraIfNotExecuted();
		if (algoPlayIndex < vAlgoSteps.size())
			algoPlayIndex++;

		playAlgo(false);

	}

	public void startAlgoOneStepBackward()
	{
		stopAlgo();
		runAlgoDijkstraIfNotExecuted();
		if (algoPlayIndex > 0)
			algoPlayIndex--;

		playAlgo(false);
	}

	public void startAlgo()
	{
		isPlayingAlgo = true;
		runAlgoDijkstraIfNotExecuted();
		playAlgo(true);
	}

	private void runAlgoDijkstraIfNotExecuted()
	{
		if (vAlgoSteps == null)
		{
			algoDijkstra = new AlgoDijkstra(getAllNodes());
			algoDijkstra.start();
			vAlgoSteps = algoDijkstra.getAnimationSteps();
		}
	}

	private void playAlgo(boolean isStreaming)
	{
		if (vAlgoSteps.size() > 0)
		{
			isPlayingAlgo = true;
			if (isStreaming)
			{
				longPlayTask = new Task<Void>()
				{
					@Override
					protected Void call() throws Exception
					{
						while (algoPlayIndex < vAlgoSteps.size())
						{
							if (!isPlayingAlgo || isCancelled())
								break;

							playOneStepAlgo();
							algoPlayIndex++;
							Thread.sleep(1000);
						}
						return null;
					}
				};
				new Thread(longPlayTask).start();
			} else
			{
				isPlayingAlgo = false;
				playOneStepAlgo();
			}
		}
	}

	private void playOneStepAlgo()
	{
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				if (algoPlayIndex < vAlgoSteps.size())
					displayOneStep(vAlgoSteps.get(algoPlayIndex));

				if (algoPlayIndex == vAlgoSteps.size() - 1)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("End of Algorithm");
					alert.setHeaderText("Dijkstra's algorithm has finished");
					alert.setContentText("The shortest nodes/path will be marked in white.");
					alert.showAndWait();
				}
				updateControlPanel();
			}
		});
	}

	private void displayOneStep(Snapshot step)
	{
		Noeud src = null;
		Noeud dest = null;
		for (ViewableEdge e : getAllViewableEdges())
			e.setAlgoSource(false);

		Vector<Noeud> snapShot = step.getSnapShot();
		for (Noeud oneNode : snapShot)
		{
			Noeud snap = oneNode;
			if (snap.getStatus() == NoeudStatus.COMPARE_SRC)
				src = snap;
			else if (snap.getStatus() == NoeudStatus.COMPARE_DEST)
				dest = snap;
			else if (snap.getStatus() == NoeudStatus.SHORTEST)
			{
				markShortestEdges(snap);
			}

			int targetIndex = getAllNodes().indexOf(snap);
			if (targetIndex == -1)
			{
				System.out.println("Unable to find node:" + snap.toString());
			} else
			{
				ViewableNode target = getAllViewableNodes().get(targetIndex);
				target.displaySnapShot(snap);
			}
		}

		if (src != null && dest != null)
		{
			Edge edge = new Edge(src, dest);
			int edgeIndex = getAllEdges().indexOf(edge);
			if (edgeIndex == -1)
			{
				System.out.println("Unable to find edge:" + edge.toString());
			} else
			{
				ViewableEdge hasEdge = getAllViewableEdges().get(edgeIndex);
				hasEdge.setAlgoSource(true);
			}
		}

		if (algoDijkstra.getShortestPathNodes().size() == 0 && algoPlayIndex == vAlgoSteps.size() - 1)
		{
			Edge edge = new Edge(algoDijkstra.getStartNode(), algoDijkstra.getEndNode());
			int edgeIndex = getAllEdges().indexOf(edge);
			if (edgeIndex == -1)
			{
				System.out.println("Unable to find edge:" + edge.toString());
			} else
			{
				ViewableEdge hasEdge = getAllViewableEdges().get(edgeIndex);
				hasEdge.setAlgoShortest(true);
			}
		}

		ensureAllEdgesAtBack();
	}

	private void markShortestEdges(Noeud snap)
	{
		for (Edge e : snap.getEdges())
		{
			Noeud dest = Utility.findTargetNodeFromSource(e, snap);
			Edge edge = new Edge(snap, dest);
			if (dest.getStatus() == NoeudStatus.SHORTEST || dest.isStartNode() || dest.isEndNode())
			{
				int edgeIndex = getAllEdges().indexOf(edge);
				if (edgeIndex == -1)
				{
					System.out.println("Unable to find edge:" + edge.toString());
				} else
				{
					ViewableEdge hasEdge = getAllViewableEdges().get(edgeIndex);
					hasEdge.setAlgoShortest(true);
				}
			}
		}
	}

	public void stopAlgo()
	{
		isPlayingAlgo = false;
		if (longPlayTask != null)
			longPlayTask.cancel();

		updateControlPanel();
	}

	public void ensureAllEdgesAtBack()
	{
		for (ViewableEdge e : getAllViewableEdges())
		{
			e.getFXNode().toBack();
		}
	}

	public void playReset()
	{
		stopAlgo();
		longPlayTask = null;
		if (vAlgoSteps != null)
			vAlgoSteps.clear();

		vAlgoSteps = null;
		algoPlayIndex = 0;
		for (ViewableNode one : getAllViewableNodes())
		{
			one.resetNodeStatus();
		}

		for (ViewableEdge e : getAllViewableEdges())
		{
			e.resetStatus();
		}
	}

	public void setControlPanel(ControlPanel controlpanel)
	{
		panel = controlpanel;
	}

	public void updateControlPanel()
	{
		panel.update(algoPlayIndex, vAlgoSteps);
		if (!isPlayingAlgo)
			panel.getControlButton().setPlay();
		else
			panel.getControlButton().setPause();
	}

	public void cancelCurrentAction()
	{
		stopNodeSelectMode(null);
		removeEdgesHints();
	}

}
