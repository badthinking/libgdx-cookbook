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
package com.mygdx.java.utils;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ImageUtils {

	public static void setScreenTextureRegion(
			final TextureRegion textureRegion, byte[] bytes) {

		if (textureRegion == null) {
			throw new IllegalArgumentException(
					"Input the TextureRegion is null.");
		}
		Texture tempTexture = textureRegion.getTexture();
		if (tempTexture != null) {
			tempTexture.dispose();
		}

		Pixmap pixmap = new Pixmap(bytes, 0, bytes.length);
		int width = pixmap.getWidth();
		int height = pixmap.getHeight();

		Texture texture = new Texture(width, height, pixmap.getFormat());
		texture.draw(pixmap, 0, 0);
		textureRegion.setTexture(texture);
		textureRegion.setRegion(0, 0, width, height);
		pixmap.dispose();

	}

	public static void setScreenTextureRegionInThread(
			final TextureRegion textureRegion, final byte[] bytes) {

		if (textureRegion == null) {
			throw new IllegalArgumentException(
					"Input the TextureRegion is null.");
		}

		Gdx.app.postRunnable(new Runnable() {

			@Override
			public void run() {

				Texture tempTexture = textureRegion.getTexture();
				if (tempTexture != null) {
					tempTexture.dispose();
					textureRegion.setTexture(null);
				}

				Pixmap pixmap = new Pixmap(bytes, 0, bytes.length);
				int width = pixmap.getWidth();
				int height = pixmap.getHeight();
				//pixmap.setBlending(Blending.None);
				//Texture texture = new Texture(width, height, pixmap.getFormat());
				Texture texture = new Texture(pixmap, true);
				texture.draw(pixmap, 0, 0);
				textureRegion.setTexture(texture);
				textureRegion.setRegion(0, 0, width, height);
				pixmap.dispose();

			}
		});

	}
}
