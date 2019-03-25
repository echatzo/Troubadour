package com.troubadour.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.troubadour.game.Troubadour;
import com.troubadour.game.sprites.Animation;
import com.troubadour.game.sprites.Background;
import com.troubadour.game.sprites.Player;
import com.troubadour.game.sprites.Wall;


public class PlayState extends State {


    private static final int WALL_SPACING = 100;
    private static final int WALL_COUNT = 6;

    private Stage stage;
    private Skin skin;
    private TextButton pauseButton;
    int row_height = Gdx.graphics.getHeight() / 12;
    int col_width = Gdx.graphics.getWidth() / 12;

    private Player player;
    private Array<Background> backgrounds;
    private Texture enemy;
    private Animation enemyAnimation;

    private float score;
    private String yourScoreName;
    BitmapFont yourBitmapFontName;

    private Sound oof;
    private Sound death;



    private Array<Wall> walls;

    public PlayState(GameStateManager gsm){
        super(gsm);

        player = new Player((Troubadour.WIDTH /4)-(Player.PLAYER_WIDTH/2), 30);
        cam.setToOrtho(false, Troubadour.WIDTH /2, Troubadour.HEIGHT /2);
        backgrounds = new Array<Background>();
        for(int i=0; i<=1; i++){
            backgrounds.add(new Background(0,400*i, "ground.png"));
        }
        enemy = new Texture("enemyAnimation.png");
        enemyAnimation = new Animation(new TextureRegion(enemy), 3, 2f);
        walls = new Array<Wall>();
        oof = Gdx.audio.newSound(Gdx.files.internal("oof.mp3"));
        death = Gdx.audio.newSound(Gdx.files.internal("death.mp3"));

        for(int i = 1; i <= WALL_COUNT; i ++){
            walls.add(new Wall(cam.viewportHeight+i*(WALL_SPACING + Wall.WALL_THICK)));
        }
        score = 0;
        yourScoreName = "score: 0";
        yourBitmapFontName = new BitmapFont();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        //font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("button/star-soldier/skin/star-soldier-ui.json"));

        pauseButton = new TextButton("Pause", skin);
        pauseButton.setSize(col_width*3,row_height);
        pauseButton.setPosition(Gdx.graphics.getWidth() - pauseButton.getWidth(),Gdx.graphics.getHeight()-80-pauseButton.getHeight());
        //world1.setTransform(true);
        //world1.scaleBy(2f);
        //pauseButton.getLabel().setFontScale(col_width/40,row_height/40);
        pauseButton.setChecked(false);
        stage.addActor(pauseButton);

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()){
            player.move();
        }

        if(pauseButton.isPressed()){
            gsm.push(new PauseState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        player.update(dt);
        for (Background background : backgrounds){
            if (background.getPos().y<cam.position.y-600){
                background.reposition();
            }
        }
        score+=dt; //increments the score
        yourScoreName = "score: " + (int) score;

        enemyAnimation.update(dt);
        cam.position.y= player.getPosition().y + 150;

        for(int i = 0; i < walls.size; i++){
            Wall wall = walls.get(i);
            if(cam.position.y-(cam.viewportHeight/2) > wall.getPosRightWall().y + wall.getRightWall().getWidth()){
                wall.reposition(wall.getPosRightWall().y + ((Wall.WALL_THICK + WALL_SPACING)*WALL_COUNT));
            }
            player.incLifeTimer(dt);
            if(player.getLifeTimer()>5f) { //verifies whether the player is still invincible
                player.setTexture(1);//change the player texture back to normal
                if (wall.collides(player.getBounds())){ //if the player hitBox touches the wall hitBox, the player is hit
                    player.hurt();

                    //son collision
                    if (player.getLifeCount() > 0) {
                        oof.play(2f);
                    }


                    player.setTexture(2);
                    Gdx.input.vibrate(500);

                    player.resetLifeTimer();
                    player.lifeAnimation.update(dt);
                    if (player.getLifeCount() <= 0) {
                        death.play(0.2f);
                        try
                        {
                            Thread.sleep(1000);
                        }
                        catch(InterruptedException ex)
                        {
                            Thread.currentThread().interrupt();
                        }
                        gsm.set(new GameOverState(gsm, (int)score));//if the player have no more lives, change the playState to a gameOverState
                    }
                }
            }
        }
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        //never forget to update position relatively to the camera as the world is scrolling
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        for(Background background : backgrounds){
            sb.draw(background.getTexture(), 0, background.getPos().y, 240, 400);
        }
        sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y, Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        for (Wall wall : walls) {
            sb.draw(wall.getLeftWall(), wall.getPosLeftWall().x, wall.getPosLeftWall().y, Wall.WALL_LENGTH, Wall.WALL_THICK);
            sb.draw(wall.getRightWall(), wall.getPosRightWall().x, wall.getPosRightWall().y, Wall.WALL_LENGTH, Wall.WALL_THICK);
        }
        sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y, Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        sb.draw(enemyAnimation.getFrame(), 0, cam.position.y + (cam.viewportHeight/2)-80, cam.viewportWidth, 80);

        for (int i=1; i<=player.getLifeCount(); i++) {
            sb.draw(player.lifeAnimation.getFrame(), cam.position.x + cam.viewportWidth - 150, cam.position.y + cam.viewportHeight - (205+20*i));
        }
        yourBitmapFontName.setColor(1.0f, 1.0f, 0f, 1.0f);//score display (temporary)
        yourBitmapFontName.draw(sb, yourScoreName, 15, (int) cam.position.y + cam.viewportHeight - (290));

        sb.end();


        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {

        for(Background background : backgrounds){
            background.dispose();
        }
        player.dispose();
        oof.dispose();
        death.dispose();
        for(Wall wall : walls){
            wall.dispose();
        }
        System.out.println("Play State Disposed");
    }
}
