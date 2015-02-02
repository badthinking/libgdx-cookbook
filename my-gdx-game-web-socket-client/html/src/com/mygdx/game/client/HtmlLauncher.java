package com.mygdx.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.mygdx.game.MyGdxGameWebSocketClient;

public class HtmlLauncher extends GwtApplication {

	@Override
	public GwtApplicationConfiguration getConfig() {
		// return new GwtApplicationConfiguration(480, 320);
		return new GwtApplicationConfiguration(800, 480);
	}

	@Override
	public ApplicationListener getApplicationListener() {
		// return new MyGdxGameWebSocketClient();
		return new HTML5Client();
	}
}