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

import org.mini2Dx.core.Mdx;
import org.mini2Dx.core.exception.PlayerDataException;
import org.mini2Dx.core.serialization.annotation.Field;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
/**
 * Show and added username to leaderboard
 */
public class LeaderboardHandler {

    public final static int BEST_SCORES_TO_SAVE = 10;
    private final static String serializedFileName = "scoreboard.json";
    private static final LeaderboardHandler current;

    @Field
    private final LinkedList<Score> scores = new LinkedList<>();

    static {
        LeaderboardHandler temp_current; // fixes 'variable current might already have been assigned'
        try {
            temp_current = Mdx.playerData.readJson(LeaderboardHandler.class, serializedFileName);
        } catch (Exception e) {
            System.out.println("WARNING: Couldn't read leaderboard.json, creating an empty one");
            temp_current = new LeaderboardHandler();
            temp_current.writeToJson();
        }
        current = temp_current;
    }

    /**
     * @return LeaderboardHandler
     */
    public static LeaderboardHandler getInstance() {
        return current;
    }

    /**
     * Compare score to save in leaderboard
     * @param score Score from user can play
     * @return score will  be saved or not
     */
    public boolean willBeInLeaderboard(int score) {
        return scores.size() < BEST_SCORES_TO_SAVE || score > scores.getLast().score;
    }

    /**
     * Add score to leaderboard
     * @param newScore The {@link Score} of the game
     */
    public void addScore(Score newScore) {
        scores.add(newScore);

        scores.sort(Comparator.reverseOrder());

        while (scores.size() > BEST_SCORES_TO_SAVE) {
            scores.removeLast();
        }

        writeToJson();
    }

    /**
     * Write the name of user to json file
     */
    private void writeToJson() {
        try {
            Mdx.playerData.writeJson(this, serializedFileName);
        } catch (PlayerDataException e) {
            System.out.println("ERROR: Couldn't save leaderboard");
            e.printStackTrace();
        }
    }

    /**
     * Get score from user
     * @return scores from user who played
     */
    public List<Score> getScores() {
        return scores;
    }
}

