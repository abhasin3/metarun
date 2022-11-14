package com.anthonyb.metaboyjam.level.entity;

import com.anthonyb.nohp.Game;
import com.anthonyb.nohp.Screen.SpriteBuilder;
import com.anthonyb.nohp.level.entity.Camera;
import com.anthonyb.nohp.level.entity.ImageEntity;
import com.anthonyb.nohp.level.entity.CameraRotatable.RotationMode;
import com.anthonyb.nohp.math.Bounds.CameraView;
import com.anthonyb.nohp.texture.SpriteAnimation;

public class SquishDeathAnim extends ImageEntity {

	int sizeModifier;

	public SquishDeathAnim(int x, int y, SpriteAnimation spriteAnim) {
		super(spriteAnim);
		super.position.set(x, y);
		this.sizeModifier = 0;
	}

	@Override
	public void tick() {
		this.sizeModifier += 3;
		if (this.sizeModifier >= super.sprite.getHeight()) {
			Game.instance.level.removeEntity(this);
			System.out.println("REMOVE SQUISHDEATHANIM");
		}
	}
	
	@Override
	public void render() {
		new SpriteBuilder(super.sprite).start(super.bounds.getMinX(), super.bounds.getMinY() + this.sizeModifier)
				.dimensions(super.getWidth(), super.getHeight() - this.sizeModifier).selfRotation(super.getSelfRotation()).draw();
	}
}
