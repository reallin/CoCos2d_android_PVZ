package com.lxj.zhiwuvsani;


import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import com.lxj.layer.WelComeLayer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {
	private CCDirector director;
	@Override
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
	

}
