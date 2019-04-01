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
import com.troubadour.game.sprites.Boss1;
import com.troubadour.game.sprites.Bullet;
import com.troubadour.game.sprites.Player;

public class PlayStateWorld2Boss  extends State {
    private Player player;
    private Boss1 boss1;
    private Background background;

    private int score;
    private float time;
    private float nextBullet;
    private String yourScoreName;
    BitmapFont yourBitmapFontName;

    private Texture enemy;
    private Animation enemyAnimation;

    private Sound oof;
    private Sound death;
    private Array<Bullet> projectiles;

    public PlayStateWorld2Boss (GameStateManager gsm, Player player, int score){
        super(gsm);
        this.player=player;
        this.score = score;
        projectiles=new Array<Bullet>();


        cam.setToOrtho(false, Troubadour.WIDTH /2, Troubadour.HEIGHT /2);
        cam.position.y= player.getPosition().y + 150;
        background = new Background(0,0, "damier.png");
        player.setMovement(0);
        player.setPosition((Troubadour.WIDTH /4)-(Player.PLAYER_WIDTH/2), 50);
        boss1 = new Boss1(player.getPosition().x + Player.PLAYER_WIDTH - Boss1.WIDTH, player.getPosition().y + cam.viewportHeight/2);

        enemy = new Texture("enemyAnimation.png");
        enemyAnimation = new Animation(new TextureRegion(enemy), 3, 2f);

        time = 0;
        yourScoreName = "score: 0";
        yourBitmapFontName = new BitmapFont();
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()){
            player.move();
            if(time>nextBullet){
                nextBullet=time+0.18f;
                projectiles.add(new Bullet(player.getPosition().x+ Player.PLAYER_WIDTH/3, player.getPosition().y, 80));
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        player.update(dt);
        boss1.update(dt);

        for (Bullet bullet : projectiles){
            bullet.update(dt);
        }
        time+=dt;
        enemyAnimation.update(dt);


    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background.getTexture(), 0, background.getPos().y, 240, 400);
        for (Bullet bullet : projectiles){
            sb.draw(bullet.getTexture(),bullet.getPosition().x, bullet.getPosition().y, Bullet.BULLET_SIZE, Bullet.BULLET_SIZE);
        }
        sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y, Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        sb.draw(boss1.getAnimation().getFrame(), boss1.getPosition().x, boss1.getPosition().y, Boss1.WIDTH, Boss1.HEIGHT);
        for (int i=1; i<=player.getLifeCount(); i++) {
            sb.draw(player.lifeAnimation.getFrame(), cam.position.x + cam.viewportWidth - 150, cam.position.y + cam.viewportHeight - (205+20*i));
        }
        yourBitmapFontName.setColor(1.0f, 1.0f, 0f, 1.0f);//score display (temporary)
        yourBitmapFontName.draw(sb, yourScoreName, 15, (int) cam.position.y + cam.viewportHeight - (290));
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
