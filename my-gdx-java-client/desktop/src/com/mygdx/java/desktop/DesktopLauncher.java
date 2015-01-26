package com.mygdx.java.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.java.MyGdxJava;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.useGL30 = true;
		config.width = 960;
		config.height = 600;
		config.resizable = true;
 
		new LwjglApplication(new MyGdxJava(), config);
	}
}
