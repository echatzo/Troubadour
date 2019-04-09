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
import com.troubadour.game.sprites.Player;
import com.troubadour.game.sprites.Skeleton;

import java.util.Random;


public class PlayStateWorld2 extends State {




    private static final int ENEMY_COUNT = 100;

    private Stage stage;
    private Skin skin;
    private TextButton pauseButton;
    int row_height = Gdx.graphics.getHeight() / 12;
    int col_width = Gdx.graphics.getWidth() / 12;


    private Player player;
    private Array<Background> backgrounds;
    private Texture enemy;
    private Texture cadre;
    private Animation enemyAnimation;

    private int score;
    private float time;
    private float nextBullet;
    private float nextWave;
    private int totalWaves;
    private String yourScoreName;
    BitmapFont yourBitmapFontName;

    private Sound oof;
    private Sound death;



    //private Array<Wall> walls;
    private Array<Skeleton> enemies;
    private Array<Bullet> projectiles;

    public PlayStateWorld2(final GameStateManager gsm){
        super(gsm);

        player = new Player((Troubadour.WIDTH /4)-(Player.PLAYER_WIDTH/2), 100);
        cam.setToOrtho(false, Troubadour.WIDTH /2, Troubadour.HEIGHT /2);

        //creates an array of two backgrounds in order to scroll them
        backgrounds = new Array<Background>();
        for(int i=0; i<=1; i++){
            backgrounds.add(new Background(0,400*i, "background4.png"));
        }

        //creates the object for the frame above with the enemy's head
        cadre = new Texture("cadregris.png");
        enemy = new Texture("boss1.png");
        enemyAnimation = new Animation(new TextureRegion(enemy), 3, 2f);
        enemies = new Array<Skeleton>();

        oof = Gdx.audio.newSound(Gdx.files.internal("oof.mp3"));
        death = Gdx.audio.newSound(Gdx.files.internal("death.mp3"));

        projectiles=new Array<Bullet>(); //create an empty array of bullets

        //score initialisation
        score = 0;
        yourScoreName = "score: 0";
        yourBitmapFontName = new BitmapFont();

        //chronometer in order to know when to create a new wave of enemies
        time =0;
        nextWave=2;
        nextBullet=1;
        totalWaves=0;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        //font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("button/star-soldier/skin/star-soldier-ui.json"));

        pauseButton = new TextButton("Pause", skin);
        pauseButton.setSize(col_width*3,row_height);
        //setPosition(Gdx.graphics.getWidth() - pauseButton.getWidth(),Gdx.graphics.getHeight()-enemy.getHeight()-pauseButton.getHeight());
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
            if(time>nextBullet){//launches a new bullet every x seconds
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
            player.setMovement(100+time); //scrolling acceleration
            nextWave+=(140/player.getMovement()); //computes the timing of the next wave of enemies

            Random rand = new Random();//this random objects allows to put a random amount of enemies, between 4 and 8
            int enemiesOnRow = 4+ rand.nextInt(4);
            float firstEnemyX = rand.nextFloat()*cam.viewportWidth*(0.8f-(enemiesOnRow*((1/8)+(Skeleton.WIDTH/cam.viewportWidth))));
            for (int j =0; j<enemiesOnRow; j++){
                enemies.add(new Skeleton(firstEnemyX+j*cam.viewportWidth/8,cam.position.y+ Skeleton.HEIGHT *2+cam.viewportHeight));
            }
        }
        if(time>15){
            gsm.set(new PlayStateWorld2Boss(gsm, player.getLifeCount(), score));
        }

        enemyAnimation.update(dt);
        cam.position.y= player.getPosition().y + 150;
        player.incLifeTimer(dt);
        for(int i = 0; i < enemies.size; i++){
            Skeleton skeleton = enemies.get(i);
            skeleton.update(dt);
            if(cam.position.y-(cam.viewportHeight/2) > skeleton.getPosition().y + skeleton.HEIGHT){
                enemies.removeIndex(i);
            }

            if(player.getLifeTimer()>0.5f) { //verifies whether the player is still invincible
                player.setTexture(1);//change the player texture back to normal
                if (skeleton.collides(player.getBounds())){ //if the player hitBox touches the wall hitBox, the player is hit
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

            for(int j=0; j < projectiles.size; j++){
                Bullet bullet = projectiles.get(j);
                if (bullet.getPosition().y>player.getPosition().y+cam.viewportHeight-150){// disposes of bullets that are to high
                    bullet.dispose();
                    projectiles.removeIndex(j);
                }
                else if(bullet.collides(skeleton.getBounds())){//checks for collisions between bullets and skeletons
                    bullet.dispose();
                    projectiles.removeIndex(j);
                    skeleton.hurt();
                    if(skeleton.getLifeCount()<=0) {
                        skeleton.dispose();
                        if(skeleton.isInvincible()){
                            score+=4;
                        }
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
        for (Skeleton skeleton : enemies) {
            sb.draw(skeleton.getTexture(), skeleton.getPosition().x, skeleton.getPosition().y, Skeleton.WIDTH, Skeleton.HEIGHT);
        }
        for (Bullet bullet : projectiles){
            sb.draw(bullet.getTexture(),bullet.getPosition().x, bullet.getPosition().y, Bullet.BULLET_SIZE, Bullet.BULLET_SIZE);
        }
        sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y, Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);

        sb.draw(cadre, 0, cam.position.y + (cam.viewportHeight/2)-80, cam.viewportWidth, 80);
        sb.draw(enemyAnimation.getFrame(), 14, cam.position.y + (cam.viewportHeight/2)-70, enemy.getWidth()/2, enemy.getHeight()*3/2);

        for (int i=1; i<=player.getLifeCount(); i++) {
            sb.draw(player.lifeAnimation.getFrame(), cam.position.x + cam.viewportWidth - 150, cam.position.y + cam.viewportHeight - (208+20*i));
        }
        yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);//score display (temporary)
        yourBitmapFontName.draw(sb, yourScoreName, 15, cam.position.y + cam.viewportHeight - (290));
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
        for(Skeleton skeleton : enemies){
            skeleton.dispose();
        }
        System.out.println("Play State World2 Disposed");
    }
}
