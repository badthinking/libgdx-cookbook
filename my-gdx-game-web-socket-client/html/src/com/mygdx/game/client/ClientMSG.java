package com.mygdx.game.client;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;

public class ClientMSG {
	ComClient cc;

	// For Bidirectional Communication mode
	public ClientMSG(String ip, int port) {
		if (Gdx.app.getType() == ApplicationType.WebGL) {
			// Only available on the HTML project - ClientMSG class
			cc = new GWTClient(ip, port, this);
		} else {
			// Only available on the JAVA-ANDROID project
			// cc = new WSClient(ip, port, this);
		}

	}

	public void onMessage(String message) {

		String[] values = message.split("\\s+"); // splitter with the " "
													// separator

		// int ClientID = Integer.valueOf(values[0]); //Check of the ID (not
		// required)

		// Calls to the upper level class methods
		if (values[0].equals("POSITION")) // POSITION player_id pos_x pos_y
		{
			HTML5Client.changePosition(Integer.valueOf(values[1]),
					Integer.valueOf(values[2]), Integer.valueOf(values[3]));
		}
	}

	public boolean sendMessage(String message) {
		if (cc != null && cc.isConnected()) {
			// Only send message
			return (cc.sendMsg(message));
		} else
			return false;
	}

	public int getId() {
		return (cc.getId());
	}

	// get name from client class

	// one method for each messages / actions that the client can do

	public void close() {
		cc.close();
	}
}