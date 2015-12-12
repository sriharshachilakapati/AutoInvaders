package com.shc.ld34.game.entities;

import com.shc.ld34.game.Resources;
import com.shc.ld34.game.states.PlayState;
import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.math.geom2d.Rectangle;
import com.shc.silenceengine.scene.entity.Entity2D;
import com.shc.silenceengine.utils.GameTimer;
import com.shc.silenceengine.utils.MathUtils;
import com.shc.silenceengine.utils.TimeUtils;

import static com.shc.ld34.game.AutoInvaders.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class EnemyShip extends Entity2D
{
    public static int INSTANCES = 0;

    private Vector2 originalPosition;
    private boolean reachedOriginalPos;

    public EnemyShip(Vector2 position, Vector2 spawnPosition)
    {
        super(MathUtils.chance(90) ? Resources.Sprites.SHIP_WHITE
                                   : Resources.Sprites.SHIP_GOLD, new Rectangle(128, 64));

        setRotation(180); // 180° rotation

        setPosition(spawnPosition);

        originalPosition = position;
        reachedOriginalPos = false;

        GameTimer shootTimer = new GameTimer(MathUtils.random_range(3, 10), TimeUtils.Unit.SECONDS);
        shootTimer.setCallback(() ->
        {
            if (isDestroyed())
                return;

            if (reachedOriginalPos)
            {
                Vector2 pos = new Vector2(getCenter().x, getY());
                PlayState.SCENE.addChild(new Bullet(pos, Bullet.ShotBy.ENEMY));
            }

            shootTimer.start();
        });
        shootTimer.start();

        INSTANCES++;
    }

    public EnemyShip(Vector2 position)
    {
        this(position, new Vector2(CANVAS_WIDTH / 2 - 64, -64));
    }

    @Override
    public void update(float delta)
    {
        if (!reachedOriginalPos)
            reachedOriginalPos = moveTo(originalPosition, 4);
    }

    @Override
    public void collision(Entity2D other)
    {
        if (other instanceof Bullet)
        {
            if (((Bullet) other).getShotBy() == Bullet.ShotBy.PLAYER)
            {
                destroy();
                other.destroy();
            }
        }
    }

    @Override
    public void destroy()
    {
        super.destroy();
        INSTANCES--;
    }
}
