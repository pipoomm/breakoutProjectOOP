/*******************************************************************************
 * Copyright 2019 Viridian Software Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.mini2dx.breakout;

import org.mini2Dx.core.Graphics;
import org.mini2Dx.core.Mdx;
import org.mini2Dx.core.collision.CollisionBox;
import org.mini2Dx.core.graphics.Sprite;
import org.mini2Dx.core.graphics.Texture;
/**
 * Brick object in this game
 */
public class Brick {

    public enum Color {
        BLUE("bricks/blue.png"),
        GREEN("bricks/green.png"),
        GREY("bricks/grey.png"),
        PURPLE("bricks/purple.png"),
        RED("bricks/red.png"),
        YELLOW("bricks/yellow.png");

        private final String color;

        /**
         * Set color for each brick
         */
        Color(final String color) {
            this.color = color;
        }

        /**
         * Returns the name of this enum constant, as contained in the
         * declaration.  This method may be overridden, though it typically
         * isn't necessary or desirable.  An enum type should override this
         * method when a more "programmer-friendly" string form exists.
         *
         * @return the name of this enum constant
         */
        @Override
        public String toString() {
            return color;
        }
    }

    private final CollisionBox collisionBox;
    private final Sprite boxSprite;
    private boolean isAlive = true;
    public static final float height = 32f, width = 64f;

    /**
     * Build brick object into the game
     */
    Brick(Color color, float xPosition, float yPosition) {
        Texture boxTexture = Mdx.graphics.newTexture(Mdx.files.internal(color.toString()));
        boxSprite = Mdx.graphics.newSprite(boxTexture);
        collisionBox = new CollisionBox();
        collisionBox.setHeight(boxSprite.getHeight());
        collisionBox.setWidth(boxSprite.getWidth());
        collisionBox.setX(xPosition);
        collisionBox.setY(yPosition);
    }

    /**
     * Updates the game screen
     */
    void update() {
        collisionBox.preUpdate();
        if (CollisionHandler.getInstance().getTouchedBrick() == this) {
            setAlive(false);
            CollisionHandler.getInstance().killBrick();
        }
    }

    /**
     * Renders the brick
     * @param g The {@link Graphics} context available for rendering
     */
    void render(Graphics g) {
        if (isAlive) {
            g.drawSprite(boxSprite, collisionBox.getRenderX(), collisionBox.getRenderY());
            if ((BreakoutGame.DEBUG_MODE & BreakoutGame.DEBUG_COLLISION_DRAW_COLLISION_BOXES) != 0) {
                collisionBox.draw(g);
            }
        }
    }


    /**
     * Check live of player in that present stage
     * @return A alive or not
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Set live that get from game
     * @param alive Parameter have alive or not
     */
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    /**
     * Returns the area that make collision fir object in the game
     * @return A collision area
     */
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }
}
