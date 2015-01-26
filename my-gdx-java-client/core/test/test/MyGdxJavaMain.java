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

package test;

import java.io.IOException;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.PropertiesUtils;
import com.mygdx.java.constant.Constants;
 

public class MyGdxJavaMain extends Game {

	SpriteBatch batch;
	Stage stage;
	Sprite sprite;
	Image image;
	TextureRegion region;

	@Override
	public void create() {
		FileHandle fileHandle = Gdx.files.internal(Constants.CONFIG);

		try {
			PropertiesUtils.load(Constants.PROPERTIES, fileHandle.reader());
		} catch (IOException e) {
			e.printStackTrace();
		}

		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		System.out.println(Constants.PROPERTIES.get("log.level"));
		stage = new Stage();
		batch = new SpriteBatch();
		sprite = new Sprite();
		image = new Image();
		region = new TextureRegion();

		//ImageUtils.setScreenTextureRegion(region);
		sprite.setScale(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		sprite.setRegion(region);
		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		sprite.draw(batch);
		// batch.draw(region.getTexture(), 0, 0, Gdx.graphics.getWidth(),
		// Gdx.graphics.getHeight());
		batch.end();

	}

	@Override
	public void resize(int width, int height) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//ImageUtils.setScreenTextureRegion(region);
	}
}
