package com.anthonyb.metaboyjam.level.entity;

import com.anthonyb.metaboyjam.statics.Sprites;
import com.anthonyb.nohp.util.UpDownInt;

public class DRS extends Enemy {

	private int origY;
	private UpDownInt udi;
	
	public DRS(int x, int y) {
		super(Sprites.drs, x, y);
		this.udi = new UpDownInt(1, -10, +10);
		this.origY = y;
	}

	@Override
	public void tick() {
		this.udi.tick();
		super.position.x += super.runSpeed;
		super.position.y = this.origY + this.udi.get();
	}
	
	@Override
	public int damage() {
		return -5;
	}
}
