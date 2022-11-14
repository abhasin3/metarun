package com.anthonyb.metaboyjam.level.menu;

import java.awt.Color;
import java.awt.Font;

import com.anthonyb.metaboyjam.level.MainLevel;
import com.anthonyb.metaboyjam.level.entity.Player;
import com.anthonyb.metaboyjam.statics.Sounds;
import com.anthonyb.nohp.Game;
import com.anthonyb.nohp.GameSettings;
import com.anthonyb.nohp.Screen;
import com.anthonyb.nohp.level.Level;
import com.anthonyb.nohp.level.Background.SolidColorBackground;
import com.anthonyb.nohp.level.entity.Camera;
import com.anthonyb.nohp.ui.Button;
import com.anthonyb.nohp.ui.UIElementBackground.BasicUIBackground;

public class MainMenu extends Level {

	public MainMenu(Camera camera) {
		super(camera, new SolidColorBackground(Color.BLACK));
		Button startGameButton = new Button(GameSettings.width / 2, GameSettings.height / 2 + 10, 200, 40,
				new BasicUIBackground(Color.BLACK).withBorder(Color.WHITE, 1).withText("Play", Color.ORANGE,
						new Font("Verdana", 0, 15))) {
			@Override
			public void onLeftClick() {
				Sounds.SELECT.play();
				Level mainLevel = new MainLevel(new Camera());
				Game.instance.level = mainLevel;
				mainLevel.addEntity(new Player());
			}
		};
		Button helpMenuButton = new Button(GameSettings.width / 2, GameSettings.height / 2 + 60, 200, 40,
				new BasicUIBackground(Color.BLACK).withBorder(Color.WHITE, 1).withText("Help", Color.ORANGE,
						new Font("Verdana", 0, 15))) {
			@Override
			public void onLeftClick() {
				Sounds.SELECT.play();
				Game.instance.level = new HelpMenu(Game.instance.level.camera);
			}
		};
		this.addEntity(startGameButton);
		this.addEntity(helpMenuButton);
	}

	@Override
	public void tick() {
		super.tick();
	}

	static final Font TITLE_FONT = new Font("Verdana", 0, 50);

	@Override
	public void render() {
		super.render();
		Screen.text("METARUN").font(TITLE_FONT).start(GameSettings.width / 2, GameSettings.height / 2 - 25)
				.color(Color.WHITE).draw();

	}
}
