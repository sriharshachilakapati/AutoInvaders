package com.shc.ld34.game.entities;

import com.shc.ld34.game.Resources;
import com.shc.ld34.game.states.PlayState;
import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.math.geom2d.Rectangle;
import com.shc.silenceengine.scene.entity.Entity2D;
import com.shc.silenceengine.utils.MathUtils;

import static com.shc.ld34.game.AutoInvaders.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class MotherShip extends Entity2D
{
    private boolean dead            = false;
    private boolean arrivedLocation = false;
    private boolean movingLeft;

    private int wave;

    public MotherShip()
    {
        super(Resources.Sprites.MOTHER_SHIP, new Rectangle(416, 250));

        setRotation(180); // 180° rotation
        setCenter(new Vector2(CANVAS_WIDTH / 2, -300));

        movingLeft = MathUtils.chance(50);

        spawnEnemies();
    }

    @Override
    public void update(float delta)
    {
        if (!arrivedLocation)
            arrivedLocation = moveCenterTo(CANVAS_WIDTH / 2, 200, 4);
        else
        {
            if (dead && moveCenterTo(getCenter().x, -450, 4))
            {
                PlayState.BOSS_WAVE = false;
                PlayState.WAVE++;
                destroy();
            }

            if (getX() <= 10 || getX() + getWidth() >= CANVAS_WIDTH - 10)
                movingLeft = !movingLeft;

            moveCenterTo(getCenter().x + (movingLeft ? -4 : 4), getCenter().y, 4);

            if (EnemyShip.INSTANCES <= 0)
            {
                if (wave >= 5)
                    dead = true;
                else
                    spawnEnemies();
            }
        }
    }

    @Override
    public void collision(Entity2D other)
    {
        if (other instanceof Bullet)
        {
            if (((Bullet) other).getShotBy() == Bullet.ShotBy.PLAYER)
                other.destroy();
        }
    }

    private void spawnEnemies()
    {
        for (int i = 10; i < CANVAS_WIDTH - 138; i += 138)
        {
            for (int j = 350; j < CANVAS_HEIGHT / 2; j += 74)
                if (MathUtils.chance(wave * 10))
                {
                    PlayState.SCENE.addChild(new EnemyShip(new Vector2(i, j), getCenter()));
                }
        }

        wave++;
    }
}
