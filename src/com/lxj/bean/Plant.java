package com.lxj.bean;

import com.lxj.base.BaseElement;

/**
 * 植物
 * @author Administrator
 *
 */
public abstract class Plant extends BaseElement {

	protected int life=100;// 生命值

	protected int line;// 行号
	protected int row;// 列号

	public Plant(String filepath) {
		super(filepath);
		setScale(0.65);
		setAnchorPoint(0.5f, 0);// 将解析的点位放在两腿之间
	}

	/**
	 * 被攻击
	 * 
	 * @param attack
	 */
	public void attacked(int attack) {
		life -= attack;
		if (life <= 0) {
			destroy();
		}
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getLife() {
		return life;
	}



}
