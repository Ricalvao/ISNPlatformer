package com.isn.platformer.Gels;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.isn.platformer.Platformer;
import com.isn.platformer.Screens.PlayScreen;

public abstract class Gel {
	 	protected World world;
	    protected TiledMap map;
	    protected Rectangle bounds;
	    protected Body body;
	    protected PlayScreen screen;
	    protected MapObject object;

	    protected Fixture fixture;

	    public  Gel(PlayScreen screen, MapObject object){
	        this.object = object;
	        this.screen = screen;
	        this.world = screen.getWorld();
	        this.map = screen.getMap();
	        this.bounds = ((RectangleMapObject) object).getRectangle();

	        BodyDef bdef = new BodyDef();
	        FixtureDef fdef = new FixtureDef();
	        PolygonShape shape = new PolygonShape();

	        bdef.type = BodyDef.BodyType.StaticBody;
	        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Platformer.SCALE, (bounds.getY() + bounds.getHeight() / 2) / Platformer.SCALE);

	        body = world.createBody(bdef);

	        shape.setAsBox(bounds.getWidth() / 2 / Platformer.SCALE, bounds.getHeight() / 2 / Platformer.SCALE);
	        fdef.shape = shape;
	        fixture = body.createFixture(fdef);

	    }

	    public void setCategoryFilter(short filterBit){
	        Filter filter = new Filter();
	        filter.categoryBits = filterBit;
	        fixture.setFilterData(filter);
	    }

	    public TiledMapTileLayer.Cell getCell(){
	        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
	        return layer.getCell((int)(body.getPosition().x * Platformer.SCALE / 16), (int)(body.getPosition().y * Platformer.SCALE / 16));
	    }

	}
