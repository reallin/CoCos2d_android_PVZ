package com.lxj.bean.impl;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

import com.lxj.bean.Bullet;

/**
 * 普通豌豆
 * 
 * @author Administrator
 * 
 */
public class CommonPease extends Bullet {

	public CommonPease() {
		super("image/fight/bullet.png");
		setScale(0.65);
		speed = 100;
		attack = 10;
	}

	@Override
	public void move() {
		CGPoint pos = CGPoint.ccp(
				CCDirector.sharedDirector().getWinSize().width - 20,
				this.getPosition().y);
		// 起点
		// 终点：平移（x轴修改）

		float t = CGPointUtil.distance(getPosition(), pos) / speed;
		CCMoveTo moveTo = CCMoveTo.action(t, pos);

		this.runAction(CCSequence.actions(moveTo,
				CCCallFunc.action(this, "destroy")));

	}



	@Override
	public void BaseAction() {
		// TODO Auto-generated method stub
		
	}




}
