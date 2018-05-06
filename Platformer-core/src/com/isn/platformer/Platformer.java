package com.isn.platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.isn.platformer.Screens.PlayScreen;

public class Platformer extends Game {
	//Taille de l'écran
	public static final int SCREEN_WIDTH = 400;
	public static final int SCREEN_HEIGHT = 208;
	
	//Echelle pour Box2D
	public static final float SCALE = 1/100;

	//Bits pour les collisions
	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short CHELL_BIT = 2;
	public static final short ENEMY_BIT = 4;
	public static final short RED_PAINT_BIT = 8;
	public static final short ORANGE_PAINT_BIT = 16;
	public static final short BLUE_PAINT_BIT = 32;
	public static final short LIGHT_BRIDGE_BIT = 64;
	public static final short LASER_BIT = 128;
	public static final short OBJECT_BIT = 256;

	public SpriteBatch batch;

	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}

	@Override
	public void render () {
		super.render();
	}
}

