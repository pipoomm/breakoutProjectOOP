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

import org.mini2Dx.core.serialization.annotation.Field;
/**
 * Score from user played in the game
 */
public class Score implements Comparable<Score> {
    @Field
    public String name;
    @Field
    public int score;

    //Constructor needed for mini2Dx serialization
    @SuppressWarnings("unused")
    public Score() {

    }

    /**
     * @param name Set name of who played
     * @param score Set score can played
     */
    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * @param s Score for present player
     * @return Score compared from 2 value
     */
    @Override
    public int compareTo(Score s) {
        return Integer.compare(this.score, s.score);
    }
}
