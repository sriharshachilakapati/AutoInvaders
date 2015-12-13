package com.shc.ld34.game.states;

import com.shc.ld34.game.Resources;
import com.shc.ld34.game.entities.Bullet;
import com.shc.ld34.game.entities.EnemyShip;
import com.shc.ld34.game.entities.MotherShip;
import com.shc.ld34.game.entities.NewWave;
import com.shc.ld34.game.entities.PlayerShip;
import com.shc.silenceengine.collision.broadphase.DynamicTree2D;
import com.shc.silenceengine.collision.colliders.SceneCollider2D;
import com.shc.silenceengine.core.Game;
import com.shc.silenceengine.core.GameState;
import com.shc.silenceengine.core.SilenceEngine;
import com.shc.silenceengine.graphics.Batcher;
import com.shc.silenceengine.graphics.Graphics2D;
import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.scene.Scene2D;
import com.shc.silenceengine.utils.MathUtils;
import org.lwjgl.openal.AL10;

import static com.shc.ld34.game.AutoInvaders.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class PlayState extends GameState
{
    public static boolean BOSS_WAVE;
    public static Scene2D SCENE;

    public static int WAVE;
    public static int SCORE;
    public static int ACCURACY;
    public static int LIVES;

    private SceneCollider2D collider;

    @Override
    public void onEnter()
    {
        WAVE = 0;
        BOSS_WAVE = false;
        SCORE = 0;
        LIVES = 5;

        Bullet.NUM_HIT_ENEMY = Bullet.NUM_SHOT_PLAYER = 0;

        SCENE = new Scene2D();
        SCENE.addChild(new PlayerShip());

        createWave();

        collider = new SceneCollider2D(new DynamicTree2D());
        collider.setScene(SCENE);

        collider.register(PlayerShip.class, Bullet.class);
        collider.register(EnemyShip.class, Bullet.class);
        collider.register(MotherShip.class, Bullet.class);
    }

    @Override
    public void update(float delta)
    {
        SCENE.update(delta);
        collider.checkCollisions();

        if (LIVES <= 0)
        {
            Game.setGameState(new EntryState());
            return;
        }

        if (EnemyShip.INSTANCES <= 0)
            createWave();

        Resources.Sounds.MUSIC.getSource().setParameter(AL10.AL_GAIN, WAVE * 0.5f);
        Resources.Sounds.MUSIC.getSource().setParameter(AL10.AL_PITCH, Math.max(0.5f, Math.min(1.0f, WAVE * 0.05f)));
    }

    @Override
    public void render(float delta, Batcher batcher)
    {
        Graphics2D g2d = SilenceEngine.graphics.getGraphics2D();
        g2d.drawTexture(Resources.Textures.BACKGROUND, 0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        g2d.getCamera().apply();
        SCENE.render(delta);

        g2d.drawTexture(Resources.Textures.KEY_ALT, 10, CANVAS_HEIGHT - 56);
        g2d.drawTexture(Resources.Textures.KEY_ALT, CANVAS_WIDTH - 74, CANVAS_HEIGHT - 56);

        Bullet.NUM_HIT_ENEMY = Math.min(Bullet.NUM_HIT_ENEMY, Bullet.NUM_SHOT_PLAYER);

        ACCURACY = (int)(((float) Bullet.NUM_HIT_ENEMY) / Bullet.NUM_SHOT_PLAYER * 100);

        String accuracy = "ACCURACY: " + ACCURACY + "%";
        g2d.drawString(accuracy, CANVAS_WIDTH - 84 - g2d.getFont().getWidth(accuracy), CANVAS_HEIGHT - g2d.getFont().getHeight() - 20);

        String score = "SCORE: " + SCORE;
        float cx = CANVAS_WIDTH/2 - g2d.getFont().getWidth(score)/2;
        float cy = CANVAS_HEIGHT - g2d.getFont().getHeight() - 20;
        g2d.drawString(score, cx, cy);

        for (int i = 84; i < LIVES * 64 + 84; i += 64)
            g2d.drawTexture(Resources.Textures.SHIP_BLACK, i, CANVAS_HEIGHT - 52, 64, 32);
    }

    @Override
    public void onLeave()
    {
        SCENE.destroy();
    }

    private void createWave()
    {
        if ((++WAVE) % 5 == 0 && !BOSS_WAVE)
        {
            SCORE += ACCURACY * WAVE;

            SCENE.addChild(new MotherShip());
            SCENE.addChild(new NewWave());
            BOSS_WAVE = true;
        }
        else if (!BOSS_WAVE)
        {
            if (SCORE != 0)
                SCORE += ACCURACY * WAVE;

            SCENE.addChild(new NewWave());

            for (int i = 10; i < CANVAS_WIDTH - 138; i += 138)
            {
                for (int j = 10; j < CANVAS_HEIGHT / 2; j += 74)
                {
                    if (MathUtils.chance(WAVE * 10))
                        SCENE.addChild(new EnemyShip(new Vector2(i, j)));
                }
            }
        }
    }
}
