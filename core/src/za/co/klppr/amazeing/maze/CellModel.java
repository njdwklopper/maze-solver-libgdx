package za.co.klppr.amazeing.maze;

public class CellModel {

    public static final byte STATE_INIT = 1;
    public static final byte STATE_VISTED = 2;
    public static final byte STATE_BACKTRACKED = 3;
    public static final byte STATE_START = 4;
    public static final byte STATE_TARGET = 5;
    public static final byte STATE_PATH = 6;

    int col;
    int row;

    float width;
    float height;

    int colTotal;
    int rowTotal;

    boolean top = true;
    boolean right = true;
    boolean bottom = true;
    boolean left = true;

    boolean visited = false;
    boolean isPath = false;

    int visitedState = STATE_INIT;

    private CellModel() {
    }

    public static CellModel from(int col, int row, float width, float height, int colTotal, int rowTotal) {
        CellModel model = new CellModel();
        model.col = col;
        model.row = row;
        model.width = width / colTotal;
        model.height = height / rowTotal;
        model.colTotal = colTotal;
        model.rowTotal = rowTotal;
        return model;
    }
}
