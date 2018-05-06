package com.isn.platformer.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.isn.platformer.Platformer;
import com.isn.platformer.Screens.PlayScreen;

public class Chell extends Sprite{
	public enum State { FALLING, JUMPING, STANDING, RUNNING, GROWING, DEAD };
    public State currentState;
    public State previousState;

    public World world;
    public Body body;

    private TextureRegion stand;
    private Animation<TextureRegion> run;
    private TextureRegion jump;
    private TextureRegion dead;

    private float stateTimer;
    private boolean runningRight;
    private boolean chellIsDead;

    public Chell(PlayScreen screen){
        //initialize default values
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        //get run animation frames and add them to marioRun Animation
        for(int i = 1; i < 4; i++) {
        	frames.add(new TextureRegion(new Texture("sprites//run_" + i + ".png")));
        }
            
        run = new Animation<TextureRegion>(0.1f, frames);

        frames.clear();

        //get jump animation frames and add them to marioJump Animation
        jump = new TextureRegion(new Texture("sprites//jump.png"));

        //create texture region for mario standing
        stand = new TextureRegion(new Texture("sprites//stand.png"));

        //create dead mario texture region
        dead = new TextureRegion(new Texture("sprites//dead.png"));

        //define mario in Box2d
        defineChell();

        //set initial values for marios location, width and height. And initial frame as marioStand.
        setBounds(0, 0, 16 * Platformer.SCALE, 16 * Platformer.SCALE);
        setRegion(stand);

     }

    public void update(float dt){
    	//update our sprite to correspond with the position of our Box2D body
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    public TextureRegion getFrame(float dt){
        //get marios current state. ie. jumping, running, standing...
        currentState = getState();

        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch(currentState){
            case DEAD:
                region = dead;
                break;
            case JUMPING:
                region = jump;
                break;
            case RUNNING:
                region = run.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = stand;
                break;
        }

        //if mario is running left and the texture isnt facing left... flip it.
        if((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        //if mario is running right and the texture isnt facing right... flip it.
        else if((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;

    }

    public State getState(){
        //Test to Box2D for velocity on the X and Y-Axis
        //if mario is going positive in Y-Axis he is jumping... or if he just jumped and is falling remain in jump state
        if(chellIsDead)
            return State.DEAD;
        else if((body.getLinearVelocity().y > 0 && currentState == State.JUMPING) || (body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        //if negative in Y-Axis mario is falling
        else if(body.getLinearVelocity().y < 0)
            return State.FALLING;
        //if mario is positive or negative in the X axis he is running
        else if(body.getLinearVelocity().x != 0)
            return State.RUNNING;
        //if none of these return then he must be standing
        else
            return State.STANDING;
    }

    public void die() {

        if (!isDead()) {

            chellIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = Platformer.NOTHING_BIT;

            for (Fixture fixture : body.getFixtureList()) {
                fixture.setFilterData(filter);
            }

            body.applyLinearImpulse(new Vector2(0, 4f), body.getWorldCenter(), true);
        }
    }

    public boolean isDead(){
        return chellIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public void jump(){
        if ( currentState != State.JUMPING ) {
            body.applyLinearImpulse(new Vector2(0, 4f), body.getWorldCenter(), true);
            currentState = State.JUMPING;
        }
    }

    public void defineChell(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 * Platformer.SCALE, 32 * Platformer.SCALE);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 * Platformer.SCALE);
        fdef.filter.categoryBits =  Platformer.CHELL_BIT;
        fdef.filter.maskBits = Platformer.GROUND_BIT |
        					   Platformer.ENEMY_BIT |
        		               Platformer.RED_PAINT_BIT|
        		               Platformer.ORANGE_PAINT_BIT|
        		               Platformer.BLUE_PAINT_BIT|
        		               Platformer.LIGHT_BRIDGE_BIT|
        		               Platformer.OBJECT_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    public void fire(){
        //fireballs.add(new FireBall(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false));
    }

    public void draw(Batch batch){
        super.draw(batch);
    }
}
