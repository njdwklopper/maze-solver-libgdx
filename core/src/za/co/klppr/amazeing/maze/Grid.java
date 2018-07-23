package za.co.klppr.amazeing.maze;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class Grid {

    private final Array<Cell> grid = new Array<Cell>();
    private final Array<Cell> stack = new Array<Cell>();

    private Cell currentCell;
    private int startingCellIndex = 0;
    private int targetCellIndex = 0;

    private Grid(float width, float height, int rowTotal, int colTotal) {
        for (int row = 0; row < rowTotal; row++) {
            for (int col = 0; col < colTotal; col++) {
                grid.add(Cell.from(CellModel.from(col, row, width, height, colTotal, rowTotal)));
            }
        }
        //startingCellIndex = MathUtils.floor(MathUtils.random(0, grid.size - 1));
        startingCellIndex = Utils.getIndex(grid, rowTotal - 1, 0, colTotal, rowTotal);
        currentCell = grid.get(startingCellIndex);
        currentCell.setVisited(true);
        currentCell.setVisitedState(CellModel.STATE_START);
        //targetCellIndex = MathUtils.floor(MathUtils.random(0, grid.size - 1));
        targetCellIndex = Utils.getIndex(grid, 0, colTotal - 1, colTotal, rowTotal);
        grid.get(targetCellIndex).setVisitedState(CellModel.STATE_TARGET);
    }

    public Array<Cell> getGrid() {
        return grid;
    }

    public int getStartingCellIndex() {
        return startingCellIndex;
    }

    public int getTargetCellIndex() {
        return targetCellIndex;
    }

    public void drawC(final ShapeRenderer cellShape){
        for (Cell cell : grid) {
            cell.draw(cellShape);
        }
    }

    public int draw() {
        Cell next = currentCell.getRandomNeighbour(grid);
        if (next != null) {
            next.setVisited(true);

            setColorToState(next);

            // STEP 2
            stack.add(currentCell);

            // STEP 3
            next.removeWalls(currentCell, next);

            // STEP 4
            currentCell = next;
        } else if (stack.size > 0) {
            currentCell = stack.pop();
            setColorToState(currentCell);
            if (stack.size == 0) {
                return 7;
            }
        }
        return 1;
    }

    private void setColorToState(Cell cell) {
        switch (cell.getVisitedState()) {
            case CellModel.STATE_VISTED:
                cell.setVisitedState(CellModel.STATE_BACKTRACKED);
                break;
            case CellModel.STATE_INIT:
                cell.setVisitedState(CellModel.STATE_VISTED);
                break;
        }
    }

    public static Grid from(float width, float height, int rowTotal, int colTotal) {
        return new Grid(width, height, rowTotal, colTotal);
    }

    public void dispose() {
        currentCell = null;
        grid.clear();
        stack.clear();
    }
}
