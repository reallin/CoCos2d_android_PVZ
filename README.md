# CoCos2d_android_PVZ
植物大战僵尸游戏，会不断的更新。
# 植物大战僵尸游戏
![](https://github.com/reallin/CoCos2d_android_PVZ/blob/master/1.png)
![](https://github.com/reallin/CoCos2d_android_PVZ/blob/master/2.png)
## Cocos2d_android介绍
Cocos2d是一个大家庭，包括Cocos2d-iphone,Cocos2d-x,Cocos2d-javascript等。而在国内，Cocos2d-x则相对领先。在中国的2D手机游戏开发中，Cocos2d-x引擎的份额超过70%。不同家庭成员之间只是语言不同，而实现的接口名称都相同。所以只要学习一个，其它的就都比较好理解了。本文讲的是Coscos2d_android，因为它是用java实现的，所以对我来说学起来比较快。

对于Cocos2d_android只要关注四个部分， 

* CCDirector(导演)，CCScene(场景)，CCLayout(幕布)以及CCSprite(精灵）。我们可以把它当成在拍电影，顾名思义可以看出它们的作用：
* CCDirector：电影中的导演，肯定是负责整部电影拍摄的，它有三个功能，管理CCScene，开线程执行SurfaceView中的绘制行为，设置游戏属性。
* CCScene:电影中的场景，当然包括人和背景。可以理解它是根View，layer都必须建立在它之上，有点类似activity与fragment的关系。
* CCLayer:场景中的部分图层，离用户最近的一层。游戏过程中始终只有一个layer能获得焦点。每个动作都必须建立在layer上。
* CCSprite:精灵，这个可以理解为activity中的一个控件。就是最小的一部分了。平时控制最多的也就是它，所以要重点关注。

## 四大组成部分用法

其它先不说，首先你要用Cocos2d_android，你就应该先把包导进来（可以在我的工程下的libs中找到）。接下来讲解下上面讲的四部分如何在代码里面用。首先是CCDirector，直接看MainActivity中CCDirector的设置代码：
```java
protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CCGLSurfaceView surfaceView = new CCGLSurfaceView(this);
		setContentView(surfaceView);
		director = CCDirector.sharedDirector();
		director.attachInView(surfaceView);//开线程
		director.setScreenSize(480, 320);
		director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);//横屏
		director.setDisplayFPS(true);//不显示帧率
		CCScene scene = CCScene.node();
		scene.addChild(new WelComeLayer());
		//导演管理场景
		director.runWithScene(scene);
	}

	@Override
	protected void onResume() {
		director.onResume();
		super.onResume();
	}

	@Override
	protected void onPause() {
		director.onPause();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		director.end();
		super.onDestroy();
	}
```
 CCGLSurfaceView继承着SurfaceView，由此可见游戏引擎是用SurfaceView做的。CCDirector是一个单例，调用sharedDirector来获取它，保证全局只有一个CCDirector。初始化完后就要开始工作了，首先attachInView与surfaceView连接，感兴趣的可以进去看下attachInView源码，它跟我们平时使用surfaceView很相似，也是开了个线程，然后在幕布上画图像。这里主要是讲使用，就不翻源码了。setDeviceOrientation设置屏幕横屏。setDisplayFPS设置为true时就会在游戏左下角显示帧率，这个只是在开发时使用。最后很关键的就是初始化一个CCScene，并调用runWithScene把场景加进去。这样CCDirector的初始工作就结束了。

接下来看CCScene，它是根View，初始时经过runWithScene加入到CCDirector中，不同游戏界面可以理解为不同layer，切换layer可以理解为切换activity一样，而切换的代码如下，基本可以看成是固定格式的。
```java
    CCScene scene = CCScene.node();
scene.addChild(new FightLayer());
CCFadeTransition transition = CCFadeTransition.transition(0.5F, scene);
	//替换场景
	CCDirector.sharedDirector().replaceScene(transition);
```
transition是一个切换动画，CCFadeTransitiion只是切换动画的一种，具体可以察看api。
好，我们要玩游戏就要有界面，界面画在哪上面？xml，还是activity。那么接下来这位成员就派上它的用场了。layer需要我们自己来实现，它要继承自CCLayer,如以下是自定义的layer：
```java
public class WelComeLayer extends CCLayer {
	public WelComeLayer(){
		init();
	}}
```
只要把你自己的实现写在init()方法中就可以了。什么时候被调用就要看什么时候它被addChild到scene中。layer中就可以处理很多事情，包括地图的加载，音乐的播放，精灵的移动等。这些后面会细讲。最后就是精灵啦。
CCSprite是用的最多的，一个僵尸可以是一个精灵，一张图片一段文字都可以是一个精灵。精灵的加载如下：
```java
choseContainer = CCSprite.sprite("fight_chose.png");
		choseContainer.setAnchorPoint(0, 1);
		choseContainer.setPosition(0, cgSize.height);
		this.addChild(choseContainer,0,1);
```
 这里是把assets下的一张图片当作精灵，setAnchorPoint是设置锚点，（0，1）就是图片的左上角，相当于图片钉在左上角上，移动旋转时它都为中心。setPosition不用说，就是设置精灵的位置啦。然后调用layer的addChild就把它加载进来了。这里它有三个参数，第一个是精灵，第二个是它的层数，0是最底层的，如果想把它置于上层，就给它设置一个值，值越大层数越高，就越不会被覆盖。最后一个参数是tag，设置了它就可以在其它地方通过tag来获取这个精灵，类似于findViewById()，
