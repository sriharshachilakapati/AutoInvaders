package com.shc.ld34.game.entities;

import com.shc.ld34.game.Resources;
import com.shc.ld34.game.states.PlayState;
import com.shc.silenceengine.graphics.SpriteBatch;
import com.shc.silenceengine.input.Keyboard;
import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.math.geom2d.Rectangle;
import com.shc.silenceengine.scene.entity.Entity2D;
import com.shc.silenceengine.utils.MathUtils;

import static com.shc.ld34.game.AutoInvaders.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class PlayerShip extends Entity2D
{
    private boolean movingLeft;

    public PlayerShip()
    {
        super(Resources.Sprites.SHIP_BLACK, new Rectangle(128, 64));
        setCenter(new Vector2(CANVAS_WIDTH / 2 - 32, CANVAS_HEIGHT + 64));

        movingLeft = MathUtils.chance(50);  // 50% chance of moving left or right
    }

    @Override
    public void update(float delta)
    {
        if (Keyboard.isClicked(Keyboard.KEY_LEFT_ALT) && !Keyboard.isPressed(Keyboard.KEY_RIGHT_ALT))
        {
            Vector2 position = new Vector2(getCenter().x, getY());
            PlayState.SCENE.addChild(new Bullet(position, Bullet.ShotBy.PLAYER));
        }

        if (getX() <= 10 || getX() + getWidth() >= CANVAS_WIDTH - 10)
            movingLeft = !movingLeft;

        moveCenterTo(getX() + 64 + ((getCenter().y == CANVAS_HEIGHT - 128) ? (movingLeft ? -4 : 4)
                                                                           : 0
        ), CANVAS_HEIGHT - 128, 4);
    }

    @Override
    public void collision(Entity2D other)
    {
        if (other instanceof Bullet)
        {
            if (!Keyboard.isPressed(Keyboard.KEY_RIGHT_ALT))
            {
                if (((Bullet) other).getShotBy() == Bullet.ShotBy.ENEMY)
                {
                    setCenter(new Vector2(CANVAS_WIDTH / 2 - 32, CANVAS_HEIGHT + 64));
                    movingLeft = MathUtils.chance(50);  // 50% chance of moving left or right
                }
            }

            if (((Bullet) other).getShotBy() != Bullet.ShotBy.PLAYER)
                other.destroy();
        }
    }

    @Override
    public void render(float delta, SpriteBatch batch)
    {
        super.render(delta, batch);

        if (Keyboard.isPressed(Keyboard.KEY_RIGHT_ALT))
        {
            Vector2 temp = Vector2.REUSABLE_STACK.pop();
            temp.set(getPosition()).y -= 10;

            batch.addSprite(Resources.Sprites.SHIELD, temp);
            Vector2.REUSABLE_STACK.push(temp);
        }
    }
}
