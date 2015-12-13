package com.shc.ld34.game.entities;

import com.shc.ld34.game.Resources;
import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.math.geom2d.Rectangle;
import com.shc.silenceengine.scene.entity.Entity2D;

import static com.shc.ld34.game.AutoInvaders.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class NewWave extends Entity2D
{
    public NewWave()
    {
        super(Resources.Sprites.NEW_WAVE, new Rectangle(658, 151));
        setCenter(new Vector2(CANVAS_WIDTH / 2, -200));

        getVelocity().y = 6;
    }

    @Override
    public void update(float delta)
    {
        if (getY() > CANVAS_HEIGHT + 200)
            destroy();
    }
}
