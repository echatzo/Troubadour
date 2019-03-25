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
    private Background background;
    private Texture bossTexture;
    private Animation bossAnimation;

    private float score;
    private float time;
    private float nextBullet;
    private String yourScoreName;
    BitmapFont yourBitmapFontName;

    private Sound oof;
    private Sound death;
    private Array<Bullet> projectiles;

    public PlayStateWorld2Boss (GameStateManager gsm, Player player, int score){
        super(gsm);
        this.player=player;
        cam.setToOrtho(false, Troubadour.WIDTH /2, Troubadour.HEIGHT /2);
        background = new Background(0,0, "damier.png");
        bossTexture = new Texture("enemysque.png");
        bossAnimation = new Animation(new TextureRegion(bossTexture), 3, 2f);
        player.movement=0;
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
        sb.draw(bossAnimation.getFrame(), player.getPosition().x, player.getPosition().y, Boss1.WIDTH, Boss1.HEIGHT);
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
