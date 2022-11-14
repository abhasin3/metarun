package com.anthonyb.metaboyjam.level.entity;

import java.awt.Color;

import com.anthonyb.nohp.Game;
import com.anthonyb.nohp.GameSettings;
import com.anthonyb.nohp.Screen;
import com.anthonyb.nohp.math.Vector2D;
import com.anthonyb.nohp.texture.Sprite;

// Essentially a mario goomba, displayed as an electricity metaboy.
public abstract class Enemy extends Mob {

	protected float runSpeed = 8;

	private boolean allowJumpAttempt = true;
	private int jumpTimer = 0;

	public Enemy(Sprite sprite, int x, int y) {
		super(sprite);
		super.position.x = x;
		super.position.y = y;
		super.persistent = true;
	}

	@Override
	public void tick() {
		Vector2D moveDir = new Vector2D(this.runSpeed, 1.25f);
		if (this.jumpTimer >= 0.85 * Player.JUMP_TIME) {
			moveDir.y -= 3f;
//			moveDir.y -= 2.4f;
		} else if (this.jumpTimer >= 0.5 * Player.JUMP_TIME) {
			moveDir.y -= 2.4f;
//			moveDir.y -= 1.8f;
		} else if (this.jumpTimer >= 0.1 * Player.JUMP_TIME) {
			moveDir.y -= 1.8f;
//			moveDir.y -= 1.65f;
		} else if (this.jumpTimer > 0) {
			moveDir.y -= 1.65f;
//			moveDir.y -= 1f;
		}
		if (this.jumpTimer > 0) {
			this.jumpTimer--;
		}
		if (super.isGrounded()) {
			moveDir.y = Math.min(moveDir.y, 0);
			this.allowJumpAttempt = true;
		} else {
//			If falling, give it a chance to jump.
			if (this.allowJumpAttempt) {
				System.out.println("ALLOW JUMP ATTEMPT");
//				Chance of jump
				if (Math.random() <= 0.75) {
					this.jumpTimer = Player.JUMP_TIME - 10;
					System.out.println("JUMPIN");
				}
			}
			this.allowJumpAttempt = false;
		}
		moveDir.y *= 4f;
		super.position.move(moveDir);
		if (super.getMinX() >= Game.instance.level.camera.getMaxX()
				|| super.getMinY() >= Game.instance.level.camera.getMaxY()) {
			System.out.println("KILL ENEMY");
			this.kill();
		}
	}

	public void onDie() {
	}

	public void kill() {
		this.onDie();
		Game.instance.level.removeEntity(this);
	}

	public abstract int damage();
}
