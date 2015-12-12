package com.shc.ld34.game.entities;

import com.shc.ld34.game.Resources;
import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.math.geom2d.Rectangle;
import com.shc.silenceengine.scene.entity.Entity2D;

import static com.shc.ld34.game.AutoInvaders.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class Bullet extends Entity2D
{
    private ShotBy shotBy;

    public Bullet(Vector2 center, ShotBy shotBy)
    {
        super(Resources.Sprites.BULLET, new Rectangle(5, 10));
        setCenter(center);

        this.shotBy = shotBy;

        getVelocity().y = (shotBy == ShotBy.PLAYER) ? -4 : 4;
    }

    @Override
    public void update(float delta)
    {
        if (getY() < -10 || getY() > CANVAS_HEIGHT)
            destroy();
    }

    public ShotBy getShotBy()
    {
        return shotBy;
    }

    public enum ShotBy
    {
        PLAYER,
        ENEMY
    }
}
