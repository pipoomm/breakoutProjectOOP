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
import org.mini2Dx.core.audio.Sound;
import org.mini2Dx.core.collision.CollisionBox;
import org.mini2Dx.core.graphics.Sprite;
import org.mini2Dx.core.graphics.Texture;

import java.io.IOException;

class Paddle {
    public static float PADDLE_ACCELERATION = 350;
    public String PADDLE_TEXTURE_IMAGE = "misc/paddle.png";
    private final CollisionBox collisionBox;
    private Sprite paddleSprite;

    public Paddle(){
        Texture paddleTexture = Mdx.graphics.newTexture(Mdx.files.internal(PADDLE_TEXTURE_IMAGE));
        paddleSprite = Mdx.graphics.newSprite(paddleTexture);
        collisionBox = new CollisionBox();
        collisionBox.setWidth(paddleSprite.getWidth());
        collisionBox.setHeight(paddleSprite.getHeight());

        returnToDefaultPosition();
    }

    public void returnToDefaultPosition(){
        collisionBox.setCenterX(BreakoutGame.gameWidth / 2);
        collisionBox.setY(BreakoutGame.gameHeight - paddleSprite.getHeight());
    }

    public void update(float delta) {
        collisionBox.preUpdate();

        if(ScoreCounter.getInstance().getScore() >= 500 && ScoreCounter.getInstance().getScore() <= 1000)
        {
            PADDLE_TEXTURE_IMAGE = "misc/paddle2.png";
            Texture paddleTexture2 = Mdx.graphics.newTexture(Mdx.files.internal(PADDLE_TEXTURE_IMAGE));
            paddleSprite = Mdx.graphics.newSprite(paddleTexture2);
            collisionBox.setWidth(paddleSprite.getWidth());
            collisionBox.setHeight(paddleSprite.getHeight());
        }

        else if(ScoreCounter.getInstance().getScore() >= 2500 && ScoreCounter.getInstance().getScore() <= 3700)
        {
            PADDLE_TEXTURE_IMAGE = "misc/paddle2.png";
            Texture paddleTexture2 = Mdx.graphics.newTexture(Mdx.files.internal(PADDLE_TEXTURE_IMAGE));
            paddleSprite = Mdx.graphics.newSprite(paddleTexture2);
            collisionBox.setWidth(paddleSprite.getWidth());
            collisionBox.setHeight(paddleSprite.getHeight());
        }

        else if(ScoreCounter.getInstance().getScore() >= 4000 && ScoreCounter.getInstance().getScore() <= 4600)
        {
            PADDLE_TEXTURE_IMAGE = "misc/paddle2.png";
            Texture paddleTexture2 = Mdx.graphics.newTexture(Mdx.files.internal(PADDLE_TEXTURE_IMAGE));
            paddleSprite = Mdx.graphics.newSprite(paddleTexture2);
            collisionBox.setWidth(paddleSprite.getWidth());
            collisionBox.setHeight(paddleSprite.getHeight());
        }

        else if(ScoreCounter.getInstance().getScore() >= 6000 && ScoreCounter.getInstance().getScore() <= 6500)
        {
            PADDLE_TEXTURE_IMAGE = "misc/paddle2.png";
            Texture paddleTexture2 = Mdx.graphics.newTexture(Mdx.files.internal(PADDLE_TEXTURE_IMAGE));
            paddleSprite = Mdx.graphics.newSprite(paddleTexture2);
            collisionBox.setWidth(paddleSprite.getWidth());
            collisionBox.setHeight(paddleSprite.getHeight());
        }

        else if(ScoreCounter.getInstance().getScore() >= 7500 && ScoreCounter.getInstance().getScore() <= 8000)
        {
            PADDLE_TEXTURE_IMAGE = "misc/paddle2.png";
            Texture paddleTexture2 = Mdx.graphics.newTexture(Mdx.files.internal(PADDLE_TEXTURE_IMAGE));
            paddleSprite = Mdx.graphics.newSprite(paddleTexture2);
            collisionBox.setWidth(paddleSprite.getWidth());
            collisionBox.setHeight(paddleSprite.getHeight());
        }

        else if(ScoreCounter.getInstance().getScore() >= 10000 && ScoreCounter.getInstance().getScore() <= 11000)
        {
            PADDLE_TEXTURE_IMAGE = "misc/paddle2.png";
            Texture paddleTexture2 = Mdx.graphics.newTexture(Mdx.files.internal(PADDLE_TEXTURE_IMAGE));
            paddleSprite = Mdx.graphics.newSprite(paddleTexture2);
            collisionBox.setWidth(paddleSprite.getWidth());
            collisionBox.setHeight(paddleSprite.getHeight());
        }

        else if(ScoreCounter.getInstance().getScore() >= 13000 && ScoreCounter.getInstance().getScore() <= 13500)
        {
            PADDLE_TEXTURE_IMAGE = "misc/paddle2.png";
            Texture paddleTexture2 = Mdx.graphics.newTexture(Mdx.files.internal(PADDLE_TEXTURE_IMAGE));
            paddleSprite = Mdx.graphics.newSprite(paddleTexture2);
            collisionBox.setWidth(paddleSprite.getWidth());
            collisionBox.setHeight(paddleSprite.getHeight());
        }

        else if(ScoreCounter.getInstance().getScore() >= 20000 && ScoreCounter.getInstance().getScore() <= 21000)
        {
            PADDLE_TEXTURE_IMAGE = "misc/paddle2.png";
            Texture paddleTexture2 = Mdx.graphics.newTexture(Mdx.files.internal(PADDLE_TEXTURE_IMAGE));
            paddleSprite = Mdx.graphics.newSprite(paddleTexture2);
            collisionBox.setWidth(paddleSprite.getWidth());
            collisionBox.setHeight(paddleSprite.getHeight());
        }

        else if(ScoreCounter.getInstance().getScore() >= 25000 && ScoreCounter.getInstance().getScore() <= 26500)
        {
            PADDLE_TEXTURE_IMAGE = "misc/paddle2.png";
            Texture paddleTexture2 = Mdx.graphics.newTexture(Mdx.files.internal(PADDLE_TEXTURE_IMAGE));
            paddleSprite = Mdx.graphics.newSprite(paddleTexture2);
            collisionBox.setWidth(paddleSprite.getWidth());
            collisionBox.setHeight(paddleSprite.getHeight());
        }

        else
        {
            PADDLE_TEXTURE_IMAGE = "misc/paddle.png";
            Texture paddleTexture2 = Mdx.graphics.newTexture(Mdx.files.internal(PADDLE_TEXTURE_IMAGE));
            paddleSprite = Mdx.graphics.newSprite(paddleTexture2);
            collisionBox.setWidth(paddleSprite.getWidth());
            collisionBox.setHeight(paddleSprite.getHeight());
        }

        InputHandler iH = InputHandler.getInstance();
        if (iH.isLeftPressed()) {
            collisionBox.setX(Math.max(collisionBox.getX() - PADDLE_ACCELERATION * delta, 0));
        }
        if (iH.isRightPressed()){
            float newX = collisionBox.getX() + PADDLE_ACCELERATION * delta;
            collisionBox.setX(newX + collisionBox.getWidth() < BreakoutGame.gameWidth ? newX : BreakoutGame.gameWidth - collisionBox.getWidth());
        }

        if (CollisionHandler.getInstance().getAliveBricks() <= 30 && CollisionHandler.getInstance().getAliveBricks() >= 25) {
            PADDLE_ACCELERATION = 180;
        }
        if (CollisionHandler.getInstance().getAliveBricks() <= 40 && CollisionHandler.getInstance().getAliveBricks() >= 30 && ScoreCounter.getInstance().getScore() >= 10000) {
            PADDLE_ACCELERATION = 160;
        }
        if (CollisionHandler.getInstance().getAliveBricks() <= 20 && CollisionHandler.getInstance().getAliveBricks() >= 10 && ScoreCounter.getInstance().getScore() >= 15000) {
            PADDLE_ACCELERATION = 150;
        }
        else{
            PADDLE_ACCELERATION = 350;
        }
    }

    public void render(Graphics g) {
        g.drawSprite(paddleSprite, collisionBox.getRenderX(), collisionBox.getRenderY());
        if ((BreakoutGame.DEBUG_MODE & BreakoutGame.DEBUG_COLLISION_DRAW_COLLISION_BOXES) != 0)
            collisionBox.draw(g);
    }

    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

}
