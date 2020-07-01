package algorithm.pathfinder;

import algorithm.AbstractAlgorithm;
import algorithm.mazegenerator.MazeGenerator;
import algorithm.mazegenerator.RandomisedPrim;
import algorithm.search.Search;
import algorithm.sort.Sort;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.text.Text;
import model.BoardModel;
import model.Vertex;
import model.VisualiserModel;

public abstract class AbstractPathfinder extends AbstractAlgorithm implements Pathfinder {
    private ObservableList<Vertex> fringe = FXCollections.observableArrayList();
    private BoardModel model;
    private ObjectProperty<Vertex> startVertex = new SimpleObjectProperty<>(),
            endVertex = new SimpleObjectProperty<>(),
            currentVertex = new SimpleObjectProperty<>();
    private BooleanProperty startSetProperty = new SimpleBooleanProperty(false),
            endSetProperty = new SimpleBooleanProperty(false),
            mazeGeneratedProperty = new SimpleBooleanProperty(false);

    public AbstractPathfinder() {
        startVertex.addListener((obs, ov, nv) -> {
            if (nv != null) {
                startSetProperty.set(true);
            } else {
                startSetProperty.set(false);
            }
        });

        endVertex.addListener((obs, ov, nv) -> {
            if (nv != null) {
                endSetProperty.set(true);
            } else {
                endSetProperty.set(false);
            }
        });


    }

    public void setModel(VisualiserModel model) {
        this.model = (BoardModel) model;
    }

    public BooleanProperty mazeGeneratedProperty() {
        return mazeGeneratedProperty;
    }

    public void generateMaze() {
        startVertex.set(null);
        endVertex.set(null);

        mazeGeneratedProperty.set(true);
        MazeGenerator mazeGenerator = new RandomisedPrim();

        mazeGenerator.setModel(model);

        mazeGenerator.generateMaze();

        visualise();
    }

    @Override
    public void setVertices(Vertex start, Vertex end) {
        assert(start != null && end != null);
        startVertex.setValue(start);
        endVertex.setValue(end);
    }

    public boolean isEndOrStart(Vertex vertex) {
        return vertex.equals(startVertex.get()) || vertex.equals(endVertex.get());
    }

    public ObservableList<Vertex> getFringe() {
        return fringe;
    }


    @Override
    public boolean hasNext() {
        return !endVertex.get().isVisited() && !getFringe().isEmpty();
    }


    public void visualise() {
        for (Vertex vertex : model.getBoard()) {
            if (vertex.equals(currentVertex.get()))
                vertex.setCurrent();
            else {
                if (vertex.isWall())
                    vertex.setWall(true);
                else if (isEndOrStart(vertex))
                    vertex.setEnd();
                else if (vertex.isVisited())
                    vertex.setVisited(true);
                else if (fringe.contains(vertex))
                    vertex.setFringe();
                else
                    vertex.resetStyle();
            }
        }
    }

    public void showResult() {
        if (!endVertex.get().isVisited()) {
            System.out.println("No path available");
            return;
        }
        var vertex = endVertex.get().getParentVertex();
        while (vertex != startVertex.get()) {
            vertex.setResult();
            vertex = vertex.getParentVertex();
        }
        endVertex.get().setEnd();
    }


    protected Vertex visitSmallestVertex(ObservableMap<Vertex, Double> map) {
        // Get the fringe node with smallest f value
        var currentVertex = getFringe().get(0);
        for (Vertex fringeNode : getFringe()) {
            if (map.get(fringeNode) < map.get(currentVertex)) {
                currentVertex = fringeNode;
            }
        }
        getFringe().remove(currentVertex);
        currentVertex.setVisited(true);
        return currentVertex;
    }

    protected void updateNeighbours(Vertex currentVertex, ObservableMap<Vertex, Double> map) {
        for (Vertex neighbour : currentVertex.getNeighbours()) {
            if (neighbour.isWall())
                continue;

            double distance = currentVertex.gValue() +
                    (currentVertex.getNonDiagNeighbours().contains(neighbour) ? NON_DIAG_COST : DIAG_COST);

            if (!neighbour.isVisited()) {
                if (getFringe().contains(neighbour)) {
                    if (distance < neighbour.gValue()) {
                        neighbour.setGValue(distance);
                        map.put(neighbour, neighbour.gValue() + neighbour.hValue());
                        neighbour.setParentVertex(currentVertex);
                    }
                } else {
                    getFringe().add(neighbour);
                    neighbour.setGValue(distance);
                    map.put(neighbour, neighbour.gValue() + neighbour.hValue());
                    neighbour.setParentVertex(currentVertex);
                }
            }
        }
    }


    public ObjectProperty<Vertex> startVertexProperty() {
        return startVertex;
    }

    public BoardModel getModel() {
        return model;
    }

    public ObjectProperty<Vertex> endVertexProperty() {
        return endVertex;
    }

    public ObjectProperty<Vertex> currentVertexProperty() {
        return currentVertex;
    }

    public boolean isSort() {
        return false;
    }

    public boolean isSearch() {
        return false;
    }

    public boolean isPathfinder() {
        return true;
    }

    public boolean isMazeGenerator() {return false;}

    public Pathfinder asPathfinder() {
        return this;
    }

    public Sort asSort() {
        return null;
    }

    public Search asSearch() {
        return null;
    }

    public MazeGenerator asMazeGenerator() {return null;}

    @Override
    public void setVertexEventHandlers(Vertex vertex, boolean played, Text statusText) {
        // Clicked, change state of vertices
        vertex.setOnMouseClicked(e -> {
            if (!played || vertex.isWall() || (isGraphTraversal() && !mazeGeneratedProperty().get())) {
                return;
            }
            if (startVertex.get() == vertex) {
                vertex.resetStyle();
                startVertex.set(null);
            } else if (endVertex.get() == vertex) {
                vertex.resetStyle();
                endVertex.set(null);
            } else if (!startIsSet()) {
                vertex.setStart();
                startVertex.set(vertex);
            } else if (!endIsSet()) {
                vertex.setEnd();
                endVertex.set(vertex);
            }
            setVertices(startVertex.get(), endVertex.get());

        });

        startSetProperty.addListener((obs, ov, nv) -> {
//            System.out.println("start nv: " + nv);
            if (!nv)
                statusText.setText("Select starting point");
            else
                statusText.setText("Select end point");

        });

        endSetProperty.addListener((obs, ov, nv) -> {
//            System.out.println("end nv: " + nv);
            if (!startIsSet())
                statusText.setText("Select starting point");
            else if (!nv)
                statusText.setText("Select end point");
            else
                statusText.setText("Press play to start");
        });


        mazeGeneratedProperty.addListener((obs, ov, nv) -> {
            if (nv)
                statusText.setText("Select starting point");
        });
    }

    @Override
    public boolean isGraphTraversal() {
        return false;
    }

    public boolean startIsSet() {
        return startVertex.get() != null;
    }

    public boolean endIsSet() {
        return endVertex.get() != null;
    }

    public boolean canPlay() {
        return startIsSet() && endIsSet();
    }


}
