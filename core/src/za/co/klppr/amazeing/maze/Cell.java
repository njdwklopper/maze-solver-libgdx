package za.co.klppr.amazeing.maze;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import static za.co.klppr.amazeing.maze.CellModel.STATE_BACKTRACKED;
import static za.co.klppr.amazeing.maze.CellModel.STATE_PATH;
import static za.co.klppr.amazeing.maze.CellModel.STATE_START;
import static za.co.klppr.amazeing.maze.CellModel.STATE_TARGET;
import static za.co.klppr.amazeing.maze.CellModel.STATE_VISTED;

public class Cell {

    private static final Color COLOR_VISITED = new Color(0, 0, 1, 1);
    private static final Color COLOR_BACKTRACKED = COLOR_VISITED;
    //private static final Color COLOR_BACKTRACKED = new Color(0, 0.4f, 0, 1);
    private static final Color COLOR_BACKGROUND = new Color(0.3f, 0, 0.1f, 1);
    private static final Color COLOR_START = new Color(0f, 1, 0f, 1);
    private static final Color COLOR_TARGET = new Color(1f, 0, 0f, 1);
    private static final Color COLOR_PATH = new Color(0, 0, 0f, 1);
    private static final Color COLOR_WALLS = new Color(1, 1, 1, 1);

    private CellModel model;

    private Cell(CellModel model) {
        this.model = model;
    }

    public CellModel getModel() {
        return model;
    }

    public Cell getRandomNeighbour(Array<Cell> grid) {
        Array<Cell> neighbors = new Array<Cell>();

        Cell top = Utils.getCellAtIndex(grid, model.row, model.col - 1, model.colTotal, model.rowTotal);
        Cell right = Utils.getCellAtIndex(grid, model.row + 1, model.col, model.colTotal, model.rowTotal);
        Cell bottom = Utils.getCellAtIndex(grid, model.row, model.col + 1, model.colTotal, model.rowTotal);
        Cell left = Utils.getCellAtIndex(grid, model.row - 1, model.col, model.colTotal, model.rowTotal);

        if (top != null && !top.model.visited) {
            neighbors.add(top);
        }
        if (right != null && !right.model.visited) {
            neighbors.add(right);
        }
        if (bottom != null && !bottom.model.visited) {
            neighbors.add(bottom);
        }
        if (left != null && !left.model.visited) {
            neighbors.add(left);
        }

        if (neighbors.size > 0) {
            int r = MathUtils.floor(MathUtils.random(0, neighbors.size - 1));
            return neighbors.get(r);
        } else {
            return null;
        }
    }

    public void draw(final ShapeRenderer cellShape) {
        drawBackGround(cellShape);
        drawWalls(cellShape);
    }

    private void drawWalls(final ShapeRenderer cellShape) {
        cellShape.begin(ShapeRenderer.ShapeType.Line);
        cellShape.setColor(COLOR_WALLS);
        float x = model.col * model.width;
        float y = model.row * model.height;
        if (model.top) {
            cellShape.line(x, y, x + model.width, y);
        }
        if (model.right) {
            cellShape.line(x + model.width, y, x + model.width, y + model.height);
        }
        if (model.bottom) {
            cellShape.line(x, y + model.height, x + model.width, y + model.height);
        }
        if (model.left) {
            cellShape.line(x, y, x, y + model.height);
        }
        cellShape.end();
    }

    private void drawBackGround(final ShapeRenderer cellShape) {
        cellShape.begin(ShapeRenderer.ShapeType.Filled);
        switch (model.visitedState) {
            case STATE_VISTED:
                cellShape.setColor(COLOR_VISITED);
                break;
            case STATE_BACKTRACKED:
                cellShape.setColor(COLOR_BACKTRACKED);
                break;
            case STATE_START:
                cellShape.setColor(COLOR_START);
                break;
            case STATE_PATH:
                cellShape.setColor(COLOR_PATH);
                break;
            case STATE_TARGET:
                cellShape.setColor(COLOR_TARGET);
                break;
            default:
                cellShape.setColor(COLOR_BACKGROUND);
                break;
        }
        cellShape.rect(model.col * model.width, model.row * model.height, model.width, model.height);
        cellShape.end();
    }

    public void removeWalls(Cell current, Cell next) {
        float x = current.model.col - next.model.col;
        if (x == 1) {
            current.model.left = false;
            next.model.right = false;
        } else if (x == -1) {
            current.model.right = false;
            next.model.left = false;
        }
        float y = current.model.row - next.model.row;
        if (y == 1) {
            current.model.top = false;
            next.model.bottom = false;
        } else if (y == -1) {
            current.model.bottom = false;
            next.model.top = false;
        }
    }

    public void setVisitedState(int visited) {
        this.model.visitedState = visited;
    }

    public void setVisited(boolean visited) {
        this.model.visited = visited;
    }

    public boolean isVisited() {
        return model.visited;
    }

    public int getVisitedState() {
        return model.visitedState;
    }

    void setPath(boolean isPath) {
        this.model.isPath = isPath;
    }

    public static Cell from(CellModel model) {
        return new Cell(model);
    }
}
