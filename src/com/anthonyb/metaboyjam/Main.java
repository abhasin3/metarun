package com.anthonyb.metaboyjam;

import com.anthonyb.metaboyjam.level.menu.MainMenu;
import com.anthonyb.nohp.Game;
import com.anthonyb.nohp.GameSettings;
import com.anthonyb.nohp.level.entity.Camera;

import kuusisto.tinysound.TinySound;

public class Main {

	public static void main(String[] args) {
		GameSettings.windowTitle = "Metarun";
		TinySound.init();
		GameSettings.width = 500;
		GameSettings.height = 300;
		GameSettings.scale = 2;
		GameSettings.maxTPS = 60;
		Game.create();
		Game.instance.level = new MainMenu(new Camera());
		Game.instance.start();
	}
}
