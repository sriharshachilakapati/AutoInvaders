package com.shc.ld34.game.states;

import com.shc.ld34.game.Resources;
import com.shc.ld34.game.entities.Bullet;
import com.shc.ld34.game.entities.EnemyShip;
import com.shc.ld34.game.entities.MotherShip;
import com.shc.ld34.game.entities.PlayerShip;
import com.shc.silenceengine.collision.broadphase.DynamicTree2D;
import com.shc.silenceengine.collision.colliders.SceneCollider2D;
import com.shc.silenceengine.core.GameState;
import com.shc.silenceengine.core.SilenceEngine;
import com.shc.silenceengine.graphics.Batcher;
import com.shc.silenceengine.graphics.Graphics2D;
import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.scene.Scene2D;
import com.shc.silenceengine.utils.MathUtils;

import static com.shc.ld34.game.AutoInvaders.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class PlayState extends GameState
{
    public static boolean BOSS_WAVE;
    public static Scene2D SCENE;

    public static int WAVE;

    private SceneCollider2D collider;

    @Override
    public void onEnter()
    {
        WAVE = 0;
        BOSS_WAVE = false;

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

        if (EnemyShip.INSTANCES <= 0)
            createWave();
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
            SCENE.addChild(new MotherShip());
            BOSS_WAVE = true;
        }
        else if (!BOSS_WAVE)
        {
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
