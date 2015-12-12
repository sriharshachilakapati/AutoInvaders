package com.shc.ld34.game;

import com.shc.ld34.game.states.PlayState;
import com.shc.silenceengine.core.Display;
import com.shc.silenceengine.core.Game;
import com.shc.silenceengine.core.SilenceEngine;
import com.shc.silenceengine.graphics.Graphics2D;
import com.shc.silenceengine.graphics.cameras.OrthoCam;
import com.shc.silenceengine.input.Keyboard;

/**
 * @author Sri Harsha Chilakapati
 */
public class AutoInvaders extends Game
{
    public static final int CANVAS_WIDTH  = 1280;
    public static final int CANVAS_HEIGHT = 720;

    public static void main(String[] args)
    {
        new AutoInvaders().start();
    }

    @Override
    public void preInit()
    {
        Display.setTitle("AutoInvaders - LD34 Compo entry");
        Display.setSize(1280, 720);
        Display.centerOnScreen();
    }

    @Override
    public void init()
    {
        Resources.init();
        Resources.Sounds.MUSIC.play();

        setGameState(new PlayState());
    }

    @Override
    public void resize()
    {
        // Resize the game according to aspect ratio
        Graphics2D g2d = SilenceEngine.graphics.getGraphics2D();
        OrthoCam cam = g2d.getCamera();

        float displayWidth = Display.getWidth();
        float displayHeight = Display.getHeight();

        float aspectRatio = Display.getAspectRatio();

        float cameraWidth, cameraHeight;

        if (displayWidth < displayHeight)
        {
            cameraWidth = CANVAS_WIDTH;
            cameraHeight = CANVAS_WIDTH / aspectRatio;
        }
        else
        {
            cameraWidth = CANVAS_HEIGHT * aspectRatio;
            cameraHeight = CANVAS_HEIGHT;
        }

        cam.initProjection(cameraWidth, cameraHeight);
        cam.center(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
    }

    @Override
    public void update(float delta)
    {
        if (Keyboard.isClicked(Keyboard.KEY_ESCAPE))
            Game.end();

        if (Keyboard.isClicked(Keyboard.KEY_F1))
            Display.setFullScreen(!Display.isFullScreen());

        Display.setTitle("AutoInvaders - LD34 Compo entry | FPS: " + Game.getFPS() + " UPS: " + Game.getUPS() + " RC: "
                         + (int) (SilenceEngine.graphics.renderCallsPerFrame));
    }

    @Override
    public void dispose()
    {
        Resources.dispose();
    }
}
