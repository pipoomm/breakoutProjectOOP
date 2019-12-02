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
import org.mini2Dx.core.assets.AssetManager;
import org.mini2Dx.core.audio.Sound;
import org.mini2Dx.core.files.FallbackFileHandleResolver;
import org.mini2Dx.core.files.FileHandleResolver;
import org.mini2Dx.core.files.InternalFileHandleResolver;
import org.mini2Dx.core.font.FontGlyphLayout;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.Graphics;
import org.mini2Dx.core.graphics.viewport.FitViewport;
import org.mini2Dx.core.graphics.viewport.Viewport;
import org.mini2Dx.core.screen.BasicGameScreen;
import org.mini2Dx.core.screen.ScreenManager;
import org.mini2Dx.core.screen.transition.NullTransition;
import org.mini2Dx.core.exception.SerializationException;
import org.mini2Dx.ui.UiContainer;
import org.mini2Dx.ui.UiThemeLoader;
import org.mini2Dx.ui.element.Button;
import org.mini2Dx.ui.element.Container;
import org.mini2Dx.ui.element.TextBox;
import org.mini2Dx.ui.event.ActionEvent;
import org.mini2Dx.ui.listener.ActionListener;
import org.mini2Dx.ui.style.UiTheme;

import java.io.IOException;
import java.util.Objects;
/**
 * <h1>Breakout Game</h1>
 * Breakout game implements an application from
 * mini2DX engine.
 * <p>
 * In this game we added more effect and function for
 * user friendly and make game more interesting.
 *
 *
 * @author  610615011, 610615016, 610615020
 * @version 1.0
 * @since   2019-12-04
 */
public class BreakoutGame extends BasicGameScreen {
    public static final int ID = 2;
    public int temp = 0;
    public int soundItem = 0;
    public int extTemp = 0;


    public static final int DEBUG_INPUT = 1, DEBUG_COLLISION_DRAW_COLLISION_BOXES = 2, DEBUG_COLLISION_PRINT = 4, DEBUG_BALL_SPEEDUP = 8;
    public static final int DEBUG_MODE = 0;

    public static final int gridSizeX = 10, gridSizeY = 6;
    public static final float gameWidth = gridSizeX * Brick.width, gameHeight = gridSizeY * Brick.height * 3;

    private static final String GAME_OVER_STRING = "GAME OVER!";
    private static final String UI_ASK_NAME_LAYOUT_XML = "ui/askname_ui.xml";

    private static Sound backgroundOGG;
    private final static float backgroundOGGVolume = 0.05f;

    private static Sound winOGG;
    private final static float winOGGVolume = 0.5f;

    private static Sound loseOGG;
    private final static float loseOGGVolume = 0.5f;

    private static Sound dieOGG;
    private final static float dieOGGVolume = 0.5f;

    private static Sound itemOGG;
    private final static float itemOGGVolume = 0.5f;

    private static Sound bossOGG;
    private final static float bossOGGVolume = 0.5f;

