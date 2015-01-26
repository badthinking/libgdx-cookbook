package com.mygdx.java.common.data;

import java.io.Serializable;

public class GdxData implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 5575904227988185881L;

	private long id;
	private byte[] bytes;
	private long time;

	public GdxData(long id, byte[] bytes, long time) {
		super();
		this.id = id;
		this.bytes = bytes;
		this.time = time;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getBytes() {
		return this.bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public long getTime() {
		return this.time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		if (bytes == null) {
			bytes = new byte[0];
		}
		return String.format("id = %s, time = %s, length = %s", id, time,
				bytes.length);
	}
}
