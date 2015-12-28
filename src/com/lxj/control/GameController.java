package com.lxj.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;

import android.util.Log;
import android.view.MotionEvent;

import com.lxj.bean.Plant;
import com.lxj.bean.impl.PeacePlant;
import com.lxj.bean.impl.PlantNut;
import com.lxj.bean.impl.PrimaryZombies;
import com.lxj.layer.FightLayer;
import com.lxj.layer.FightLine;
import com.lxj.sql.ShowPlant;
import com.lxj.tool.CommonUtil;

public class GameController {
	private static GameController instance;
	private CCTMXTiledMap map;
	private FightLayer layer;
	private boolean isStart = false;
	private ArrayList<ShowPlant> chosePlantList;
	private CGPoint[][] towers = new CGPoint[5][9];
	
	private ShowPlant currentShowPlant;// 展示用植物：
	private Plant currentPlant;
	private static List<FightLine> fightLines;
static{
	fightLines = new ArrayList<FightLine>();
	for (int lineNum = 0; lineNum <= 4; lineNum++) {
		FightLine line = new FightLine(lineNum);
		fightLines.add(line);
}
}
	private GameController(){
		
	}
	public void start(CCTMXTiledMap gameMap, List<ShowPlant> chosePlantList){
		isStart = true;
		this.map = gameMap;
		this.layer = (FightLayer)gameMap.getParent();
		this.chosePlantList =	(ArrayList<ShowPlant>)chosePlantList;
		loadPlantPos();
		CCScheduler.sharedScheduler().schedule("addZombies", this, 5, false);
		//addZombies(1f);
	}
	public static GameController getInstance(){
		if(instance == null){
			synchronized (GameController.class) {
				instance = new GameController();
			}
		}
		return instance;
	}
	/**
	 * 解析安放植物的二维数组
	 */
	private void loadPlantPos() {
		for (int i = 1; i <= 5; i++) {
			CCTMXObjectGroup group = map.objectGroupNamed(String.format("tower0%d", i));
			for(int j =0;j < group.objects.size();j++){
				 HashMap<String, String> item= group.objects.get(j);
				 towers[i-1][j] =CGPoint.ccp(Integer.parseInt(item.get("x")), Integer.parseInt(item.get("y")));
				
			}
		}
	}
	public void GameOver(){
		isStart = false;
		Log.i("game info", "game over");
	}
	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}
	//添加僵尸
	public void addZombies(float t){
		Random random = new Random();
		int lineNum = random.nextInt(5);
		int startIndex = lineNum * 2;
		int endIndex = lineNum * 2 + 1;
		
		List<CGPoint> points = CommonUtil.loadPoint(map, "road");
		PrimaryZombies zombies = new PrimaryZombies(points.get(startIndex), points.get(endIndex));
		this.layer.addChild(zombies,1);
		fightLines.get(lineNum).addZombies(zombies);
	}
	
	public void handlerTouch(MotionEvent event){
		if(CommonUtil.isClicke(event, layer, layer.getChildByTag(FightLayer.TAG_CHOSE))){
			for(ShowPlant item:chosePlantList){
				if(CommonUtil.isClicke(event, layer,item.getDefaultImg())){
					currentShowPlant = item;
					currentShowPlant.getDefaultImg().setOpacity(100);
					switch (item.getId()) {
					case 4:
						currentPlant = new PlantNut();
						break;
					case 1:
						currentPlant = new PeacePlant();
						break;

					}
				}
			}
		}else{
			if(currentShowPlant !=null){//只有选了植物才可以放置
			if(checkField(layer.convertTouchToNodeSpace(event))){
				addPlant();
			}
			}
		}
	}
	public boolean checkField(CGPoint touch){
		int row = (int)touch.x/46;
		int line = (int)(CCDirector.sharedDirector().getWinSize().height-touch.y)/54;
		if(row>=1&&row<=9){
			if(line>=1&&line<=5){
				this.currentPlant.setLine(line-1);
				this.currentPlant.setRow(row-1);
				currentPlant.setPosition(towers[line-1][row-1]);
				return true;
			}
		}
		return false;
	}
	public void addPlant(){
		//CGPoint point = towers[this.currentPlant.getLine()][this.currentPlant.getRow()];
		//currentPlant.setPosition(point.x,point.y);
		FightLine fightLine = fightLines.get(this.currentPlant.getLine());
		if(fightLine.isAdd(currentPlant)){
		
		fightLine.addPlant(currentPlant);
		this.layer.addChild(currentPlant);
		currentShowPlant.getDefaultImg().setOpacity(255);
		currentShowPlant = null;
		currentPlant = null;
		}
	}
}
