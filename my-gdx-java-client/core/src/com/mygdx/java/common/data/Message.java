package com.mygdx.java.common.data;

import java.io.Serializable;

public class Message implements Serializable {

	public static final int QUIT = 0;
	public static final int GET_DATA = 0;
	public static final int SEND_DATA = 0;

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = -3012829182440549266L;

	private long id;
	private int type;
	private byte[] data;

	public Message(long id, int type, byte[] data) {
		super();
		this.id = id;
		this.type = type;
		this.data = data;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public byte[] getData() {
		return this.data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