    static {
        try {
            backgroundOGG = Mdx.audio.newSound(Mdx.files.internal("audio/03ChibiNinja.ogg"));
            winOGG = Mdx.audio.newSound(Mdx.files.internal("audio/8bit-ME_Victory01.ogg"));
            loseOGG = Mdx.audio.newSound(Mdx.files.internal("audio/8bit-GAG_loop.ogg"));
            dieOGG = Mdx.audio.newSound(Mdx.files.internal("audio/cancel-01.wav"));
            itemOGG = Mdx.audio.newSound(Mdx.files.internal("audio/item-01.wav"));
            bossOGG = Mdx.audio.newSound(Mdx.files.internal("audio/8bit-act08_boss01.mp3"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Brick.Color[] brickColors = {Brick.Color.RED, Brick.Color.PURPLE, Brick.Color.BLUE, Brick.Color.GREEN, Brick.Color.YELLOW, Brick.Color.GREY};
    private GameState gameState;

    private Viewport viewport;
    private Background background;
    private Paddle paddle;
    private Ball ball;
    private final Brick[][] bricks = new Brick[gridSizeX][gridSizeY];
    private ScoreCounter score;
    private LivesHandler lives;
    private UiContainer uiContainer;


    /**
     * This method is used to set initial game object for start scene.
     * @param gameContainer Parameter to get content object.
     */
    @Override
    public void initialise(GameContainer gameContainer) {
        //noinspection ConstantConditions
        assert gridSizeY > brickColors.length; //there should be at least a color for every row of bricks

        viewport = new FitViewport(gameWidth, gameHeight);
        background = new Background();
        initialiseGame();
        //Create fallback file resolver so we can use the default mini2Dx-ui theme
        FileHandleResolver fileHandleResolver = new FallbackFileHandleResolver(new InternalFileHandleResolver());

        //Create asset manager for loading resources
        AssetManager assetManager = new AssetManager(fileHandleResolver);

        //Add mini2Dx-ui theme loader
        assetManager.setAssetLoader(UiTheme.class, new UiThemeLoader(fileHandleResolver));

        //Load default theme
        assetManager.load(UiTheme.DEFAULT_THEME_FILENAME, UiTheme.class);

        uiContainer = new UiContainer(gameContainer, assetManager);

    }

    private final FontGlyphLayout glyphLayout = Mdx.fonts.defaultFont().newGlyphLayout();

    /**
     * Build the initial in each object component in this game
     *
     */
    public void initialiseGame() {
        backgroundOGG.loop(backgroundOGGVolume);
        gameState = GameState.RUNNING;
        paddle = new Paddle();
        ball = new Ball();
        initialiseBricks();
        CollisionHandler.getInstance().setPaddle(paddle);
        CollisionHandler.getInstance().setBall(ball);
        score = new ScoreCounter();
        lives = new LivesHandler();
    }

    /**
     * Updates the game screen
     * @param gameContainer Parameter to get content object.
     * @param screenManager For show menu when stage end
     * @param delta The time in seconds since the last update
     */
    @Override
    public void update(GameContainer gameContainer, ScreenManager screenManager, float delta) {

        InputHandler.update();
        switch (gameState) {
            case RUNNING:

                if (InputHandler.getInstance().isQuitPressed()) {
                    gameState = GameState.EXITING;
                } else if (InputHandler.getInstance().isRestartPressed()) {
                    gameState = GameState.RESTARTED;
                }
                if (CollisionHandler.getInstance().getAliveBricks() == 0) {
                    initialiseBricks();
                    ball.returnToDefaultPosition();
                }
                if (CollisionHandler.getInstance().getAliveBricks() == 30 && extTemp ==0) {
                    bossOGG.play(bossOGGVolume);
                    backgroundOGG.stop();
                    extTemp =1;
                }
                if (CollisionHandler.getInstance().getAliveBricks() == 24 && extTemp ==1) {
                    bossOGG.stop();
                    backgroundOGG.loop(backgroundOGGVolume);
                    extTemp =0;
                }
                if (lives.isDead()) {
                    backgroundOGG.stop();
                    loseOGG.play(loseOGGVolume);
                    gameState = GameState.ENDING_GAME;
                } else {

                    paddle.update(delta);
                    CollisionHandler.update();
                    ball.update(delta);
                    for (int i = 0; i < gridSizeX; i++)
                        for (int j = 0; j < gridSizeY; j++)
                            bricks[i][j].update();

                    score.update();
                    if(ScoreCounter.getInstance().getScore() >= 500 && soundItem == 0)
                    {
                        System.out.println("Played");
                        itemOGG.play(itemOGGVolume);
                        soundItem = 1;
                    }
                    else if(ScoreCounter.getInstance().getScore() >= 2500 && soundItem == 1)
                    {
                        System.out.println("Played");
                        itemOGG.play(itemOGGVolume);
                        soundItem =2;
                    }
                    else if(ScoreCounter.getInstance().getScore() >= 4000 && soundItem == 2)
                    {
                        System.out.println("Played");
                        itemOGG.play(itemOGGVolume);
                        soundItem =3;
                    }
                    else if(ScoreCounter.getInstance().getScore() >= 4800 && soundItem == 3)
                    {
                        System.out.println("Played");
                        //itemOGG.play(itemOGGVolume);
                        soundItem = 4;
                    }
                    else if(ScoreCounter.getInstance().getScore() >= 6000 && soundItem == 4)
                    {
                        System.out.println("Played");
                        itemOGG.play(itemOGGVolume);
                        soundItem = 5;
                    }
                    else if(ScoreCounter.getInstance().getScore() >= 7500 && soundItem == 5)
                    {
                        System.out.println("Played");
                        itemOGG.play(itemOGGVolume);
                        soundItem = 6;
                    }
                    else if(ScoreCounter.getInstance().getScore() >= 8600 && soundItem == 6)
                    {
                       // System.out.println("Played");
                       // itemOGG.play(itemOGGVolume);
                        soundItem = 7;
                    }
                    else if(ScoreCounter.getInstance().getScore() >= 10000 && soundItem == 7)
                    {
                        System.out.println("Played");
                        itemOGG.play(itemOGGVolume);
                        soundItem = 8;
                    }
                    else if(ScoreCounter.getInstance().getScore() >= 13000 && soundItem == 8)
                    {
                        System.out.println("Played");
                        itemOGG.play(itemOGGVolume);
                        soundItem = 9;
                    }
                    else if(ScoreCounter.getInstance().getScore() >= 14000 && soundItem == 9)
                    {
                        //System.out.println("Played");
                        //itemOGG.play(itemOGGVolume);
                        soundItem = 10;
                    }
                    else if(ScoreCounter.getInstance().getScore() >= 20000 && soundItem == 10)
                    {
                        System.out.println("Played");
                        itemOGG.play(itemOGGVolume);
                        soundItem = 11;
                    }

                    else if(ScoreCounter.getInstance().getScore() >= 25000 && soundItem == 11)
                    {
                        System.out.println("Played");
                        itemOGG.play(itemOGGVolume);
                        soundItem = 12;
                    }


                    if (ball.getCollisionBox().getY() > gameHeight) {
                        lives.decrease();
                        if (!lives.isDead())
                            dieOGG.play(dieOGGVolume);
                            ball.returnToDefaultPosition();
                    }
                }
                break;
            case ENDING_GAME:
                if (LeaderboardHandler.getInstance().willBeInLeaderboard(ScoreCounter.getInstance().getScore())) {
                    gameState = GameState.ASK_NAME;
                } else {
                    gameState = GameState.WAITING_FOR_ANY_KEY;
                }
                break;
            case ASK_NAME:
                Mdx.input.setInputProcessor(uiContainer);

                Container temp_askNameContainer = null;
                try {
                    temp_askNameContainer = Mdx.xml.fromXml(Mdx.files.internal(UI_ASK_NAME_LAYOUT_XML).reader(), Container.class);
                } catch (SerializationException | IOException e) {
                    e.printStackTrace();
                }
                final Container askNameContainer = Objects.requireNonNull(temp_askNameContainer);
                askNameContainer.setXY((BreakoutGame.gameWidth - askNameContainer.getWidth()) / 2, (BreakoutGame.gameHeight - askNameContainer.getHeight()) / 2);
                final Button confirmButton = (Button) askNameContainer.getElementById("confirmButton");
                final TextBox playerNameText = (TextBox) askNameContainer.getElementById("playerNameText");
                confirmButton.addActionListener(new ActionListener() {
                    /**
                     * Called when an action event begins (e.g. mouse down)
                     * @param event An {@link ActionEvent} instance containing the event details
                     */
                    @Override
                    public void onActionBegin(ActionEvent event) {

                    }

                    /**
                     * Called when an action event ends (e.g. mouse up)
                     * @param event An {@link ActionEvent} instance containing the event details
                     */
                    @Override
                    public void onActionEnd(ActionEvent event) {
                        LeaderboardHandler.getInstance().addScore(new Score(playerNameText.getValue(), ScoreCounter.getInstance().getScore()));
                        confirmButton.setEnabled(false);
                        gameState = GameState.WAITING_FOR_ANY_KEY;
                    }
                });
                uiContainer.add(askNameContainer);
                gameState = GameState.WAITING_FOR_NAME;
                break;
            case WAITING_FOR_NAME:
                uiContainer.update(delta);
                break;
            case WAITING_FOR_ANY_KEY:
                if (InputHandler.getInstance().isAnyKeyPressed()) {
                    gameState = GameState.EXITING;
                }
                break;
            case EXITING:
                screenManager.enterGameScreen(MainMenu.ID, new NullTransition(),
                        new NullTransition());
                gameState = GameState.RESTARTED;
                break;
            case RESTARTED:
                initialiseGame();
                break;
        }
    }

    /**
     * Create brick object when start new game or level
     */
    private void initialiseBricks() {
        for (int j = 0; j < gridSizeY; j++)
        {
            for (int i = 0; i < gridSizeX; i++)
            {
                bricks[i][j] = new Brick(brickColors[j], i * Brick.width, j * Brick.height);
            }
        }
        temp++;


        CollisionHandler.getInstance().setBricks(bricks);
        if(temp != 1)
        {
        winOGG.play(winOGGVolume);
        }
    }

    /**
     * Renders the game screen
     * @param gameContainer The {@link GameContainer} of the game
     * @param g The {@link Graphics} context available for rendering
     */
    @Override
    public void render(GameContainer gameContainer, Graphics g) {
        viewport.apply(g);
        background.render(g);
        if (gameState == GameState.WAITING_FOR_NAME) {
            uiContainer.render(g);
        } else {
            if (lives.isDead()) {
                drawCenterAlignedString(g, GAME_OVER_STRING);
            } else {
                paddle.render(g);
                ball.render(g);
                for (int i = 0; i < gridSizeX; i++)
                    for (int j = 0; j < gridSizeY; j++)
                        bricks[i][j].render(g);
                lives.render(g);
            }
        }
        score.render(g);
    }

    protected enum GameState {
        RUNNING,
        ENDING_GAME,
        ASK_NAME,
        WAITING_FOR_NAME,
        WAITING_FOR_ANY_KEY,
        EXITING,
        RESTARTED
    }

    /**
     * Returns the identifier of the screen
     * @return A unique identifier
     */
    @Override
    public int getId() {
        return ID;
    }

    /**
     * Create window screen breakout game
     */
    public void drawCenterAlignedString(Graphics g, String s) {
        glyphLayout.setText(s);
        int renderX = Math.round((gameWidth / 2f) - (glyphLayout.getWidth() / 2f));
        int renderY = Math.round((gameHeight / 2f) - (glyphLayout.getHeight() / 2f));
        g.drawString(s, renderX, renderY);
    }
}