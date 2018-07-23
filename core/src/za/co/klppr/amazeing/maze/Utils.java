package za.co.klppr.amazeing.maze;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Utils {

    public static Cell getCellAtIndex(Array<Cell> grid, CellModel model) {
        int index = getIndex(model);
        return index == -1 ? null : grid.get(index);
    }

    public static Cell getCellAtIndex(Array<Cell> grid, float row, float col, int colTotal, int rowTotal) {
        int index = getIndex(grid, row, col, colTotal, rowTotal);
        return index == -1 ? null : grid.get(index);
    }

    public static int getIndex(CellModel model) {
        if (model.col < 0 || model.row < 0 || model.col >= model.colTotal || model.row >= model.rowTotal) {
            return -1;
        }
        return MathUtils.floor(model.col + model.row * model.colTotal);
    }

    public static int getIndex(Array<Cell> grid, float row, float col, int colTotal, int rowTotal) {
        if (col < 0 || row < 0 || col >= colTotal || row >= rowTotal) {
            return -1;
        }
        return MathUtils.floor(col + row * colTotal);
    }

    public static Cell getCellTop(Array<Cell> grid, CellModel model) {
        return getCellAtIndex(grid, model.row - 1, model.col, model.colTotal, model.rowTotal);
    }

    public static Cell getCellRight(Array<Cell> grid, CellModel model) {
        return getCellAtIndex(grid, model.row, model.col + 1, model.colTotal, model.rowTotal);
    }

    public static Cell getCellBottom(Array<Cell> grid, CellModel model) {
        return getCellAtIndex(grid, model.row + 1, model.col, model.colTotal, model.rowTotal);
    }

    public static Cell getCellLeft(Array<Cell> grid, CellModel model) {
        return getCellAtIndex(grid, model.row, model.col - 1, model.colTotal, model.rowTotal);
    }
}
