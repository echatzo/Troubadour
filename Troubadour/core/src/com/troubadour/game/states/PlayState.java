package com.troubadour.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.troubadour.game.Troubadour;
import com.troubadour.game.sprites.Player;
import com.troubadour.game.sprites.Wall;

public class PlayState extends State {


    private static final int WALL_SPACING = 100;
    private static final int WALL_COUNT = 6;

    private Player player;
    private Texture background;
    private Texture enemy;

    private Array<Wall> walls;

    public PlayState(GameStateManager gsm){
        super(gsm);
        player = new Player((Troubadour.WIDTH /4)-(Player.PLAYER_WIDTH/2), 30);
        cam.setToOrtho(false, Troubadour.WIDTH /2, Troubadour.HEIGHT /2);
        background = new Texture("background.png");
        enemy = new Texture("enemy.png");

        walls = new Array<Wall>();

        for(int i = 1; i <= WALL_COUNT; i ++){
            walls.add(new Wall(i*(WALL_SPACING + Wall.WALL_THICK)));
        }

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()){
            player.move();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        player.update(dt);
        cam.position.y= player.getPosition().y + 180;

        for(Wall wall : walls){
            if(cam.position.y-(cam.viewportHeight/2) > wall.getPosrightWall().y + wall.getrightWall().getWidth()){
                wall.reposition(wall.getPosrightWall().y + ((Wall.WALL_THICK + WALL_SPACING)*WALL_COUNT));
            }
        }
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, cam.position.y - (cam.viewportHeight/2), cam.viewportWidth, cam.viewportHeight);

        sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y, Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        for (Wall wall : walls) {
            sb.draw(wall.getleftWall(), wall.getPosleftWall().x, wall.getPosleftWall().y, Wall.WALL_LENGTH, Wall.WALL_THICK);
            sb.draw(wall.getrightWall(), wall.getPosrightWall().x, wall.getPosrightWall().y, Wall.WALL_LENGTH, Wall.WALL_THICK);
        }
        sb.draw(enemy, 0, cam.position.y + (cam.viewportHeight/2)-80, cam.viewportWidth, 80);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}