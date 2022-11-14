package com.anthonyb.metaboyjam.level.entity;

import com.anthonyb.metaboyjam.statics.Sprites;
import com.anthonyb.nohp.Game;
import com.anthonyb.nohp.texture.SpriteAnimation;

public class Flamethrower extends Enemy {

	private SpriteAnimation flamethrowerAnim = new SpriteAnimation(4, false, Sprites.flamethrowerAnim);

	public Flamethrower(int x, int y) {
		super(Sprites.flamethrowerAnim[0], x, y);
		super.position.zIndex = 1;
		super.sprite = flamethrowerAnim;
	}

	@Override
	public void tick() {
		super.cdx1 = 35;
		super.cdy1 = 16;
		super.cdy2 = -9;
		this.flamethrowerAnim.tick();
		if (super.getMinX() >= Game.instance.level.camera.getMaxX()
				|| super.getMinY() >= Game.instance.level.camera.getMaxY()) {
			System.out.println("KILL ENEMY");
			this.kill();
		}
	}

	@Override
	public int damage() {
		return 15;
	}
}
