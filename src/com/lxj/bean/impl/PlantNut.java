package com.lxj.bean.impl;

import java.util.ArrayList;

import org.cocos2d.nodes.CCSpriteFrame;

import com.lxj.bean.DefancePlant;


public class PlantNut extends DefancePlant {
	private static ArrayList<CCSpriteFrame> shakeFrames;// “°ªŒ–Ú¡–÷°

	public PlantNut() {
		super("image/plant/nut/p_3_01.png");
		// TODO Auto-generated constructor stub

	}

	@Override
	public void BaseAction() {
		// TODO Auto-generated method stub
		this.runAction(com.lxj.tool.CommonUtil.getRepeatAnimation(shakeFrames, 11,
				"image/plant/nut/p_3_%02d.png"));
	}

}
