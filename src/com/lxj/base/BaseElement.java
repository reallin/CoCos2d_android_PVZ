package com.lxj.base;

import org.cocos2d.nodes.CCSprite;

import com.lxj.zhiwuvsani.DieListener;

public abstract class BaseElement extends CCSprite{
	DieListener dieListener;
	public void setDieListener(DieListener dieListener){
		this.dieListener = dieListener;
	}
	public BaseElement(String filepath) {
		super(filepath);
		this.setAnchorPoint(0.5f,0);
		this.setScale(0.5f);
		
	}
	public abstract void BaseAction();
		
	public void destroy(){
		if(dieListener != null){
			dieListener.onDie(this);
		}
		this.removeSelf();
	}
}
