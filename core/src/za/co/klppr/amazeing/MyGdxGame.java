package za.co.klppr.amazeing;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import za.co.klppr.amazeing.maze.Grid;
import za.co.klppr.amazeing.maze.Solver;

public class MyGdxGame extends ApplicationAdapter implements GestureDetector.GestureListener {

    int state = 0;
    float timeSinceColslision = 0;
    private Grid grid;
    private Solver solver;

    private static final byte DELTA = 2;

    private ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(new GestureDetector(this));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.08f, 0.08f, 0.08f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        switch (state) {
            case 0:
                grid = Grid.from(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 16 * DELTA, 16 * DELTA);
                state = 1;
                break;
            case 1:
                //timeSinceCollision++;
                //if (timeSinceCollision > 30.0f) {
                grid.drawC(shapeRenderer);
                state = grid.draw();
                //}
                break;
            case 7:
                grid.drawC(shapeRenderer);
                break;
            case 2:
                grid = null;
                Gdx.gl.glClearColor(1, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
                state = 0;
                break;
            case 3:
                solver = new Solver(grid.getGrid(), grid.getStartingCellIndex());
                state = 4;
                break;
            case 4:
                solver.draw(shapeRenderer);
                state = solver.searchPath();
                break;
            case 5:
                solver.draw(shapeRenderer);
                break;
            case 6:
                state = solver.resetVisit(grid.getStartingCellIndex(), grid.getTargetCellIndex());
                break;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            state = 2;
        }
    }

    @Override
    public void dispose() {
        grid.dispose();
        shapeRenderer.dispose();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        switch (state) {
            case 7:
                state = 3;
                break;
            case 5:
                state = 2;
                break;
        }
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
