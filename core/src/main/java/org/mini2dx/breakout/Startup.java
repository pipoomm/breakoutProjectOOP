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

import org.mini2Dx.core.game.ScreenBasedGame;
/**
 * Start screen for user interact
 */
public class Startup extends ScreenBasedGame {
    public static final String GAME_IDENTIFIER = "org.mini2dx.breakout";

    /**
     * Initialise the game make UI user can select scene and prepare the game
     */
    @Override
    public void initialise() {
        addScreen(new MainMenu());
        addScreen(new BreakoutGame());
    }


    @Override
    public int getInitialScreenId() {
        return MainMenu.ID;
    }
}
