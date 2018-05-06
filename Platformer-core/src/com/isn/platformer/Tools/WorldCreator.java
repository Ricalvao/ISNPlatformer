package com.isn.platformer.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.isn.platformer.Platformer;
import com.isn.platformer.Gels.RedGel;
import com.isn.platformer.Screens.PlayScreen;
import com.isn.platformer.Sprites.Enemy;

public class WorldCreator {
	private Array<Enemy> enemies;
	
	public WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        
        //create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Platformer.SCALE, (rect.getY() + rect.getHeight() / 2) / Platformer.SCALE);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Platformer.SCALE, rect.getHeight() / 2 / Platformer.SCALE);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        
      //create object bodies/fixtures
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Platformer.SCALE, (rect.getY() + rect.getHeight() / 2) / Platformer.SCALE);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Platformer.SCALE, rect.getHeight() / 2 / Platformer.SCALE);
            fdef.shape = shape;
            fdef.filter.categoryBits = Platformer.OBJECT_BIT;
            body.createFixture(fdef);
        }
        
      //create red bodies/fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            new RedGel(screen, object);
        }
        
      //create orange bodies/fixtures
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Platformer.SCALE, (rect.getY() + rect.getHeight() / 2) / Platformer.SCALE);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Platformer.SCALE, rect.getHeight() / 2 / Platformer.SCALE);
            fdef.shape = shape;
            fdef.friction = 0.1f;
            fdef.filter.categoryBits = Platformer.ORANGE_GEL_BIT;
            body.createFixture(fdef);
        }
        
        //create blue bodies/fixtures
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Platformer.SCALE, (rect.getY() + rect.getHeight() / 2) / Platformer.SCALE);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Platformer.SCALE, rect.getHeight() / 2 / Platformer.SCALE);
            fdef.shape = shape;
            fdef.restitution = 1.5f;
            fdef.filter.categoryBits = Platformer.BLUE_GEL_BIT;
            body.createFixture(fdef);
        }
        
        //create all goombas
        enemies = new Array<Enemy>();
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies.add(new Enemy(screen, rect.getX() / Platformer.SCALE, rect.getY() / Platformer.SCALE));
        }
	}
	
	public Array<Enemy> getEnemies() {
        return enemies;
    }
}
