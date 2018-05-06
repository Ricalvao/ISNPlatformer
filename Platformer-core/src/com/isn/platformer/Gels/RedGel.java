package com.isn.platformer.Gels;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.isn.platformer.Platformer;
import com.isn.platformer.Screens.PlayScreen;

public class RedGel extends Gel{
	private static TiledMapTileSet tileSet;
    private final int OFF = 36;
    private final int ON = 37;

    public RedGel(PlayScreen screen, MapObject object){
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("Tileset");
        fixture.setUserData(this);
        setCategoryFilter(Platformer.RED_GEL_BIT);
    }

    public void turnOnOff(boolean b) {
        if(b) {
            getCell().setTile(tileSet.getTile(ON));
        } else {
        	getCell().setTile(tileSet.getTile(OFF));
        }
    }
}
