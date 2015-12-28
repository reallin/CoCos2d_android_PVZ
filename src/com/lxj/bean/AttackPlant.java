package com.lxj.bean;

import java.util.ArrayList;
import java.util.List;

import com.lxj.base.BaseElement;
import com.lxj.zhiwuvsani.DieListener;


public abstract class AttackPlant extends Plant implements DieListener{
	// 弹夹
	protected List<Bullet> bullets = new ArrayList<Bullet>();
	public AttackPlant(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 生产用于攻击的子弹
	 * 
	 * @return
	 */
	public abstract Bullet createBullet();

	public List<Bullet> getBullets() {
		return bullets;
	}

	@Override
	public void onDie(BaseElement element) {

		if (element instanceof Bullet) {
			bullets.remove(element);
		}
	}
	@Override
	public void BaseAction() {
		// TODO Auto-generated method stub
		
	}
	
}
