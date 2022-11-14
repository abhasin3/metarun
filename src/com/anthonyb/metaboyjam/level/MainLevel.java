package com.anthonyb.metaboyjam.level;

import com.anthonyb.metaboyjam.level.entity.DRS;
import com.anthonyb.metaboyjam.level.entity.Floor;
import com.anthonyb.metaboyjam.level.entity.Goomba;
import com.anthonyb.metaboyjam.statics.Sprites;
import com.anthonyb.nohp.Game;
import com.anthonyb.nohp.GameSettings;
import com.anthonyb.nohp.Screen;
import com.anthonyb.nohp.level.Level;
import com.anthonyb.nohp.level.Background.EmptyBackground;
import com.anthonyb.nohp.level.entity.Camera;
import com.anthonyb.nohp.texture.SpriteAnimation;
import com.anthonyb.nohp.util.Timer;

public class MainLevel extends Level {

	private SpriteAnimation backgroundSprite;

	private Timer spawnTimer;

	public MainLevel(Camera camera) {
		super(camera, new EmptyBackground());

		this.backgroundSprite = new SpriteAnimation(7, false, Sprites.bgAnim);
//		Setup initial floors
		super.addEntity(new Floor(0, 125));
		super.addEntity(new Floor(150, 125));
		super.addEntity(new Floor(300, 125));
		super.addEntity(new Floor(450, 125));

		this.spawnTimer = new Timer();
		this.spawnTimer.set(GameSettings.maxTPS);
	}

	public void tick() {
		this.backgroundSprite.tick();
		super.tick();
		this.spawnTimer.tick();
		if (this.spawnTimer.ready()) {
			System.out.println("SPAWN GOOMBA");
			Game.instance.level.addEntity(new Goomba());
//			Spawn between [0.5, 1.5) seconds
			this.spawnTimer.set((int) (0.5 * GameSettings.maxTPS + Math.random() * GameSettings.maxTPS));
			if (Math.random() < 0.11) { // 11% chance of spawning a coin
				Game.instance.level
						.addEntity(new DRS(Game.instance.level.camera.getMinXi() - (int) (4 * Math.random()) * Sprites.drs.getWidth(),
								(int) (Game.instance.level.camera.getMidYi() + (Math.random() * 100 - 25))));
			}
		}
	}

	public void render() {
		Screen.sprite(this.backgroundSprite).start(0, 0).dimensions(500, 300).draw();
		super.render();
	}
}
