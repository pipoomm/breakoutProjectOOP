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
import org.mini2Dx.core.graphics.Texture;

import java.io.IOException;

public class Background {
       // private final static int BACKGROUND_SCALE = 1;

    private final Texture backgroundTexture = Mdx.graphics.newTexture(Mdx.files.internal("misc/background.png"));
    private final Texture cheer = Mdx.graphics.newTexture(Mdx.files.internal("misc/ok.png"));
    private final Texture cheer2 = Mdx.graphics.newTexture(Mdx.files.internal("misc/ok2.png"));



    public void render(Graphics g) {
        g.drawTexture(backgroundTexture,0,0);
        if (CollisionHandler.getInstance().getAliveBricks() == 10) {
            g.drawTexture(cheer,15,400);
        }
        if (CollisionHandler.getInstance().getAliveBricks() == 9) {
            g.drawTexture(cheer2,15,400);
        }
        if (CollisionHandler.getInstance().getAliveBricks() == 8) {
            g.drawTexture(cheer,15,400);
        }
        if (CollisionHandler.getInstance().getAliveBricks() == 7) {
            g.drawTexture(cheer2,15,400);
        }
        if (CollisionHandler.getInstance().getAliveBricks() == 6) {
            g.drawTexture(cheer,15,400);
        }
        if (CollisionHandler.getInstance().getAliveBricks() == 5) {
            g.drawTexture(cheer2,15,400);
        }
        if (CollisionHandler.getInstance().getAliveBricks() == 4) {
            g.drawTexture(cheer,15,400);
        }
        if (CollisionHandler.getInstance().getAliveBricks() == 3) {
            g.drawTexture(cheer2,15,400);
        }
        if (CollisionHandler.getInstance().getAliveBricks() == 2) {
            g.drawTexture(cheer,15,400);
        }
        if (CollisionHandler.getInstance().getAliveBricks() == 1) {
            g.drawTexture(cheer2,15,400);
        }
        //for (int x = 0; x < BreakoutGame.gameWidth; x += backgroundTexture.getWidth() / BACKGROUND_SCALE)
        //    for (int y = 0; y < BreakoutGame.gameHeight; y += backgroundTexture.getHeight() / BACKGROUND_SCALE)
        //        g.drawTexture(backgroundTexture, x, y, backgroundTexture.getWidth() / BACKGROUND_SCALE, backgroundTexture.getHeight() / BACKGROUND_SCALE);
    }
}
