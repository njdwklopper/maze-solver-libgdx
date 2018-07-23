package za.co.klppr.amazeing.maze;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class Solver {
    private final Array<Cell> grid;
    private final Array<Cell> path = new Array<Cell>();
    private final Array<Cell> stack = new Array<Cell>();
    private Cell currentCell;

    public Solver(final Array<Cell> grid, int startCellIndex) {
        this.grid = grid;
        this.currentCell = this.grid.get(startCellIndex);
    }

    public int resetVisit(int resetCellStart, int resetCellTarget) {
        currentCell = grid.get(resetCellStart);
        for (Cell cell : grid) {
            cell.setVisitedState(CellModel.STATE_VISTED);
        }
        grid.get(resetCellStart).setVisitedState(CellModel.STATE_START);
        grid.get(resetCellTarget).setVisitedState(CellModel.STATE_TARGET);
        return 4;
    }

    public void draw(final ShapeRenderer cellShape) {
        for (Cell cell : grid) {
            cell.draw(cellShape);
        }
    }

    public int searchPath() {

        Cell next = getNext();
        if (next != null) {
            if (next.getVisitedState() == CellModel.STATE_TARGET) {
                return 5;
            }
            next.setPath(true);

            setColorToState(next);

            stack.add(currentCell);

            currentCell = next;
        } else if (stack.size > 0) {
            currentCell = stack.pop();
            setColorToState(currentCell);
            if (stack.size == 0){
                return 6;
            }
        }

        return 4;
    }

    private Cell getNext() {
        Cell cell = Utils.getCellTop(grid, currentCell.getModel());
        if (cell != null && cell.getModel().visitedState != CellModel.STATE_PATH) {
            if (cell.getVisitedState() == CellModel.STATE_TARGET){
                return cell;
            }
            int wallCount = 0;
            CellModel model = cell.getModel();
            if (!model.bottom) {
                if (model.top) {
                    wallCount++;
                }
                if (model.right) {
                    wallCount++;
                }
                if (model.left) {
                    wallCount++;
                }
                if (wallCount < 3) {
                    return cell;
                }
            }
        }

        cell = Utils.getCellRight(grid, currentCell.getModel());
        if (cell != null && cell.getModel().visitedState != CellModel.STATE_PATH) {
            if (cell.getVisitedState() == CellModel.STATE_TARGET){
                return cell;
            }
            int wallCount = 0;
            CellModel model = cell.getModel();
            if (!model.left) {
                if (model.top) {
                    wallCount++;
                }
                if (model.right) {
                    wallCount++;
                }
                if (model.bottom) {
                    wallCount++;
                }
                if (wallCount < 3) {
                    return cell;
                }
            }
        }

        cell = Utils.getCellBottom(grid, currentCell.getModel());
        if (cell != null && cell.getModel().visitedState != CellModel.STATE_PATH) {
            if (cell.getVisitedState() == CellModel.STATE_TARGET){
                return cell;
            }
            int wallCount = 0;
            CellModel model = cell.getModel();
            if (!model.top) {
                if (model.right) {

                    wallCount++;
                }
                if (model.bottom) {
                    wallCount++;
                }
                if (model.left) {
                    wallCount++;
                }
                if (wallCount < 3) {
                    return cell;
                }
            }
        }

        cell = Utils.getCellLeft(grid, currentCell.getModel());
        if (cell != null && cell.getModel().visitedState != CellModel.STATE_PATH) {
            if (cell.getVisitedState() == CellModel.STATE_TARGET){
                return cell;
            }
            int wallCount = 0;
            CellModel model = cell.getModel();
            if (!model.right) {
                if (model.top) {
                    wallCount++;
                }
                if (model.bottom) {
                    wallCount++;
                }
                if (model.left) {
                    wallCount++;
                }
                if (wallCount < 3) {
                    return cell;
                }
            }
        }
        return null;
    }

    private void setColorToState(Cell cell) {
        switch (cell.getVisitedState()) {
            case CellModel.STATE_VISTED:
            case CellModel.STATE_BACKTRACKED:
                cell.setVisitedState(CellModel.STATE_PATH);
                break;
        }
    }

    public Cell getCurrentCell() {
        return currentCell;
    }
}
