package com.troubadour.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.troubadour.game.Troubadour;
import com.troubadour.game.sprites.Animation;
import com.troubadour.game.sprites.Background;
import com.troubadour.game.sprites.Bullet;
import com.troubadour.game.sprites.Ghost;
import com.troubadour.game.sprites.Player;

import java.util.Random;


public class PlayStateWorld3 extends State {




    private static final int ENEMY_SPACING = 50;
    private static final int ENEMY_COUNT = 150;

    private Stage stage;
    private Skin skin;
    private TextButton pauseButton;
    int row_height = Gdx.graphics.getHeight() / 12;
    int col_width = Gdx.graphics.getWidth() / 12;

    private Player player;
    private Array<Background> backgrounds;
    private Texture cadre;
    private Texture enemy;
    private Animation enemyAnimation;

    private int score;
    private float time;
    private float nextBullet;
    private float nextWave;
    private int totalWaves;
    private String yourScoreName;
    BitmapFont yourBitmapFontName;


    private Array<Ghost> enemies;
    private Array<Bullet> projectiles;

    public PlayStateWorld3(final GameStateManager gsm){
        super(gsm);

        player = new Player((Troubadour.WIDTH /4)-(Player.PLAYER_WIDTH/2), 100);
        cam.setToOrtho(false, Troubadour.WIDTH /2, Troubadour.HEIGHT /2);
        backgrounds = new Array<Background>();
        for(int i=0; i<=1; i++){
            backgrounds.add(new Background(0,400*i, "background2.png"));
        }
        cadre = new Texture("cadrebleu.png");
        enemy = new Texture("ghostboss.png");
        enemyAnimation = new Animation(new TextureRegion(enemy), 3, 0.8f);
        enemies = new Array<Ghost>();

        projectiles=new Array<Bullet>();
        score = 0;
        yourScoreName = "score: 0";
        yourBitmapFontName = new BitmapFont();
        time = 0;
        nextWave=1.5f;
        nextBullet=1;
        totalWaves=0;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        //font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("button/star-soldier/skin/star-soldier-ui.json"));

        pauseButton = new TextButton("Pause", skin);
        pauseButton.setSize(col_width*3,row_height);
        //pauseButton.setPosition(Gdx.graphics.getWidth() - pauseButton.getWidth(),Gdx.graphics.getHeight()-cadre.getHeight()-pauseButton.getHeight());
        //methode brute pour placer
        //pauseButton.setPosition(Gdx.graphics.getWidth() - pauseButton.getWidth(),(int) cam.position.y + cam.viewportHeight + 850);
        pauseButton.setPosition(col_width*(float)7.8,row_height*(float)8.6);
        pauseButton.scaleBy(2f);
        pauseButton.getLabel().setFontScale(col_width/40,row_height/40);
        pauseButton.setChecked(false);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.push(new PauseState(gsm));
            }
        });
        stage.addActor(pauseButton);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()){
            player.move();
            if(time>nextBullet){
                nextBullet=time+0.12f;
                projectiles.add(new Bullet(player.getPosition().x+ Player.PLAYER_WIDTH/3, player.getPosition().y, player.getMovement()+120));
            }
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
        for (Bullet bullet : projectiles){
            bullet.update(dt);
        }
        time+=dt;
        if (time>nextWave&&totalWaves<ENEMY_COUNT){
            totalWaves++;
            player.setMovement(100+time);
            nextWave+=(35/player.getMovement());
            Random rand = new Random();
            float firstEnemyX = rand.nextFloat()*cam.viewportWidth*(0.8f-((1/8)+(Ghost.WIDTH/cam.viewportWidth)));
            enemies.add(new Ghost(firstEnemyX,cam.position.y+Ghost.HEIGHT *2+cam.viewportHeight));

        }
        if(time>40) {
            gsm.set(new PlayStateWorld3Boss(gsm, player.getLifeCount(), score));
        }

        enemyAnimation.update(dt);
        cam.position.y= player.getPosition().y + 150;

        player.incLifeTimer(dt);
        for(int i = 0; i < enemies.size; i++){
            Ghost ghost = enemies.get(i);
            ghost.update(dt);
            if(cam.position.y-(cam.viewportHeight/2) > ghost.getPosition().y + ghost.HEIGHT){
                enemies.removeIndex(i);
            }

            if(player.getLifeTimer()>1f) { //verifies whether the player is still invincible
                player.setTexture(1);//change the player texture back to normal
                if (ghost.collides(player.getBounds())){ //if the player hitBox touches the wall hitBox, the player is hit
                    player.hurt();

                    /*//son collision
                    if (player.getLifeCount() > 0) {
                        oof.play(2f);
                    }


                    player.setTexture(2);
                    Gdx.input.vibrate(500);

                    player.resetLifeTimer();*/
                    player.lifeAnimation.update(dt);
                    if (player.getLifeCount() <= 0) {
                        try
                        {
                            Thread.sleep(1000);
                        }
                        catch(InterruptedException ex)
                        {
                            Thread.currentThread().interrupt();
                        }
                        gsm.set(new GameOverState(gsm, (int)score,3));//if the player have no more lives, change the playState to a gameOverState
                    }
                }
            }

            for(int j=0; j < projectiles.size; j++){
                Bullet bullet = projectiles.get(j);
                if (bullet.getPosition().y>player.getPosition().y+cam.viewportHeight-150){
                    bullet.dispose();
                    projectiles.removeIndex(j);
                }
                else if(bullet.collides(ghost.getBounds())){
                    bullet.dispose();
                    projectiles.removeIndex(j);
                    ghost.hurt();
                    if(ghost.getLifeCount()<=0) {
                        ghost.dispose();
                        enemies.removeIndex(i);
                        score++;
                        yourScoreName = "score: " + (int) score;
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
        for (Ghost ghost : enemies) {
            sb.draw(ghost.getTexture2(), ghost.getPosition().x, ghost.getPosition().y, Ghost.WIDTH, Ghost.HEIGHT);
        }
        for (Bullet bullet : projectiles){
            sb.draw(bullet.getTexture(),bullet.getPosition().x, bullet.getPosition().y, Bullet.BULLET_SIZE, Bullet.BULLET_SIZE);
        }
        sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y, Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        sb.draw(cadre, 0, cam.position.y + (cam.viewportHeight/2)-80, cam.viewportWidth, 80);
        sb.draw(enemyAnimation.getFrame(), 14, cam.position.y + (cam.viewportHeight/2)-70, enemy.getWidth()/3, enemy.getHeight());

        for (int i=1; i<=player.getLifeCount(); i++) {
            sb.draw(player.lifeAnimation.getFrame(), cam.position.x + cam.viewportWidth - 150, cam.position.y + cam.viewportHeight - (208+20*i));
        }
        yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);//score display (temporary)
        yourBitmapFontName.draw(sb, yourScoreName, 15,  cam.position.y + cam.viewportHeight - (290));
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
        for(Ghost ghost : enemies){
            ghost.dispose();
        }
        System.out.println("Play State World3 Disposed");
    }
}
