package com.anthonyb.metaboyjam.level.entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.event.KeyEvent;

import com.anthonyb.metaboyjam.level.menu.DeathMenu;
import com.anthonyb.metaboyjam.statics.Sounds;
import com.anthonyb.metaboyjam.statics.Sprites;
import com.anthonyb.nohp.Game;
import com.anthonyb.nohp.GameSettings;
import com.anthonyb.nohp.Screen;
import com.anthonyb.nohp.io.Keyboard;
import com.anthonyb.nohp.level.Level;
import com.anthonyb.nohp.level.entity.Camera;
import com.anthonyb.nohp.level.entity.Entity;
import com.anthonyb.nohp.level.entity.PostRenderable;
import com.anthonyb.nohp.math.Vector2D;
import com.anthonyb.nohp.texture.SpriteAnimation;
import com.anthonyb.nohp.util.Timer;

public class Player extends Mob implements PostRenderable {

	static final int MAX_HEALTH = 50;

	private int score = 0;
	private int shares = 0;
	private int speed = 4;
	private int health = MAX_HEALTH;
	private int bonkTimer = 0;

	private SpriteAnimation playerAnim = new SpriteAnimation(4, false, Sprites.angelAnim);
	private SpriteAnimation damageTintAnim = new SpriteAnimation(4, false, Sprites.damageTintAnim);

	private int lastFloor = 0;
	public static final int JUMP_TIME = GameSettings.maxTPS / 2;
	private boolean grounded = false;
	int bonkMultiplier = 0;
	private int jumpTimer = 0;
	private int maxHoleInARow = 0;
	private Timer hitTimer = new Timer();
	private Timer scoreTimer = new Timer();

	public Player() {
		super(Sprites.angelAnim[0]);
		super.cdx1 = 6;
		super.cdx2 = -9;
		super.cdy1 = 3;
//		super.cdy2 = -4;
		super.position.set(0, 0);
		super.persistent = true;
		this.scoreTimer.set(1 * GameSettings.maxTPS);
	}

	public void tick() {
		this.hitTimer.tick();
		this.scoreTimer.tick();
		if (this.scoreTimer.ready()) {
			this.score += 10;
			this.scoreTimer.set(1 * GameSettings.maxTPS);
		}
		if (this.bonkTimer > 0) {
			this.bonkTimer--;
		}
		Vector2D moveDir = new Vector2D(1f, 1.25f);

		if (this.jumpTimer >= 0.85 * JUMP_TIME) {
			System.out.println("C");
			moveDir.y -= 3f;
//			moveDir.y -= 2.4f;
		} else if (this.jumpTimer >= 0.5 * JUMP_TIME) {
			moveDir.y -= 2.4f;
//			moveDir.y -= 1.8f;
		} else if (this.jumpTimer >= 0.1 * JUMP_TIME) {
			System.out.println("B");
			moveDir.y -= 1.8f;
//			moveDir.y -= 1.65f;
		} else if (this.jumpTimer > 0) {
			System.out.println("A");
			moveDir.y -= 1.65f;
//			moveDir.y -= 1f;
		}
		if (this.jumpTimer > 0) {
			this.jumpTimer--;
		}
		if (Keyboard.getPressed(KeyEvent.VK_W) && this.jumpTimer == 0 && this.grounded) {
//			movement.y -= 1.5f;
			this.jumpTimer = JUMP_TIME;
		}
		if (Keyboard.getPressed(KeyEvent.VK_X)) {
			GameSettings.engineDebugMode = true;
//			super.position.y = 0;
//			this.health = MAX_HEALTH;
		}
		if (Keyboard.getPressed(KeyEvent.VK_C)) {
			GameSettings.engineDebugMode = false;
		}
		if (Keyboard.getPressed(KeyEvent.VK_A)) {
//			moveDir.x -= 0.25f;
			moveDir.x -= 0.4f;
		}
//		if (this.grounded) {
//			this.running = Keyboard.getPressed(KeyEvent.VK_D);
//		}
//		if (this.running) {
		if (Keyboard.getPressed(KeyEvent.VK_D)) {
//			moveDir.x += 0.7f;
			moveDir.x += 0.75f;
//			moveDir.x += 0.75f;
		}

		this.grounded = super.isGrounded();
		if (this.grounded) {
			this.bonkMultiplier = 0;
			moveDir.y = Math.min(moveDir.y, 0);
		}

//		Floor spawner
		Level level = Game.instance.level;
		moveDir = moveDir.multiply(this.speed);
		super.position.move(moveDir);
		int divResult;
		if ((divResult = this.getMidXi() / 150) > this.lastFloor) {
			this.lastFloor = divResult;
//			It would be nice to have larger holes but I had to set max holes to 1 because I moved the player from the
//			center of the camera to slightly offset to the right so there is more opportunity to get hit by a bullet.
//			Since it's harder to react to larger holes, they are disabled (previously they were limited to 2).
			if (Math.random() <= 0.88 || this.maxHoleInARow >= 1) { // was 0.9
				System.out.println("SPAWN FLOOR");
				level.addEntity(new Floor(divResult * 150 + 3 * 150, 125));
				this.maxHoleInARow = 0;
			} else {
				System.out.println("SPAWN HOLE");
				this.maxHoleInARow++;
			}
		}

//		Check Enemy Collisions
		for (Entity entity : Game.instance.level.getEntities_DIRECTLY()) {
			if (entity instanceof Enemy) {
				Enemy enemy = (Enemy) entity;
				if (super.intersects(enemy)) {
					if (enemy instanceof DRS) {
						this.shares++;
						this.score += 100;
						Sounds.DRS.play();
						this.damage(enemy.damage());
						enemy.kill();
						continue; // Disallow bonking and doing the damage again for DRS coins.
					}
//					was: 10, then i increased gravity to 1.25 so it's now 13 (10 * 1.25 is 12.5 so 13 should account for pixel round errors).
					if (moveDir.y > 0 && super.getMaxY() - 13 <= enemy.getMinY()) {
//						Must be moving down
//						And must be stepping on the top part of the enemy head
						this.grounded = true; // Allow running after bonking for a single frame.
						if (Keyboard.getPressed(KeyEvent.VK_W)) {
//							If you're also pressing W, perform a bonk jump!
							this.jumpTimer = JUMP_TIME;
							this.bonkTimer = (int) (0.65 * GameSettings.maxTPS);
							Sounds.BONK.play();
							this.bonkMultiplier++;
						} else {
							Sounds.GOOMBA_DEATH.play();
						}
						enemy.kill();
						if (enemy instanceof Goomba) {
							((Goomba) enemy).doSquishAnim();
						}
						this.score += 25;
					} else {
						this.damage(enemy.damage());
					}
				}
			}
		}

//		Move camera
		Camera camera = level.camera;
		if (super.position.x > camera.position.x) {
			camera.position.x = super.position.x - 69; // nice.
		}

//		Check for pit death
		if (super.getMinY() >= camera.getMaxY()) {
			this.die();
		}

		this.playerAnim.tick();
		this.damageTintAnim.tick();
		super.sprite = this.playerAnim;
	}

