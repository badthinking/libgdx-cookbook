/*******************************************************************************
 * Copyright 2013 Andreas Oehlke
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


package com.packtpub.libgdx.canyonbunny.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CameraHelper {

	private static final String TAG = CameraHelper.class.getName();

	private final float MAX_ZOOM_IN = 0.25f;
	private final float MAX_ZOOM_OUT = 10.0f;

	private Vector2 position;
	private float zoom;
	private Sprite target;

	public CameraHelper () {
		position = new Vector2();
		zoom = 1.0f;
	}

	public void update (float deltaTime) {
		if (!hasTarget()) return;

		position.x = target.getX() + target.getOriginX();
		position.y = target.getY() + target.getOriginY();
	}

	public void setPosition (float x, float y) {
		this.position.set(x, y);
	}

	public Vector2 getPosition () {
		return position;
	}

	public void addZoom (float amount) {
		setZoom(zoom + amount);
	}

	public void setZoom (float zoom) {
		this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
	}

	public float getZoom () {
		return zoom;
	}

	public void setTarget (Sprite target) {
		this.target = target;
	}

	public Sprite getTarget () {
		return target;
	}

	public boolean hasTarget () {
		return target != null;
	}

	public boolean hasTarget (Sprite target) {
		return hasTarget() && this.target.equals(target);
	}

	public void applyTo (OrthographicCamera camera) {
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}

}
