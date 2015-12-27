package com.lxj.zhiwuvsani;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGSize;

import android.app.Activity;

public abstract class BaseLayer extends CCLayer {
	protected CGSize cgSize ;
	protected static SoundEngine engine;
	
	static{
		engine = SoundEngine.sharedEngine();
		engine.preloadSound(getContext(), R.raw.start);
		engine.preloadSound(getContext(), R.raw.day);
		engine.preloadSound(getContext(), R.raw.night);
		engine.preloadSound(getContext(), R.raw.onclick);
	}
	public BaseLayer(){
		cgSize = CCDirector.sharedDirector().getWinSize();
	}
	/**
	 * 获取上下文信息（Activity）
	 * @return
	 */
	protected static Activity getContext() {
		return CCDirector.sharedDirector().getActivity();
	}
}
