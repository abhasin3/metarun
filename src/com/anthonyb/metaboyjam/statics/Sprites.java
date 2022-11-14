package com.anthonyb.metaboyjam.statics;

import com.anthonyb.nohp.texture.Sprite;

public class Sprites {

	private static final Sprite GALAXY_SHEET = new Sprite("galaxy_anim.png"),
			ANGEL_SHEET = new Sprite("angel_anim.png"), DAMAGE_TINT_SHEET = new Sprite("player_damage_tint.png"),
			GOOMBA_SHEET = new Sprite("goomba.png"), DEVIL_GOOMBA_SHEET = new Sprite("devil_goomba.png"),
			FLAMETHROWER_SHEET = new Sprite("flamethrower.png"), BG_SHEET = new Sprite("background_anim.png");
//	40 x 50
	public static final Sprite fudAnim1 = new Sprite(31, 29, 40, 50, GALAXY_SHEET),
			fudAnim2 = new Sprite(31 + 103, 29, 40, 50, GALAXY_SHEET),
			fudAnim3 = new Sprite(31 + 2 * 103, 29, 40, 50, GALAXY_SHEET),
			fudAnim4 = new Sprite(31 + 3 * 103, 29, 40, 50, GALAXY_SHEET),
			fudAnim5 = new Sprite(31 + 4 * 103, 29, 40, 50, GALAXY_SHEET), floor = new Sprite("floor.png"),
			bullet = new Sprite("bullet.png"), drs = new Sprite("drs.png");
//			bg1 = new Sprite(0 * 100, 0 * 100, 100, 100, BG_SHEET);

	public static Sprite[] bgAnim = new Sprite[12];

	public static Sprite[] angelAnim = new Sprite[12];
	public static Sprite[] damageTintAnim = new Sprite[12];
	public static Sprite[] goombaAnim = new Sprite[12];
	public static Sprite[] devilGoombaAnim = new Sprite[12];
	public static Sprite[] flamethrowerAnim = new Sprite[12];
	public static Sprite[] galaxyAnim = new Sprite[12];
	static {
		for (int i = 0; i < bgAnim.length; i++) {
			bgAnim[i] = new Sprite(i * 100, 0, 100, 68, BG_SHEET);
			int x5 = i % 5;
			int y5 = i / 5;
			angelAnim[i] = new Sprite(x5 * 100 + 29, y5 * 100 + 27, 100 - 29 - 23, 100 - 27 - 24, ANGEL_SHEET);
			damageTintAnim[i] = new Sprite(x5 * 100 + 29, y5 * 100 + 27, 100 - 29 - 23, 100 - 27 - 24,
					DAMAGE_TINT_SHEET);
			goombaAnim[i] = new Sprite(x5 * 100 + 34, y5 * 100 + 27, 100 - 34 - 23, 100 - 27 - 24, GOOMBA_SHEET);
			devilGoombaAnim[i] = new Sprite(x5 * 100 + 34, y5 * 100 + 27, 100 - 34 - 33, 100 - 27 - 24,
					DEVIL_GOOMBA_SHEET);
//			goombaAnim[i] = new Sprite(x5 * 100 + 28, y5 * 100 + 27, 100 - 28 - 0, 100 - 27 - 24, GOOMBA_SHEET);
			flamethrowerAnim[i] = new Sprite(x5 * 100 + 28, y5 * 100 + 27, 100 - 28 - 0, 100 - 27 - 24,
					FLAMETHROWER_SHEET);
			galaxyAnim[i] = new Sprite(x5 * 100 + 29, y5 * 100 + 27, 100 - 29 - 11, 100 - 27 - 24, GALAXY_SHEET);
		}
	}
//	public static final Sprite[] fudAnim = { fudAnim1, fudAnim2, fudAnim3, fudAnim4, fudAnim5 };
//	public static final SpriteAnimation fudAnim = new SpriteAnimation(15, false, fudAnim1, fudAnim2, fudAnim3, fudAnim4, fudAnim5);
}
