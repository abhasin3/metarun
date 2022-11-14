package com.anthonyb.metaboyjam.level.entity;

import com.anthonyb.metaboyjam.statics.Sprites;
import com.anthonyb.nohp.Game;
import com.anthonyb.nohp.math.Vector2D;

public class Bullet extends Enemy {

	private boolean withYDir = false;
	private float yDir = 0f;

	public Bullet(int x, int y, float xSpeed) {
		super(Sprites.bullet, x, y);
		super.runSpeed = xSpeed;
	}

	public Bullet withYDir(float yDir) {
		this.withYDir = true;
		this.yDir = yDir;
		return this;
	}

	public void tick() {
//		super.tick(); we don't want to apply the ground logic so I copied the Enemy tick method here to be modified
		Vector2D moveDir = new Vector2D(super.runSpeed, this.withYDir ? this.yDir : 0);
		super.position.move(moveDir);
		if (super.getMinX() >= Game.instance.level.camera.getMaxX()
				|| super.getMinY() >= Game.instance.level.camera.getMaxY()) {
			System.out.println("KILL BULLET");
			this.kill();
		}
	}

	@Override
	public int damage() {
		return 20;
	}
}
