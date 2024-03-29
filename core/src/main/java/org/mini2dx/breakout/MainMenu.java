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
import org.mini2Dx.core.assets.AssetManager;
import org.mini2Dx.core.exception.SerializationException;
import org.mini2Dx.core.files.FallbackFileHandleResolver;
import org.mini2Dx.core.files.FileHandleResolver;
import org.mini2Dx.core.files.InternalFileHandleResolver;
import org.mini2Dx.core.files.LocalFileHandleResolver;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.viewport.FitViewport;
import org.mini2Dx.core.graphics.viewport.Viewport;
import org.mini2Dx.core.screen.BasicGameScreen;
import org.mini2Dx.core.screen.ScreenManager;
import org.mini2Dx.core.screen.transition.NullTransition;
import org.mini2Dx.ui.UiContainer;
import org.mini2Dx.ui.UiThemeLoader;
import org.mini2Dx.ui.element.*;
import org.mini2Dx.ui.event.ActionEvent;
import org.mini2Dx.ui.listener.ActionListener;
import org.mini2Dx.ui.style.UiTheme;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
/**
 * User interface for user interact when start game to navigate to other scene
 */
public class MainMenu extends BasicGameScreen {
    public final static int ID = 1;
    public static final String UI_MAINMENU_LAYOUT_XML = "ui/mainmenu_ui.xml";
    public static final String UI_LEADERBOARD_LAYOUT_XML = "ui/leaderboard_ui.xml";

    private AssetManager assetManager;
    private UiContainer uiContainer;
    private Viewport viewport;
    private int screenToLoad = 0;

