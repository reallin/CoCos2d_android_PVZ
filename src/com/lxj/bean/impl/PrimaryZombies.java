package com.lxj.bean.impl;

import java.util.ArrayList;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

import android.R.integer;
import android.R.plurals;

import com.lxj.base.BaseElement;
import com.lxj.bean.Plant;
import com.lxj.bean.Zombies;
import com.lxj.control.GameController;
import com.lxj.tool.CommonUtil;

public class PrimaryZombies extends Zombies{
	private ArrayList<CCSpriteFrame> moveFrams;
	private ArrayList<CCSpriteFrame> attackFrame;
	private boolean isAttack = false;// 记录僵尸的攻击状态
	private BaseElement target;// 记录攻击目标
	private static ArrayList<CCSpriteFrame> headFrame;// 头掉下来
	private static ArrayList<CCSpriteFrame> dieFrame;// 趴着地上
	private static String headRes = "image/zombies/zombies_1/head/z_1_head_%02d.png";
	private static String dieRes = "image/zombies/zombies_1/die/z_1_die_%02d.png";
	
	private boolean isDie = false;

	public PrimaryZombies(CGPoint start, CGPoint end) {
		super("image/zombies/zombies_1/walk/z_1_01.png");
		// TODO Auto-generated constructor stub
		this.startPoint = start;
		this.endPoint = end;
		//从起点开始走
		this.setPosition(start);
		move();
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		//计算速度
		float t = CGPointUtil.distance(startPoint, endPoint)/speed;
		CCMoveTo ccMoveTo = CCMoveTo.action(t, endPoint);
		CCSequence ccSequence = CCSequence.actions(ccMoveTo, CCCallFunc.action(this, "gameOver"));
		this.runAction(ccSequence);
		//边走边动
		this.runAction(CommonUtil.getRepeatAnimation(moveFrams, 7, "image/zombies/zombies_1/walk/z_1_%02d.png"));
	}
	//游戏结束
	public void gameOver(){
		this.removeSelf();
		GameController.getInstance().GameOver();
	}
	
	
	@Override
	public void attack(BaseElement element) {
		// TODO Auto-generated method stub
		if(!isAttack){
			isAttack = true;
			this.target = element;
			this.stopAllActions();
			// 播放攻击序列帧
			this.runAction(CommonUtil.getRepeatAnimation(attackFrame, 10,
								"image/zombies/zombies_1/attack/z_1_attack_%02d.png"));
			CCScheduler.sharedScheduler().schedule("attackPlant", this, 0.5f,
					false);
		}
		
		
	}
	//必须要加参数float t
	public void attackPlant(float t){
		if(target instanceof Plant){
			Plant plant = (Plant) target;
			plant.attacked(attack);
			//植物被干死了
			if(plant.getLife()<0){
				plant.destroy();
				this.stopAllActions();
				this.move();
			}
		}
		
	}

	@Override
	public void attacked(int attack) {
		// TODO Auto-generated method stub
		life -= attack;
	
		if (life <= 0 && !isDie) {
			isDie = true;
			// 脑袋掉下
			// 慢慢的趴在地上
			
			// 脑袋掉下来，慢慢的爬到地上，销毁
			this.stopAllActions();
			CCAnimate head = (CCAnimate) CommonUtil.getAnimation(headFrame, 6,
					headRes);
			CCAnimate die = (CCAnimate) CommonUtil.getAnimation(dieFrame, 6,
					dieRes);
			// 播放僵尸倒下的动画
			this.runAction(CCSequence.actions(head, die,
					CCCallFunc.action(this, "destroy")));
			
		}
	}

	@Override
	public void BaseAction() {
		// TODO Auto-generated method stub
		
	}
}
