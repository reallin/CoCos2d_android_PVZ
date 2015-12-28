package com.lxj.bean;

import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import com.lxj.base.BaseElement;
/**
 * 僵尸的基类
 * @author linxj
 *
 */
public abstract class Zombies extends BaseElement{
	
	protected int speed = 10;//速度
	protected int attack = 10;//攻击力量
	protected int life = 50;//生命力

	protected CGPoint startPoint;// 起点
	protected CGPoint endPoint;// 终点

	public Zombies(String filepath) {
		
		super(filepath);
		
	}

	/**
	 * 移动
	 */
	public abstract void move();

	/**
	 * 攻击
	 * 
	 * @param element:攻击植物，攻击僵尸
	 */
	public abstract void attack(BaseElement element);

	/**
	 * 被攻击
	 */
	public abstract void attacked(int attack);
}
