package com.lxj.layer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cocos2d.actions.CCScheduler;

import android.R.integer;

import com.lxj.base.BaseElement;
import com.lxj.base.BaseLayer;
import com.lxj.bean.AttackPlant;
import com.lxj.bean.Bullet;
import com.lxj.bean.DefancePlant;
import com.lxj.bean.Plant;
import com.lxj.bean.Zombies;
import com.lxj.zhiwuvsani.DieListener;

public class FightLine extends BaseLayer implements DieListener {
	private int lineNum;
	private List<Zombies> zombiesList;// 当前行添加的僵尸集合

	private Map<Integer, Plant> plants;// 当前行添加的植物集合
	private List<AttackPlant> attackPlantList;// 当前行添加的攻击型植物

	public FightLine(int lineNum) {
		super();
		this.lineNum = lineNum;
		zombiesList = new ArrayList<Zombies>();

		plants = new HashMap<Integer, Plant>();
		attackPlantList = new ArrayList<AttackPlant>();
		// 僵尸攻击植物
				// 每隔一个时间，判断当前行是否有僵尸，判断当前行是否有植物，将所有僵尸循环，僵尸的脚下是否有植物
				CCScheduler.sharedScheduler()
						.schedule("attackPlant", this, 0.5f, false);

				// 每隔一个时间，判断当前行是否有僵尸，判断当前行是否有攻击植物，将所有攻击型植物循环，攻击僵尸
				CCScheduler.sharedScheduler().schedule("attackZombies", this, 0.5f,
						false);

				// 每隔一个时间，判断当前行是否有僵尸，判断当前行是否有攻击植物，获取到所有攻击型植物的弹夹，判断所有子弹，是否击中僵尸
				CCScheduler.sharedScheduler()
						.schedule("checkBullet", this, 0.2f, false);
}
	//添加，如果成功返回true
	public void addPlant(Plant plant){
		plant.setDieListener(this);
			plants.put(plant.getRow(), plant);
			//如果是攻击型的，还要加入attackPlantList
			if(plant instanceof AttackPlant){
				attackPlantList.add((AttackPlant)plant);
			}

	}
	//攻击僵尸
	public void attackZombies(float t){
		if (zombiesList.size() > 0 && attackPlantList.size() > 0) {
			for (AttackPlant item : attackPlantList) {
				item.createBullet();
			}
		}
	}
	//子弹打僵尸
	public void checkBullet(float t){
		if(zombiesList.size()>0&&attackPlantList.size()>0){
			for(AttackPlant attackPlant:attackPlantList){
				List<Bullet> bullets = attackPlant.getBullets();//获取子弹数，不同植物有不同子弹
				for(Bullet bullet :bullets){
					for(Zombies zombies :zombiesList){
						float left = zombies.getPosition().x-20;
						float right = zombies.getPosition().x +20;
						float position = bullet.getPosition().x;
						//取一定范围，这是防止抽样0.2s导致错过一些判断
						if(left<position&&position<right){
							zombies.attacked(bullet.getAttack());
							bullet.destroy();
						}
					}
				}
			}
		}
	}
	public boolean isAdd(Plant plant){
		return !plants.containsKey(plant.getRow());
	}
	public void addZombies(Zombies zombies){
		zombiesList.add(zombies);
		zombies.setDieListener(this);
	}
	//僵尸攻击植物
	public void attackPlant(float t){
		if(plants.size()>0&&zombiesList.size()>0){
			for(Zombies zombies :zombiesList){
				int key = (int)zombies.getPosition().x/46- 1;
				Plant plant = plants.get(key);
				if (plant != null) {
					zombies.attack(plant);
				}
			}
		}
	}
	@Override
	public void onDie(BaseElement element) {
		// TODO Auto-generated method stub
		if (element instanceof Plant) {
			int key = ((Plant) element).getRow();
			plants.remove(key);

			if (element instanceof AttackPlant) {
				attackPlantList.remove(element);
			}
		} else if(element instanceof Zombies){
			zombiesList.remove(element);
		}
	}
}
