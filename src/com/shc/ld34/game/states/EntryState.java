package com.shc.ld34.game.states;

import com.shc.ld34.game.Resources;
import com.shc.ld34.game.entities.Bullet;
import com.shc.silenceengine.core.Game;
import com.shc.silenceengine.core.GameState;
import com.shc.silenceengine.core.SilenceEngine;
import com.shc.silenceengine.graphics.Batcher;
import com.shc.silenceengine.graphics.Graphics2D;
import com.shc.silenceengine.input.Keyboard;

import static com.shc.ld34.game.AutoInvaders.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class EntryState extends GameState
{
    @Override
    public void update(float delta)
    {
        if (Keyboard.isClicked(Keyboard.KEY_LEFT_ALT) || Keyboard.isClicked(Keyboard.KEY_RIGHT_ALT))
            Game.setGameState(new PlayState());
    }

    @Override
    public void render(float delta, Batcher batcher)
    {
        Graphics2D g2d = SilenceEngine.graphics.getGraphics2D();
        g2d.drawTexture(Resources.Textures.BACKGROUND, 0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        float x = CANVAS_WIDTH / 2 - Resources.Textures.LOGO.getWidth();
        float y = CANVAS_HEIGHT / 2 - Resources.Textures.LOGO.getHeight();
        g2d.drawTexture(Resources.Textures.LOGO, x, y, 640, 294);

        g2d.drawTexture(Resources.Textures.KEY_ALT, 10, CANVAS_HEIGHT - 56);
        g2d.drawTexture(Resources.Textures.KEY_ALT, CANVAS_WIDTH - 74, CANVAS_HEIGHT - 56);

        Bullet.NUM_HIT_ENEMY = Math.min(Bullet.NUM_HIT_ENEMY, Bullet.NUM_SHOT_PLAYER);

        String accuracy = "ACCURACY: " + PlayState.ACCURACY + "%";
        g2d.drawString(accuracy, CANVAS_WIDTH - 84 - g2d.getFont().getWidth(accuracy), CANVAS_HEIGHT - g2d.getFont().getHeight() - 20);

        String score = "SCORE: " + PlayState.SCORE;
        float cx = CANVAS_WIDTH/2 - g2d.getFont().getWidth(score)/2;
        float cy = CANVAS_HEIGHT - g2d.getFont().getHeight() - 20;
        g2d.drawString(score, cx, cy);

        g2d.drawString("PRESS [ALT] TO START PLAYING!", 84, cy);
    }
}