    /**
     * Initialises the game screen menu
     * @param gc The {@link GameContainer} of the game
     */
    @Override
    public void initialise(GameContainer gc) {
        FileHandleResolver fileHandleResolver = new FallbackFileHandleResolver(new InternalFileHandleResolver(),
                new LocalFileHandleResolver());
        assetManager = new AssetManager(fileHandleResolver);
        assetManager.setAssetLoader(UiTheme.class, new UiThemeLoader(fileHandleResolver));
        assetManager.load(UiTheme.DEFAULT_THEME_FILENAME, UiTheme.class);

        viewport = new FitViewport(BreakoutGame.gameWidth, BreakoutGame.gameHeight);
        uiContainer = new UiContainer(gc, assetManager);
        Container mainMenuContainer = null;
        try {
            mainMenuContainer = Mdx.xml.fromXml(Mdx.files.internal(UI_MAINMENU_LAYOUT_XML).reader(), Container.class);
        } catch (SerializationException | IOException e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(mainMenuContainer);

        TextButton newGameButton = (TextButton) mainMenuContainer.getElementById("newGameButton");
        TextButton leaderboardButton = (TextButton) mainMenuContainer.getElementById("leaderboardButton");
        TextButton quitButton = (TextButton) mainMenuContainer.getElementById("quitButton");
        mainMenuContainer.shrinkToContents(true);

        mainMenuContainer.setXY((BreakoutGame.gameWidth - mainMenuContainer.getWidth()) / 2, (BreakoutGame.gameHeight - mainMenuContainer.getHeight()) / 2);
        uiContainer.add(mainMenuContainer);

        Container temp_leaderboardContainer = null;
        try {
            temp_leaderboardContainer = Mdx.xml.fromXml(Mdx.files.internal(UI_LEADERBOARD_LAYOUT_XML).reader(), Container.class);
        } catch (SerializationException | IOException e) {
            e.printStackTrace();
        }
        final Container leaderboardContainer = Objects.requireNonNull(temp_leaderboardContainer);
        leaderboardContainer.shrinkToContents(true);
        leaderboardContainer.setXY((BreakoutGame.gameWidth - leaderboardContainer.getWidth()) / 2, (BreakoutGame.gameHeight - leaderboardContainer.getHeight()) / 2);

        TextButton mainMenuButton = (TextButton) leaderboardContainer.getElementById("mainMenuButton");
        Container scoreContainer = (Container) leaderboardContainer.getElementById("scoreContainer");

        newGameButton.addActionListener(new ActionListener() {
            /**
             * Called when an action event begins
             * @param event An {@link ActionEvent} instance containing the event details
             */
            @Override
            public void onActionBegin(ActionEvent event) {

            }

            /**
             * Called when an action event ends
             * @param event An {@link ActionEvent} instance containing the event details
             */
            @Override
            public void onActionEnd(ActionEvent event) {
                screenToLoad = BreakoutGame.ID;
            }
        });

        leaderboardButton.addActionListener(new ActionListener() {
            /**
             * Called when an action event begins
             * @param event An {@link ActionEvent} instance containing the event details
             */
            @Override
            public void onActionBegin(ActionEvent event) {

            }

            /**
             * Called when an action event ends
             * @param event An {@link ActionEvent} instance containing the event details
             */
            @Override
            public void onActionEnd(ActionEvent event) {
                scoreContainer.removeAll();
                List<Score> scores = LeaderboardHandler.getInstance().getScores();
                int i = 0;
                for (Score currentScore : scores) {
                    Div currentScoreRow = new Div();
                    currentScoreRow.setFlexLayout("flex-column:xs-12c lg-12c xl-12c");
                    currentScoreRow.setVisibility(Visibility.VISIBLE);

                    Div currentScorePositionDiv = new Div();
                    currentScorePositionDiv.setVisibility(Visibility.VISIBLE);
                    currentScorePositionDiv.setFlexLayout("flex-column:xs-1c lg-1c xl-1c");
                    Label currentScorePosition = new Label();
                    currentScorePosition.setText(Integer.toString(++i));
                    currentScorePosition.setVisibility(Visibility.VISIBLE);
                    currentScorePositionDiv.add(currentScorePosition);
                    currentScoreRow.add(currentScorePositionDiv);

                    Div currentScoreNameDiv = new Div();
                    currentScoreNameDiv.setVisibility(Visibility.VISIBLE);
                    currentScoreNameDiv.setFlexLayout("flex-column:xs-8c lg-8c xl-8c");
                    Label currentScoreName = new Label();
                    currentScoreName.setText(currentScore.name);
                    currentScoreName.setVisibility(Visibility.VISIBLE);
                    currentScoreNameDiv.add(currentScoreName);
                    currentScoreRow.add(currentScoreNameDiv);

                    Label currentScoreLabel = new Label();
                    currentScoreLabel.setText(Integer.toString(currentScore.score));
                    currentScoreLabel.setVisibility(Visibility.VISIBLE);
                    Div currentScoreDiv = new Div();
                    currentScoreDiv.setVisibility(Visibility.VISIBLE);
                    currentScoreDiv.setFlexLayout("flex-column:xs-3c lg-3c xl-3c");
                    currentScoreDiv.add(currentScoreLabel);
                    currentScoreRow.add(currentScoreDiv);

                    scoreContainer.add(currentScoreRow);
                }
                uiContainer.get(0).setVisibility(Visibility.NO_RENDER);
                uiContainer.add(leaderboardContainer);
                uiContainer.shrinkToContents(true);
            }
        });

        quitButton.addActionListener(new ActionListener() {
            /**
             * Called when an action event ends
             * @param event An {@link ActionEvent} instance containing the event details
             */
            @Override
            public void onActionBegin(ActionEvent event) {

            }

            /**
             * Called when an action event ends
             * @param event An {@link ActionEvent} instance containing the event details
             */
            @Override
            public void onActionEnd(ActionEvent event) {
                Mdx.platformUtils.exit(false);
            }
        });

        mainMenuButton.addActionListener(new ActionListener() {
            /**
             * Called when an action event ends
             * @param event An {@link ActionEvent} instance containing the event details
             */
            @Override
            public void onActionBegin(ActionEvent event) {

            }

            /**
             * Called when an action event ends
             * @param event An {@link ActionEvent} instance containing the event details
             */
            @Override
            public void onActionEnd(ActionEvent event) {
                uiContainer.remove(leaderboardContainer);
                uiContainer.get(0).setVisibility(Visibility.VISIBLE);
            }
        });

        uiContainer.setViewport(viewport);
    }

    /**
     * Updates the game screen
     * @param gc Parameter to get content object.
     * @param screenManager For show menu when stage end
     * @param delta The time in seconds since the last update
     */
    @Override
    public void update(GameContainer gc, ScreenManager screenManager, float delta) {
        if (!assetManager.update()) {
            return;
        }
        if (!UiContainer.isThemeApplied()) {
            UiContainer.setTheme(assetManager.get(UiTheme.DEFAULT_THEME_FILENAME, UiTheme.class));
        }
        uiContainer.update(delta);
        if (screenToLoad != 0) {
            screenManager.enterGameScreen(screenToLoad, new NullTransition(), new NullTransition());
            screenToLoad = 0;
        }
        Mdx.input.setInputProcessor(uiContainer);
    }

    /**
     * Renders the game screen
     * @param gc The {@link GameContainer} of the game
     * @param g The {@link Graphics} context available for rendering
     */
    @Override
    public void render(GameContainer gc, Graphics g) {
        viewport.apply(g);
        uiContainer.render(g);
    }

    /**
     * Returns the identifier of the screen
     * @return A unique identifier
     */
    @Override
    public int getId() {
        return ID;
    }
}
