package com.mygdx.game;

public interface ComClient
{
		public void connectClient (String ip);
		
		public boolean sendMsg(String msg);

		public boolean isConnected();

		public int getId();
		
		public void close();
}