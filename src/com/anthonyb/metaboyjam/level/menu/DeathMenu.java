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

public class DeathMenu extends Level {

	private int score, drs;

	public DeathMenu(int score, int drs, Camera camera) {
		super(camera, new EmptyBackground());
		this.score = score;
		this.drs = drs;
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

	static final Font SCORE_FONT = new Font("Verdana", 0, 25);
	static final Font DRS_FONT = new Font("Verdana", 0, 10);

	@Override
	public void render() {
		super.render();
		Screen.text("Score: " + this.score).font(SCORE_FONT).start(GameSettings.width / 2, GameSettings.height / 2)
				.color(Color.WHITE).draw();
		Screen.text("Coins (DRS\'d): " + this.drs).font(DRS_FONT)
				.start(GameSettings.width / 2, GameSettings.height / 2 + 30).color(Color.WHITE).draw();
	}
}
