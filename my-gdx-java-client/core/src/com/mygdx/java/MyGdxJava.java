/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 */

package com.mygdx.java;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.GeneralSecurityException;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.PropertiesUtils;
import com.mygdx.java.client.ForClient;
import com.mygdx.java.client.handler.ForClientIoHandler;
import com.mygdx.java.client.impl.ForClientImpl;
import com.mygdx.java.common.data.GdxData;
import com.mygdx.java.common.data.Message;
import com.mygdx.java.constant.Constants;
import com.mygdx.java.utils.ImageUtils;
import com.mygdx.java.utils.IoFilterChainBuilderUtils;

public class MyGdxJava extends Game {

	SpriteBatch batch;
	TextureRegion region;
	ForClient client;

	@Override
	public void create() {
		Gdx.app.postRunnable(new Runnable() {

			@Override
			public void run() {
				try {
					start();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		/*
		 * FileHandle fileHandle = Gdx.files.internal(Constants.CONFIG);
		 * 
		 * try { PropertiesUtils.load(Constants.PROPERTIES,
		 * fileHandle.reader()); } catch (IOException e) { e.printStackTrace();
		 * }
		 * 
		 * Gdx.app.setLogLevel(Application.LOG_DEBUG);
		 * 
		 * System.out.println(Constants.PROPERTIES.get("log.level"));
		 */
		batch = new SpriteBatch();

		region = new TextureRegion();
		region.setRegion(new Texture("badlogic.jpg"));

		batch = new SpriteBatch();
		getData();
		Gdx.input.setInputProcessor(new InputProcessor() {

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer,
					int button) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer,
					int button) {
				getData();
				return true;
			}

			@Override
			public boolean scrolled(int amount) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyDown(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// getData();
		batch.begin();
		batch.draw(region, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		batch.end();

	}

	@Override
	public void resize(int width, int height) {
		getData();
	}

	private void getData() {
		if (client == null) {
			return;
		}
		Gdx.app.postRunnable(new Runnable() {

			@Override
			public void run() {
				client.getConnectFuture()
						.getSession()
						.write(new Message(System.currentTimeMillis(),
								Message.GET_DATA, "Test get image".getBytes()));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	private void start() throws Exception {
		client = new ForClientImpl(new InetSocketAddress("192.168.2.100", 7577));
		// client.getIoConnector().setConnectTimeoutMillis(1000 * 2);
		DefaultIoFilterChainBuilder chain = client.getIoConnector()
				.getFilterChain();
		//IoFilterChainBuilderUtils.addClientSslFilter(chain);

		IoFilterChainBuilderUtils.addObjectSerializationCodec(chain);

		// IoFilterChainBuilderUtils.addCodec(chain);
		client.getIoConnector().setHandler(new IoHandlerAdapter() {
			@Override
			public void messageReceived(IoSession session, Object message)
					throws Exception {
				if (message instanceof GdxData) {
					GdxData gdxData = (GdxData) message;
					System.out.println("---->" + gdxData);
					ImageUtils.setScreenTextureRegionInThread(region,
							gdxData.getBytes());
				}
			}
		});
		client.getIoConnector().getSessionConfig()
				.setReadBufferSize(1024 * 512);
		client.getIoConnector().getSessionConfig()
				.setIdleTime(IdleStatus.BOTH_IDLE, 2);
		client.start();
		client.getConnectFuture().getSession().setAttribute("Type", "MSG");
		client.getConnectFuture().getSession().write("Test2 OK");

	}

	@Override
	public void dispose() {
		region.getTexture().dispose();
		batch.dispose();
		try {
			client.shutdown();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
