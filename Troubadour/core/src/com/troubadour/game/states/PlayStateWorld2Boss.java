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
import com.troubadour.game.sprites.Boss1;
import com.troubadour.game.sprites.Bullet;
import com.troubadour.game.sprites.BulletBoss1;
import com.troubadour.game.sprites.Player;

public class PlayStateWorld2Boss  extends State {
    private Player player;
    private Boss1 boss1;
    private Background background;

    private int score;
    private float time;
    private float nextBullet;
    private float nextBossBullet;
    private String yourScoreName;
    BitmapFont yourBitmapFontName;

    private Texture enemy;
    private Animation enemyAnimation;

    private Sound oof;
    private Sound death;
    private Array<Bullet> projectiles;
    private Array<BulletBoss1> projectilesBoss;

    private Stage stage;
    private Skin skin;
    private TextButton pauseButton;
    int row_height = Gdx.graphics.getHeight() / 12;
    int col_width = Gdx.graphics.getWidth() / 12;

    public PlayStateWorld2Boss (final GameStateManager gsm, int lifeCount, int score){
    public PlayStateWorld2Boss (GameStateManager gsm, int lives, int score){
        super(gsm);
        this.player=new Player(50, Troubadour.WIDTH/2-player.PLAYER_WIDTH/2);
        this.player.setLifeCount(lives);
        this.score = score;
        projectiles=new Array<Bullet>();

        this.player = new Player((Troubadour.WIDTH /4)-(Player.PLAYER_WIDTH/2), 50);
        this.player.setMovement(0);
        this.player.setLifeCount(lifeCount);
        this.player.setTexture(1);

        cam.setToOrtho(false, Troubadour.WIDTH /2, Troubadour.HEIGHT /2);
        cam.position.y= player.getPosition().y + 150;

        this.boss1 = new Boss1(player.getPosition().x + Player.PLAYER_WIDTH - Boss1.WIDTH, player.getPosition().y + cam.viewportHeight/2);

        this.score = score;

        this.time = 0;

        this.projectiles = new Array<Bullet>();
        this.background = new Background(0,0, "damier.png");

        this.enemy = new Texture("enemyAnimation.png");
        this.enemyAnimation = new Animation(new TextureRegion(enemy), 3, 2f);

        yourScoreName = "score: " + (int) this.score;
        yourBitmapFontName = new BitmapFont();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("button/star-soldier/skin/star-soldier-ui.json"));

        pauseButton = new TextButton("Pause", skin);
        pauseButton.setSize(col_width*4,row_height);
        //methode brute pour placer
        //pauseButton.setPosition(Gdx.graphics.getWidth() - pauseButton.getWidth(),(int) cam.position.y + cam.viewportHeight + 850);
        pauseButton.scaleBy(2f);
        pauseButton.getLabel().setFontScale(col_width/40,row_height/40);
        pauseButton.setChecked(false);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.push(new PauseState(gsm));
            }
        });
        pauseButton.setPosition(col_width*(float)7.8,row_height*(float)8.6);
        stage.addActor(pauseButton);


    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()){
            player.move();
            if(time>nextBullet){
                nextBullet=time+0.18f;
                projectiles.add(new Bullet(player.getPosition().x+ Player.PLAYER_WIDTH/3, player.getPosition().y, 120));
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

        for(int j=0; j < projectiles.size; j++){
            Bullet bullet = projectiles.get(j);
            if (bullet.getPosition().y>player.getPosition().y+cam.viewportHeight-150){
                bullet.dispose();
                projectiles.removeIndex(j);
            }
            else if(bullet.collides(boss1.getBounds())){
                bullet.dispose();
                projectiles.removeIndex(j);
                boss1.hurt();
                if(boss1.getLifeCount()<=0) {
                    boss1.dispose();
                    score = score + 50;
                    yourScoreName = "score: " + (int) score;
                }

            }

        }

        if(boss1.getLifeCount() == 0){
            gsm.set(new ChooseWorldState(gsm));
        }

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
        yourBitmapFontName.draw(sb, yourScoreName, 15, cam.position.y + cam.viewportHeight - (290));
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
