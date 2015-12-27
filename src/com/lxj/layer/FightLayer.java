package com.lxj.layer;

import java.util.ArrayList;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGSize;

import com.lxj.tool.CommonUtil;
import com.lxj.zhiwuvsani.BaseLayer;

public class FightLayer extends BaseLayer {
	private CCTMXTiledMap map;
	private CCSprite ready;
	public FightLayer() {
		// TODO Auto-generated constructor stub
		init();
		/*CCSprite sprite = CCSprite.sprite("image/welcome.jpg");
		this.addChild(sprite);*/
	}

	private void init() {
		// TODO Auto-generated method stub
		loadMap();
		ready();
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
		
	}
}
