package com.anthonyb.metaboyjam.level.menu;

import java.awt.Color;
import java.awt.Font;

import com.anthonyb.metaboyjam.statics.Sounds;
import com.anthonyb.nohp.Game;
import com.anthonyb.nohp.GameSettings;
import com.anthonyb.nohp.Screen;
import com.anthonyb.nohp.level.Level;
import com.anthonyb.nohp.level.Background.EmptyBackground;
import com.anthonyb.nohp.level.entity.Camera;
import com.anthonyb.nohp.ui.Button;
import com.anthonyb.nohp.ui.UIElementBackground.BasicUIBackground;

public class HelpMenu extends Level {

	public HelpMenu(Camera camera) {
		super(camera, new EmptyBackground());
		Button backToMainMenu = new Button(GameSettings.width / 2, GameSettings.height / 2 + 120, 200, 40,
				new BasicUIBackground(Color.BLACK).withBorder(Color.WHITE, 1).withText("Back to Main Menu",
						Color.ORANGE, new Font("Verdana", 0, 15))) {
			@Override
			public void onLeftClick() {
				Sounds.SELECT.play();
				Game.instance.level = new MainMenu(Game.instance.level.camera);
			}
		};
		this.addEntity(backToMainMenu);
	}

	static final Font INFO_FONT = new Font("Verdana", 0, 12);

	static final String[] TEXT = { "Controls:", "W - Jump", "A - Run slower", "D - Run faster",
			"Don\'t forget, Metaboys are always running to the right!", "",
			"Story: Minting is broken and totally out of control!",
			"Try to survive as the newly minted Metaboys come to destroy", "the OG gang!", "",
			"Objective: Survive as long as you can,", "and pickup coins while you\'re at it!", "", "Helpful Info:",
			"BONK: If you hold W (Jump) while squishing an enemy / bullet, you will bounce!",
			"Coins will heal 5 HP! Collect them all :)" };

	@Override
	public void render() {
		super.render();
		for (int i = 0; i < TEXT.length; i++) {
			if (TEXT[i].isEmpty())
				continue;
			Screen.text(TEXT[i]).font(INFO_FONT).start(GameSettings.width / 2, 15 + i * 15).color(Color.WHITE).draw();
		}

	}
}