	public void damage(int damageAmt) {
		if (this.hitTimer.ready()) {
			// 0.4s of invulnerability.
			this.health -= damageAmt;
			if (damageAmt > 0) {
				this.hitTimer.set((int) (0.4 * GameSettings.maxTPS));
			}
			if (this.health <= 0) {
				this.die();
			} else if (damageAmt > 0) {
				Sounds.HURT.play();
			}
		}
	}

	public void die() {
		System.out.println("PLAYER DIED");
		Game.instance.level = new DeathMenu(this.score, this.shares, Game.instance.level.camera);
	}

	@Override
	public void render() {
		super.render();
		if (!this.hitTimer.ready()) {
//			Render damage tint
			Composite original = Screen.g.getComposite();
			Composite translucent = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			Screen.g.setComposite(translucent);
			Screen.sprite(this.damageTintAnim.getSprite()).start(super.bounds.getMinX(), super.bounds.getMinY())
					.dimensions(super.getWidth(), super.getHeight()).selfRotation(super.getSelfRotation()).draw();
			Screen.g.setComposite(original);
		}
	}

	@Override
	public void engineDebugRender() {
		super.engineDebugRender();
		Screen.text("GROUNDED:" + this.grounded).start(50, 50).color(Color.BLACK).draw();
	}

	static final Color DRS_COL = new Color(0xFF93186C);
	static final Font UI_FONT = new Font("Verdana", 1, 8);
	static final Font BONK_FONT = new Font("Verdana", 1, 40);

	@Override
	public void postRender() {
		Camera camera = Game.instance.level.camera;
		int cameraX = camera.getMinXi();
		int cameraY = camera.getMinYi();
		int maxHealthWidth = 50;
		int healthWidth = (int) ((this.health / (double) MAX_HEALTH) * maxHealthWidth);
		if (this.health > MAX_HEALTH) {
			maxHealthWidth = healthWidth;
		}
		Screen.rect().start(super.getMidXi() - cameraX - maxHealthWidth / 2, super.getMinYi() - cameraY - 10)
				.dimensions(maxHealthWidth, 5).color(Color.RED).fill(false).draw();
		Screen.rect().start(super.getMidXi() - cameraX - maxHealthWidth / 2, super.getMinYi() - cameraY - 10)
				.dimensions(healthWidth, 5).color(this.health > MAX_HEALTH ? DRS_COL : Color.GREEN).fill(true).draw();
		Screen.text("Coins: " + this.shares).start(super.getMidXi() - cameraX, super.getMinYi() - cameraY - 15)
				.font(UI_FONT).color(Color.BLACK).draw();
		Screen.text("Score: " + this.score).start(super.getMidXi() - cameraX, super.getMinYi() - cameraY - 25)
				.font(UI_FONT).color(Color.BLACK).draw();

		if (this.bonkTimer > 0) {
			String bonkText = "BONK";
			if (this.bonkMultiplier > 1) {
				bonkText += " x" + this.bonkMultiplier;
			} else {
				bonkText += "!";
			}
			Screen.text(bonkText).start(GameSettings.width / 2 - 4, 50 + (this.bonkTimer * 1f) - 4).font(BONK_FONT)
					.color(Color.BLACK).draw();
			Screen.text(bonkText).start(GameSettings.width / 2 - 2, 50 + (this.bonkTimer * 1f) - 2).font(BONK_FONT)
					.color(this.bonkTimer % 26 > 13 ? DRS_COL : Color.WHITE).draw();
			Screen.text(bonkText).start(GameSettings.width / 2, 50 + (this.bonkTimer * 1f)).font(BONK_FONT)
					.color(this.bonkTimer % 26 > 13 ? Color.WHITE : DRS_COL).draw();
		}
	}
}
