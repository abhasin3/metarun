package com.anthonyb.metaboyjam.statics;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

public class Sounds {

	public static final Sound BULLET = TinySound.loadSound("/bullet.wav"),
			DRS = TinySound.loadSound("/drs.wav"),
			HURT = TinySound.loadSound("/hurt.wav"),
			SELECT = TinySound.loadSound("/select.wav"),
			BONK = TinySound.loadSound("/bonk.wav"),
			GOOMBA_DEATH = TinySound.loadSound("/goomba_death.wav");
}
