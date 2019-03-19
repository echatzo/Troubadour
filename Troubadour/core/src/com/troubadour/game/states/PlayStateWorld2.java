package com.troubadour.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.troubadour.game.Troubadour;
import com.troubadour.game.sprites.Animation;
import com.troubadour.game.sprites.Background;
import com.troubadour.game.sprites.Bullet;
import com.troubadour.game.sprites.Ghost;
import com.troubadour.game.sprites.Player;
import com.troubadour.game.sprites.Wall;


public class PlayStateWorld2 extends State {




    private static final int ENEMY_SPACING = 50;
    private static final int ENEMY_COUNT = 100;


    private Player player;
    private Array<Background> backgrounds;
    private Texture enemy;
    private Animation enemyAnimation;

    private float score;
    private String yourScoreName;
    BitmapFont yourBitmapFontName;

    private Sound oof;
    private Sound death;



    //private Array<Wall> walls;
    private Array<Ghost> enemies;
    private Array<Bullet> projectiles;

    public PlayStateWorld2(GameStateManager gsm){
        super(gsm);

        player = new Player((Troubadour.WIDTH /4)-(Player.PLAYER_WIDTH/2), 100);
        cam.setToOrtho(false, Troubadour.WIDTH /2, Troubadour.HEIGHT /2);
        backgrounds = new Array<Background>();
        for(int i=0; i<=1; i++){
            backgrounds.add(new Background(0,400*i, "damier.png"));
        }
        enemy = new Texture("enemyAnimation.png");
        enemyAnimation = new Animation(new TextureRegion(enemy), 3, 2f);
        //walls = new Array<Wall>();
        enemies = new Array<Ghost>();

        oof = Gdx.audio.newSound(Gdx.files.internal("oof.mp3"));
        death = Gdx.audio.newSound(Gdx.files.internal("death.mp3"));

        /*for(int i = 1; i <= WALL_COUNT; i ++){
            walls.add(new Wall(i*(WALL_SPACING + Wall.WALL_THICK)));
        }*/
        for(int i = 1; i <= ENEMY_COUNT; i ++){
            //enemies.add(new Ghost((Troubadour.WIDTH /4),i*(ENEMY_SPACING + Ghost.GHOST_HEIGHT)));
            enemies.add(new Ghost(i*(ENEMY_SPACING + Ghost.GHOST_HEIGHT)+cam.viewportHeight));
        }
        projectiles=new Array<Bullet>();
        score = 0;
        yourScoreName = "score: 0";
        yourBitmapFontName = new BitmapFont();

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()){
            player.move();
        }
        if(Gdx.input.justTouched()){
            if (projectiles.size<5) {
                projectiles.add(new Bullet(player.getPosition().x, player.getPosition().y));
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

        //score+=dt; //increments the score
        //yourScoreName = "score: " + (int) score;

        enemyAnimation.update(dt);
        cam.position.y= player.getPosition().y + 150;

        for(int i = 0; i < enemies.size; i++){
            Ghost ghost = enemies.get(i);
            ghost.update(dt);
            if(cam.position.y-(cam.viewportHeight/2) > ghost.getPositionRight().y + ghost.GHOST_WIDTH){
                score=score+1;
                yourScoreName = "score: " + (int) score;
                //ghost.reposition(ghost.getPositionRight().y + ((Ghost.GHOST_WIDTH + ENEMY_SPACING)*ENEMY_COUNT));
                enemies.removeIndex(i);
            }
            player.incLifeTimer(dt);
            if(player.getLifeTimer()>150f) { //verifies whether the player is still invincible
                player.setTexture(1);//change the player texture back to normal
                if (ghost.collides(player.getBounds())){ //if the player hitBox touches the wall hitBox, the player is hit
                    player.decLifeCount();

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
                if (bullet.getPosition().y>player.getPosition().y+cam.viewportHeight){
                    bullet.dispose();
                    projectiles.removeIndex(j);
                }
                else if(bullet.collides(ghost.getLeftBounds())||bullet.collides(ghost.getRightBounds())){
                    bullet.dispose();
                    projectiles.removeIndex(j);
                    ghost.dispose();
                    enemies.removeIndex(i);
                    score=score+1;
                    yourScoreName = "score: " + (int) score;
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
        for (Ghost ghost : enemies) {
            sb.draw(ghost.getTexture(), ghost.getPositionLeft().x, ghost.getPositionLeft().y, Ghost.GHOST_WIDTH, Ghost.GHOST_HEIGHT);
            sb.draw(ghost.getTexture(), ghost.getPositionRight().x, ghost.getPositionRight().y, Ghost.GHOST_WIDTH, Ghost.GHOST_HEIGHT);
        }
        for (Bullet bullet : projectiles){
            sb.draw(bullet.getTexture(),bullet.getPosition().x, bullet.getPosition().y, Bullet.BULLET_SIZE, Bullet.BULLET_SIZE);
        }
        sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y, Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        sb.draw(enemyAnimation.getFrame(), 0, cam.position.y + (cam.viewportHeight/2)-80, cam.viewportWidth, 80);

        for (int i=1; i<=player.getLifeCount(); i++) {
            sb.draw(player.lifeAnimation.getFrame(), cam.position.x + cam.viewportWidth - 150, cam.position.y + cam.viewportHeight - (205+20*i));
        }
        yourBitmapFontName.setColor(1.0f, 1.0f, 0f, 1.0f);//score display (temporary)
        yourBitmapFontName.draw(sb, yourScoreName, 15, (int) cam.position.y + cam.viewportHeight - (290));
        sb.end();
    }

    @Override
    public void dispose() {

        for(Background background : backgrounds){
            background.dispose();
        }
        player.dispose();
        oof.dispose();
        death.dispose();
        for(Ghost ghost : enemies){
            ghost.dispose();
        }
        System.out.println("Play State World2 Disposed");
    }
}
