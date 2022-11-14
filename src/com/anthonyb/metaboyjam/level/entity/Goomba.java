package com.anthonyb.metaboyjam.level.entity;

import com.anthonyb.metaboyjam.statics.Sounds;
import com.anthonyb.metaboyjam.statics.Sprites;
import com.anthonyb.nohp.Game;
import com.anthonyb.nohp.GameSettings;
import com.anthonyb.nohp.texture.Sprite;
import com.anthonyb.nohp.texture.SpriteAnimation;
import com.anthonyb.nohp.util.Timer;

public class Goomba extends Enemy {

	private SpriteAnimation goombaAnim = new SpriteAnimation(4, false, Sprites.goombaAnim);
	private SpriteAnimation devilGoombaAnim = new SpriteAnimation(4, false, Sprites.devilGoombaAnim);
	private SpriteAnimation projectileEnemyAnim = new SpriteAnimation(4, false, Sprites.galaxyAnim);

	private Flamethrower flamethrower;

	private enum Gun {
		NONE, FLAMETHROWER, PROJECTILE;
	};

	private Gun gun;

	private Timer shootTimer;

	public Goomba() {
		super(Sprites.goombaAnim[0], Game.instance.level.camera.getMinXi() - Sprites.goombaAnim[0].getWidth(), 60);
		super.sprite = this.goombaAnim;
		if (Math.random() <= .18) {
			super.cdx1 = 8;
			super.cdx2 = -23;
			super.sprite = this.projectileEnemyAnim;
			Sprite currentSprite = this.projectileEnemyAnim.getSprite();
			super.position.x = Game.instance.level.camera.getMinXi() - currentSprite.getWidth();
			super.width = currentSprite.getWidth();
			super.height = currentSprite.getHeight();
			this.shootTimer = new Timer();
			this.shootTimer.set((int) (0.25 * GameSettings.maxTPS + Math.random() * 0.5 * GameSettings.maxTPS));
			this.gun = Gun.PROJECTILE;
		} else if (Math.random() <= .40) {
			super.sprite = this.devilGoombaAnim;
			Sprite currentSprite = this.devilGoombaAnim.getSprite();
			super.position.x = Game.instance.level.camera.getMinXi() - currentSprite.getWidth();
			super.width = currentSprite.getWidth();
			super.height = currentSprite.getHeight();
			this.shootTimer = new Timer();
			this.flamethrower = new Flamethrower(super.getMidXi() + 6, super.getMidYi());
			Game.instance.level.addEntity(this.flamethrower);
			this.gun = Gun.FLAMETHROWER;
		} else {
			this.gun = Gun.NONE;
			this.cdx2 -= 10;
		}
	}

	public void tick() {
		if (this.gun == Gun.PROJECTILE) {
			this.projectileEnemyAnim.tick();
			this.shootTimer.tick();
//			Make sure the Goomba is at least halfway on the screen so the player knows there's potential for a bullet.
			if (super.getMidXi() >= Game.instance.level.camera.getMinXi() && this.shootTimer.ready()) {
//				Shoot bullet
				Sounds.BULLET.play();
				Game.instance.level
						.addEntity(new Bullet(super.getMidXi() + 28, super.getMidYi() - 7, super.runSpeed + 3));
				this.shootTimer.set((int) (0.5 * GameSettings.maxTPS + Math.random() * 0.75 * GameSettings.maxTPS));
			}
		} else if (this.gun == Gun.FLAMETHROWER) {
			this.devilGoombaAnim.tick();
		} else {
			this.goombaAnim.tick();
		}
		super.tick();
		if (this.gun == Gun.FLAMETHROWER) {
			this.flamethrower.position.set(super.getMidXi() +14, super.getMidY()); // was 6
		}
	}

	public void onDie() {
		if (this.gun == Gun.FLAMETHROWER) {
			this.flamethrower.onDie();
			Game.instance.level.removeEntity(this.flamethrower);
		}
	}

	public void doSquishAnim() {
		SpriteAnimation anim;
		if (this.gun == Gun.FLAMETHROWER) {
			anim = this.devilGoombaAnim;
		} else if (this.gun == Gun.PROJECTILE) {
			anim = this.projectileEnemyAnim;
		} else {
			anim = this.goombaAnim;
		}
		Game.instance.level.addEntity(new SquishDeathAnim(super.getMidXi(), super.getMidYi(), anim));
	}

	public int damage() {
		if (this.gun == Gun.PROJECTILE) {
			return 5;
		}
		return 10;
	}
}
