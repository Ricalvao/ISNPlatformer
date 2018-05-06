package com.isn.platformer.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.isn.platformer.Platformer;
import com.isn.platformer.Screens.PlayScreen;

public class Cube extends Sprite{
	protected World world;
    protected PlayScreen screen;
    public Body body;
    private TextureRegion cubeOff;
    private TextureRegion cubeOn;
    
    public Cube(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineCube();
        
        cubeOff = new TextureRegion(new Texture("sprites//cube_1.png"));
        cubeOn = new TextureRegion(new Texture("sprites//cube_2.png"));
        setBounds(getX(), getY(), 16 / Platformer.SCALE, 16 / Platformer.SCALE);
        setRegion(cubeOff);
    }

    public void update(float dt){
        setPosition(body.getPosition().x - getWidth() / 2 + 1 / (2 * Platformer.SCALE), body.getPosition().y - getWidth() / 2 + 1 / (2 * Platformer.SCALE));
    }

    protected void defineCube() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(50 / Platformer.SCALE, 100 / Platformer.SCALE);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        
        PolygonShape square = new PolygonShape();
        square.setAsBox(7.5f / Platformer.SCALE, 7.5f / Platformer.SCALE);

        fdef.shape = square;
        fdef.filter.categoryBits = Platformer.OBJECT_BIT;
        fdef.filter.maskBits = Platformer.GROUND_BIT |
				               Platformer.ENEMY_BIT |
	                           Platformer.ORANGE_GEL_BIT|
	                           Platformer.RED_GEL_BIT|
	                           Platformer.BLUE_GEL_BIT|
	                           Platformer.LIGHT_BRIDGE_BIT|
	                           Platformer.CHELL_BIT|
	                           Platformer.OBJECT_BIT;
        
        body.createFixture(fdef).setUserData(this);
    }
    
    public void turnOnOff(boolean b) {
    	if(!b) {
    		setRegion(cubeOff);
    	} else {
    		setRegion(cubeOn);
    	}
    }
}
