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
import org.mini2Dx.core.graphics.Texture;
/**
 * Live amount in each stage
 */
public class LivesHandler {
    public static final int INITIAL_LIVES_NUM = 3;
    private final static String HEART_TEXTURE_IMAGE = "misc/heart.png";
    private final static Texture HEART_TEXTURE = Mdx.graphics.newTexture(Mdx.files.internal(HEART_TEXTURE_IMAGE));
    private static LivesHandler current = new LivesHandler();
    private int lives = INITIAL_LIVES_NUM;


    public LivesHandler() {
        current = this;
    }

    /**
     * @return LivesHandler
     */
    public static LivesHandler getInstance() {
        return current;
    }

    /**
     * Get total amount of live left
     * @return live amount
     */
    public int getLives() {
        return lives;
    }

    /**
     * Check when live is 0
     * @return dead stage
     */
    public boolean isDead() {
        return lives == 0;
    }

    /**
     * When ball drop to bottom decrease live
     *Decrease live amount
     */
    public void decrease() {
        lives--;
    }

    /**
     * Renders the heart object
     * @param g The {@link Graphics} context available for rendering
     */
    public void render(Graphics g) {
        for (int i = 0; i < lives; i++) {
            g.drawTexture(HEART_TEXTURE, 4 + (HEART_TEXTURE.getWidth() / 4 + 4) * i, BreakoutGame.gameHeight - HEART_TEXTURE.getHeight() / 4f, HEART_TEXTURE.getWidth() / 4, HEART_TEXTURE.getHeight() / 4);
        }
    }
}
