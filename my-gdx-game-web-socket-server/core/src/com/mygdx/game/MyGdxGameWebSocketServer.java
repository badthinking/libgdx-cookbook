package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class MyGdxGameWebSocketServer implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;

	static final int spWidth = 128;
	static final int spHeight = 256;
	private static int x_card = 0; // To send the coordinates of card
	private static int y_card = 0;
	// position
	private int last_x = 0, last_y = 0; // To store the last sent coordinates

	ServerMSG serverMSG;

	@Override
	public void create() {
		serverMSG = new ServerMSG(8077);
		float w = Gdx.graphics.getWidth(); // Save the Width and Height to use
											// it after
		float h = Gdx.graphics.getHeight();

		// IMPORTANT
		// THE 0,0 POINT OF MY GAME IS THE LEFT BOTTOM CORNER, EVERY OBJECTS
		// WILL USE THIS SYSTEM OF REFERENCE
		//
		camera = new OrthographicCamera(w, h); // Create the camera and put his
												// position on the real 0,0
		camera.position.x = w / 2;
		camera.position.y = h / 2;
		camera.update();

		batch = new SpriteBatch();

		// LETS CREATE THE IMAGE THAT YOU CAN MOVE OVER THE SCREEN
		texture = new Texture(Gdx.files.internal("data/cartajoker.png"));
		TextureRegion region = new TextureRegion(texture, 0, 0, 128, 256); // spWidth,
																			// spHeight);
																			// //must
																			// have
																			// the
																			// same
																			// size
																			// of
																			// the
																			// image
																			// size
		sprite = new Sprite(region);
	}

	@Override
	public void dispose() {
		batch.dispose();
		serverMSG.close();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0.5f, 0, 1); // background color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		sprite.draw(batch);

		if (Gdx.input.isTouched()) // The screen is touched or is clicked for
									// the mouse
		{
			// We need to catch the input, translate his values to the X, Y
			// system of the camera and move the main image
			Vector3 touch = new Vector3();
			touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touch);
			// The center of the card in the touch point
			x_card = (int) touch.x;
			y_card = (int) touch.y;

			if ((Math.abs(last_x - touch.x) >= 3)
					|| (Math.abs(last_y - touch.y) >= 3)) // Only send new
															// values when the
															// position is
															// updated
			{
				serverMSG.sendMessageToAll("POSITION -1 " + (int) touch.x + " "
						+ (int) touch.y);
				last_x = (int) touch.x;
				last_y = (int) touch.y;
			}
		}

		sprite.setPosition((float) (x_card - (spWidth / 2)),
				(float) (y_card - (spHeight / 2))); // if touched, to the touch
													// point, if message
													// received, to the message
													// coordinates point.

		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	// Method called by the ServerMSG (bidirectional) when client moves the card
	public static void changePosition(int PlayerId, int x, int y) {
		x_card = x;
		y_card = y;
	}
}