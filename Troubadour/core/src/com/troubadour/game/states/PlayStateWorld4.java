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
import com.troubadour.game.sprites.Frog;

import java.util.Random;


public class PlayStateWorld4 extends State {




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
    private Texture frogBulletTexture;
    private Animation enemyAnimation;

    private int score;
    private float time;
    private float nextBullet;
    private float nextFrogBullet;
    private float nextWave;
    private int totalWaves;
    private String yourScoreName;
    BitmapFont yourBitmapFontName;

    private Random rand;



    //private Array<Wall> walls;
    private Array<Frog> enemies;
    private Array<Bullet> projectiles;
    private Array<Bullet> frogProjectiles;

    public PlayStateWorld4(final GameStateManager gsm){
        super(gsm);

        player = new Player((Troubadour.WIDTH /4)-(Player.PLAYER_WIDTH/2), 100);
        cam.setToOrtho(false, Troubadour.WIDTH /2, Troubadour.HEIGHT /2);
        backgrounds = new Array<Background>();
        for(int i=0; i<=1; i++){
            backgrounds.add(new Background(0,400*i, "background3.png"));
        }
        cadre = new Texture("cadrevert.png");
        enemy = new Texture("frog.png");
        enemyAnimation = new Animation(new TextureRegion(enemy), 3, 2f);
        enemies = new Array<Frog>();
        frogBulletTexture= new Texture("bubblesAnimation.png");
        rand = new Random();
/*
        for(int i = 1; i <= ENEMY_COUNT; i ++){
            Random rand = new Random();
            int enemiesOnRow = 4+ rand.nextInt(4);
            float firsEnemyX = rand.nextFloat()*cam.viewportWidth*(1-(enemiesOnRow/6));
            for (int j =0; j<enemiesOnRow; j++){
                enemies.add(new Ghost(firsEnemyX+j*cam.viewportWidth/8,i*(ENEMY_SPACING + Ghost.HEIGHT)+cam.viewportHeight));
            }
        }
        */
        projectiles=new Array<Bullet>();
        frogProjectiles=new Array<Bullet>();
        score = 0;
        yourScoreName = "score: 0";
        yourBitmapFontName = new BitmapFont();
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
            //System.out.println(player.getMovement());
            nextWave+=(50/player.getMovement());




           float firstEnemyX = rand.nextFloat()*cam.viewportWidth*(1f-((1/8)+(Frog.WIDTH/cam.viewportWidth)));

           //System.out.println(rand.nextFloat()*(Troubadour.WIDTH-(Frog.WIDTH)));
            //float firstEnemyX = rand.nextFloat()*(Troubadour.WIDTH-(Frog.WIDTH));
            enemies.add(new Frog(firstEnemyX,cam.position.y+Frog.WIDTH *2+cam.viewportHeight));
        }
        if(time>40){
            gsm.set(new PlayStateWorld4Boss(gsm, player.getLifeCount(), score));
        }

        enemyAnimation.update(dt);
        cam.position.y= player.getPosition().y + 150;

        player.incLifeTimer(dt);
        for(int i = 0; i < enemies.size; i++){
            Frog frog = enemies.get(i);
            frog.update(dt);
            if(time>nextFrogBullet&&rand.nextInt(10)==3) {
                nextFrogBullet=time+0.3f;
                frogProjectiles.add(new Bullet(frog.getPosition().x+frog.WIDTH/2-Bullet.BULLET_SIZE/2, frog.getPosition().y, -70, frogBulletTexture));
            }
            if(cam.position.y-(cam.viewportHeight/2) > frog.getPosition().y + frog.HEIGHT){
                enemies.removeIndex(i);
            }

            if(player.getLifeTimer()>0.5f) { //verifies whether the player is still invincible
                player.setTexture(1);//change the player texture back to normal
                if (frog.collides(player.getBounds())){ //if the player hitBox touches the wall hitBox, the player is hit
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
                        gsm.set(new GameOverState(gsm, (int)score,4));//if the player have no more lives, change the playState to a gameOverState
                    }
                }
            }

            for(int j=0; j < projectiles.size; j++){
                Bullet bullet = projectiles.get(j);
                if (bullet.getPosition().y>player.getPosition().y+cam.viewportHeight-150){
                    bullet.dispose();
                    projectiles.removeIndex(j);
                }
                else if(bullet.collides(frog.getBounds())){
                    bullet.dispose();
                    projectiles.removeIndex(j);
                    frog.hurt();
                    if(frog.getLifeCount()<=0) {
                        frog.dispose();
                        enemies.removeIndex(i);
                        score++;
                        yourScoreName = "score: " + (int) score;
                    }

                }

            }
        }
        for(int i = 0; i < frogProjectiles.size; i++){
            Bullet frogBullet = frogProjectiles.get(i);
            frogBullet.update(dt);
            if (frogBullet.getPosition().y<player.getPosition().y-100){
                frogProjectiles.removeIndex(i);
            }
            else if(player.getLifeTimer()>0.5f) { //verifies whether the player is still invincible
                player.setTexture(1);//change the player texture back to normal
                if (frogBullet.collides(player.getBounds())){ //if the player hitBox touches the wall hitBox, the player is hit
                    player.hurt();
                    frogProjectiles.removeIndex(i);
                   /* //son collision
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
                        gsm.set(new GameOverState(gsm, (int)score,4));//if the player have no more lives, change the playState to a gameOverState
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
        for (Frog frog : enemies) {
            sb.draw(frog.getTexture(), frog.getPosition().x, frog.getPosition().y, Frog.WIDTH, Frog.HEIGHT);
        }
        for (Bullet bullet : projectiles){
            sb.draw(bullet.getTexture(),bullet.getPosition().x, bullet.getPosition().y, Bullet.BULLET_SIZE, Bullet.BULLET_SIZE);
        }
        for (Bullet bullet : frogProjectiles){
            sb.draw(bullet.getTexture(),bullet.getPosition().x, bullet.getPosition().y, Bullet.BULLET_SIZE, Bullet.BULLET_SIZE);
        }
        sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y, Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        sb.draw(cadre, 0, cam.position.y + (cam.viewportHeight/2)-80, cam.viewportWidth, 80);
        sb.draw(enemyAnimation.getFrame(), 14, cam.position.y + (cam.viewportHeight/2)-70, cadre.getHeight()*2/3, cadre.getHeight()*2/3);

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
        for(Frog frog : enemies){
            frog.dispose();
        }
        System.out.println("Play State World2 Disposed");
    }
}
