package com.lxj.layer;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCTintBy;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.ccColor3B;


import com.lxj.tool.CommonUtil;
import com.lxj.zhiwuvsani.BaseLayer;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.ViewDebug.FlagToString;

public class WelComeLayer extends BaseLayer {
	private static final int TAG_START = 0;
	
	private CCSprite bar= CCSprite.sprite("image/loading/loading_01.png");
	private volatile CCSprite startGame =  CCSprite.sprite("image/loading/loading_start.png");
	private boolean isClick = false;
	public WelComeLayer(){
		consumeTime();
		init();
	}

	/**
	 * 耗时工作：主要工作检查版本信息
	 */
	private void consumeTime() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				// 耗时操作：访问网络，版本检测，预加载图片，预加载声音文件
				SystemClock.sleep(5000);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				setIsTouchEnabled(true);
				bar.removeSelf();

				startGame.setVisible(true);
				super.onPostExecute(result);
			}

		}.execute();

	}

	private void init() {
		// TODO Auto-generated method stub
		splash();
	}


	private void splash() {
		// TODO Auto-generated method stub
		CCSprite loading = CCSprite.sprite("image/popcap_logo.png");
		loading.setPosition(cgSize.width/2,cgSize.height/2);//居中显示
		this.addChild(loading);
		//显示1s，0.5s后删除
		CCSequence ccSequence = CCSequence.actions( CCDelayTime.action(1),CCHide.action(),CCDelayTime.action(0.5F),CCCallFunc.action(this, "login"));
		loading.runAction(ccSequence);
	}
	//注意一定要为public,具体可看CCCallFunc源码

	public void login(){
		CCSprite bg = CCSprite.sprite("image/background.png");
		bg.setAnchorPoint(0, 0);
		this.addChild(bg);
		//设置标题
		CCLabel cLabel = CCLabel.makeLabel("植物大战僵尸", "Roboto_Bold.ttf", 35);//创建字体，中间参数为ttf文件，20为字体大小
		cLabel.setColor(ccColor3B.ccc3(255,255, 255));//初始值
		cLabel.setPosition(cgSize.width/2,220);
		this.addChild(cLabel);
		setName("林晓佳", 170f);
		setName("柯登科", 130f);
		//设置进度条
		bar  = CCSprite.sprite("image/loading/loading_01.png");
		bar.setPosition(cgSize.width/2, 25);
		this.addChild(bar);
		//以下是序列帧
		ArrayList<CCSpriteFrame> frames = new ArrayList<CCSpriteFrame>();
		String fileName = "image/loading/loading_%02d.png";//用占位符表示图片尾部，格式为两位数，如01，11。
		for (int num = 1; num <= 9; num++) {
			frames.add(CCSprite.sprite(String.format(fileName, num))
					.displayedFrame());
		}

		CCAnimation animation = CCAnimation.animation("", 0.2f, frames);
		CCAnimate animate = CCAnimate.action(animation, false);// 参数二：永不停止的序列帧播放（true），如果只想播放一次（false）
		bar.runAction(animate);
		
		startGame = CCSprite.sprite("image/loading/loading_start.png");
		startGame.setPosition(cgSize.width / 2, 25);
		startGame.setVisible(false);
		this.addChild(startGame, 0, TAG_START);
		
	
	}
	
	//设置名字
	public void setName(String name,float y){
			//改变颜色幅度
			ccColor3B c = ccColor3B.ccc3(0,-100,0);
			CCTintBy tintBy = CCTintBy.action(1, c);
			CCLabel cLabel = CCLabel.makeLabel(name, "Roboto_Thin.ttf", 20);//创建字体，中间参数为ttf文件，20为字体大小
			cLabel.setColor(ccColor3B.ccc3(255,228, 0));//初始值
			cLabel.setPosition(cgSize.width/2,y);
			
			CCSequence ccSequence = CCSequence.actions(tintBy, tintBy.reverse());
			CCRepeatForever ccRepeatForever = CCRepeatForever.action(ccSequence);
			this.addChild(cLabel,1);
			cLabel.runAction(ccRepeatForever);
		
	}
	/**
	 * 加载背景音乐
	 */
	private void loadBGM() {
		engine.playSound(getContext(), com.lxj.zhiwuvsani.R.raw.start, true);
	}
//设置按钮点击跳转到游戏页
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		if(CommonUtil.isClicke(event, this, this.getChildByTag(TAG_START))){
			engine.playEffect(getContext(), com.lxj.zhiwuvsani.R.raw.onclick);
			CCScene scene = CCScene.node();
			scene.addChild(new FightLayer());
			CCFadeTransition transition = CCFadeTransition.transition(0.5F, scene);
			
			//替换场景
			CCDirector.sharedDirector().replaceScene(transition);
			
		}
		return super.ccTouchesEnded(event);
	}
	
	
}
