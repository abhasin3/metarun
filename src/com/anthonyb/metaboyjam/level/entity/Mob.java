package com.anthonyb.metaboyjam.level.entity;

import java.awt.Color;

import com.anthonyb.nohp.Game;
import com.anthonyb.nohp.Screen;
import com.anthonyb.nohp.level.entity.Entity;
import com.anthonyb.nohp.level.entity.ImageEntity;
import com.anthonyb.nohp.math.Bounds;
import com.anthonyb.nohp.math.Bounds.CameraView;
import com.anthonyb.nohp.math.collision.Colliders;
import com.anthonyb.nohp.texture.Sprite;

public abstract class Mob extends ImageEntity {

	protected int cdx1, cdy1, cdx2, cdy2;

	public Mob(Sprite sprite) {
		super(sprite);
	}

	public boolean intersects(Mob other) {
		return Colliders.intersect(super.bounds, this.cdx1, this.cdy1, this.cdx2, this.cdy2, other.bounds, other.cdx1,
				other.cdy1, other.cdx2, other.cdy2);
	}

	public boolean isGrounded() {
		boolean grounded = false;
		for (Entity entity : Game.instance.level.getEntities_DIRECTLY()) {
			if (entity instanceof Floor) {
				Floor floor = (Floor) entity;
				if (this.intersects(floor)) {
					float delta = super.getMaxY() - floor.getMinY();
					if (delta >= 0f && delta <= 5f) {
//					if (super.getMaxY() >= floor.getMinY()) { // This was really buggy and didn't work well since you could just stand in the middle of the floor. Instead use a 5 pixel region of the top.
						grounded = true;
					}
				}
			}
		}
//		Game.instance.level.getEntities_DIRECTLY().stream().filter(ent -> ent instanceof Floor).forEach(floor -> {
//			if (Colliders.intersect(super.bounds, floor.bounds)) {
//				if (super.getMaxY() >= floor.getMinY()) {
//					grounded = true;
//				}
//			}
//		});
		return grounded;
	}

	public void engineDebugRender() {
		super.engineDebugRender();
		Bounds boundsUnrot = this.bounds.toCameraView(CameraView.UNROTATED_LAYER);

		Screen.rect().start(boundsUnrot.getMinX() + this.cdx1, boundsUnrot.getMinY() + this.cdy1)
				.dimensions((int) (boundsUnrot.getMaxX() + this.cdx2 - (boundsUnrot.getMinX() + this.cdx1)),
						(int) (boundsUnrot.getMaxY() + this.cdy2 - (boundsUnrot.getMinY() + this.cdy1)))
				.color(Color.YELLOW).cameraView(boundsUnrot.getCameraView()).draw();
	}
}
