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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {

	public static BufferedImage getScreenBufferedImage() {
		BufferedImage image = null;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screen = new Rectangle(dim);
		try {
			Robot robot = new Robot();
			image = robot.createScreenCapture(screen);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return image;
	}

	public static byte[] getScreenBufferedImageBytes() {
		return getBufferedImageBytes(getScreenBufferedImage());
	}

	public static byte[] getBufferedImageBytes(BufferedImage bufferedImage) {

		if (bufferedImage == null) {
			throw new IllegalArgumentException(
					"Input the BufferedImage is null.");
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] bytes = null;
		try {
			ImageIO.write(bufferedImage, "png", out);
			out.flush();
			bytes = out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return bytes;
	}

	public static BufferedImage imageBytes2BufferedImage(byte[] bytes) {
		BufferedImage image = null;
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		try {
			image = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public static void main(String[] args) {
		BufferedImage bufferedImage = imageBytes2BufferedImage(getScreenBufferedImageBytes());
		try {
			ImageIO.write(bufferedImage, "png",new File( "test/test.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
