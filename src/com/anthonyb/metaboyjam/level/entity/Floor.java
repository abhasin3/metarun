package com.anthonyb.metaboyjam.level.entity;

import com.anthonyb.metaboyjam.statics.Sprites;
import com.anthonyb.nohp.Game;

public class Floor extends Mob {

	private boolean hasBeenOnScreen = false;

	public Floor(int x, int y) {
		super(Sprites.floor);
		super.width = 150;
		super.height = 50;
		super.position.set(x, y);
		super.position.zIndex = -1; // Push to back.
		super.persistent = true; // make it persistent so we can check if on screen and destroy after.
	}

	public void tick() {
		if (this.getMaxX() < Game.instance.level.camera.getMinX()) {
			Game.instance.level.removeEntity(this);
			System.out.println("DELETE FLOOR");
		}
	}

//	public void tick() {
////		super.position.set();
//	}

//	private int width, height;
//
//	public Floor(int x, int y, int width, int height) {
//		super.position.set(x, y);
//		this.width = width;
//		this.height = height;
//	}

//	public float getMinX() {
//		return super.position.x - this.width / 2f;
//	}
//
//	public float getMinY() {
//		return super.position.y - this.height / 2f;
//	}
//
//	public float getMaxX() {
//		return super.position.x + this.width / 2f;
//
//	}
//
//	public float getMaxY() {
//		return super.position.y + this.height / 2f;
//	}
//
//	public void tick() {
//	}
//
//	public void render() {
//		Screen.rect().start(super.getMinXi(), super.getMinYi()).dimensions(super.getWidth(), super.getHeight())
//				.color(Color.RED).fill().draw();
//	}
}
