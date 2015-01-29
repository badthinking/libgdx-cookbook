package com.mygdx.java.common.data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

public class Message implements Serializable {

	public static final int QUIT = 1;
	public static final int CLIENT_GET_DATA = 2;
	public static final int CLIENT_SEND_DATA = 3;
	public static final int SERVER_GET_DATA = 4;
	public static final int SERVER_SEND_DATA = 5;

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = -3012829182440549266L;

	private long id;
	private int type;
	private byte[] data;
	private long time;
	private String info;

	public Message(long id, int type, byte[] data) {
		this.id = id;
		this.type = type;
		this.data = data;
		this.time = System.currentTimeMillis();
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

	public long getTime() {
		return this.time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {

		// return String
		// .format("{id = \"%s\", type = \"%s\", length = \"%s\", time = \"%s\", info = \"%s\"}",
		// this.id, this.type, this.data == null ? 0
		// : this.data.length, this.time,
		// this.info == null ? "" : info);
		ObjectNode objectNode = new ObjectNode(JsonNodeFactory.instance);

		Date date = new Date(this.time);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		objectNode.put("date", simpleDateFormat.format(date));
		objectNode.put("id", this.id);
		objectNode.put("type", this.time);
		objectNode.put("length", this.data == null ? 0 : this.data.length);
		objectNode.put("sha1", new String(DigestUtils.sha1Hex(data)));
		objectNode.put("time", this.time);
		objectNode.put("info", this.info);

		return objectNode.toString();
	}

}
