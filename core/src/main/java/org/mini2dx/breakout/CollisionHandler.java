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

import org.mini2Dx.core.collision.CollisionBox;
import org.mini2Dx.core.geom.Rectangle;
/**
 * Collision happen in each object 
 */
public class CollisionHandler {
    private static final CollisionHandler current;

    private Paddle paddle;
    private Ball ball;
    private Brick[][] bricks;

    private boolean isBallTouchingPaddle = false;
    private boolean isBallTouchingAnyBrick = false;
    private Brick touchedBrick = null;
    private int aliveBricks;

    static {
        current = new CollisionHandler();
    }

    private CollisionHandler(){
    }

    /**
     * @return CollisionHandler
     */
    public static CollisionHandler getInstance() {
        return current;
    }

    /**
     * Set collision for paddle
     * @param paddle For paddle object
     */
    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    /**
     * Set collision for ball
     * @param ball For ball object
     */
    public void setBall(Ball ball){
        this.ball = ball;
    }

    /**
     * Updates the game screen
     */
    public static void update(){
        current.isBallTouchingPaddle = current.ball.getCollisionBox().intersects((Rectangle) current.paddle.getCollisionBox());

        current.touchedBrick = null;

        for (int i = 0; i < BreakoutGame.gridSizeX; i++)
            for (int j = 0; j < BreakoutGame.gridSizeY; j++)
                if (current.bricks[i][j].isAlive() && current.ball.getCollisionBox().intersects(current.bricks[i][j].getCollisionBox()))
                    current.touchedBrick = current.bricks[i][j];

        current.isBallTouchingAnyBrick = current.touchedBrick != null;

        if ((BreakoutGame.DEBUG_MODE & BreakoutGame.DEBUG_COLLISION_PRINT) != 0) {
            if (current.isBallTouchingPaddle)
                System.out.println("Touched the paddle");
            if (current.isBallTouchingAnyBrick)
                System.out.println("Touched a brick");
        }
    }

    /**
     * Set collision area for bricks
     * @param bricks collision of brick area
     */
    public void setBricks(Brick[][] bricks) {
        this.bricks = bricks;
        aliveBricks = bricks.length * bricks[0].length;
    }

    /**
     * Check ball touched paddle
     * @return TouchingPaddle or not
     */
    public boolean isBallTouchingPaddle(){
        return isBallTouchingPaddle;
    }

    /**
     * Check ball touched to brick
     * @return TouchingBrick or not
     */
    public boolean isBallTouchingAnyBrick() {
        return isBallTouchingAnyBrick;
    }

    /**
     * Check brick that ball touched
     * @return Brick that touched
     */
    public Brick getTouchedBrick() {
        return touchedBrick;
    }

    /**
     * Get amount of brick left
     * @return Amount of brick
     */
    public int getAliveBricks() {
        return aliveBricks;
    }

    /**
     *Decrease brick amount when ball touched
     */
    public void killBrick() {
        aliveBricks--;
    }
}
