package za.co.klppr.amazeing.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import za.co.klppr.amazeing.MyGdxGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        //config.fullscreen = true;
        config.forceExit = true;

        config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
        //If I want to test windowed
        //boolean fullscreen = false;
        //if(!fullscreen ){
            config.fullscreen = false;
            config.width /= 1.2f;
            config.height /= 1.2f;
        //}
        config.resizable = false;
        config.samples = 4;
        config.vSyncEnabled = true;

        new LwjglApplication(new MyGdxGame(), config);
    }
}
