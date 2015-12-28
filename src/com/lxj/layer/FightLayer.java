package com.lxj.layer;

import java.util.ArrayList;
import java.util.List;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import android.view.MotionEvent;


import com.lxj.base.BaseLayer;
import com.lxj.bean.Plant;
import com.lxj.control.GameController;
import com.lxj.sql.ShowPlant;
import com.lxj.tool.CommonUtil;

public class FightLayer extends BaseLayer {
	public static final int TAG_CHOSE = 10;
	private CCTMXTiledMap map;
	private CCSprite ready;
	private List<ShowPlant> chosePlantList;
	private CCSprite choseContainer;
	public FightLayer() {
		// TODO Auto-generated constructor stub
		init();
		/*CCSprite sprite = CCSprite.sprite("image/welcome.jpg");
		this.addChild(sprite);*/
	}

	private void init() {
		// TODO Auto-generated method stub
		engine.playSound(getContext(), com.lxj.zhiwuvsani.R.raw.day, true);
		loadMap();
		loadContain();
		ready();
	}
	private ShowPlant getSprite(int num){
		ShowPlant plant1 = new ShowPlant(num);
		CGPoint pos = CGPoint.ccp(
				(75 + chosePlantList.size() * 53)*0.65f,
				cgSize.height - 65*0.65);
		// moveto动作
		CCMoveTo moveTo = CCMoveTo.action(0.3f, pos);
		plant1.getDefaultImg().runAction(moveTo);
		return plant1;
	}
	private void loadContain() {
		// TODO Auto-generated method stub
		chosePlantList = new ArrayList<ShowPlant>();
		//默认只添加两种植物
		
		chosePlantList.add(getSprite(1));
		chosePlantList.add(getSprite(4));
		
		choseContainer = CCSprite.sprite("image/fight/chose/fight_chose.png");
		choseContainer.setAnchorPoint(0, 1);
		choseContainer.setPosition(0, cgSize.height);
		this.addChild(choseContainer,0,TAG_CHOSE);
		for (ShowPlant item : chosePlantList) {
			CCSprite plant = item.getDefaultImg();
			
			plant.setScale(0.65);
			// plant.setAnchorPoint(0, 0.5f);
			this.addChild(plant,1);
		}

		// 玩家已有植物容器进行缩放（包含所有已经选择的植物信息）
		choseContainer.setScale(0.65f);
	}

	private void ready(){
		//加载第一张图片
		ready = CCSprite.sprite("image/fight/startready_01.png");
		ready.setAnchorPoint(0.5f,0.5f);
		ready.setPosition(cgSize.width/2,cgSize.height/2);
		//播放开始序列帧
		CCSequence ccSequence = CCSequence.actions(CommonUtil.getAnimation(null, 3, "image/fight/startready_%02d.png"),CCCallFunc.action(this, "go"));
		ready.runAction(ccSequence);
		this.addChild(ready);
	}
	//加载地图
	private void loadMap() {
		// TODO Auto-generated method stub
		map = CCTMXTiledMap.tiledMap("image/fight/map_day.tmx");
		map.setAnchorPoint(0.5f,0.5f);
		CGSize contentSize = map.getContentSize();
		//左移右移一半
		map.setPosition(contentSize.width/2,contentSize.height/2);
		this.addChild(map);
	}
	//开始游戏
	public void go(){
		ready.removeSelf();
		this.setIsTouchEnabled(true);
		GameController.getInstance().start(map, chosePlantList);
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		// TODO Auto-generated method stub
		if(GameController.getInstance().isStart()){
			GameController.getInstance().handlerTouch(event);
		}
		return super.ccTouchesBegan(event);
	}
	
}
