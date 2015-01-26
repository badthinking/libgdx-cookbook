package com.mygdx.java.client.handler;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.mygdx.java.common.data.GdxData;
import com.mygdx.java.common.data.Message;

public class ForClientIoHandler implements IoHandler {


	@Override
	public void exceptionCaught(IoSession iosession, Throwable throwable)
			throws Exception {
		System.out.println("exceptionCaught | " + getAddress(iosession) + " | "
				+ iosession.getId() + " | " + throwable.getMessage());
		iosession.close(true);
	}

	@Override
	public void messageReceived(IoSession iosession, Object obj)
			throws Exception {
		System.out.println("messageReceived | " + getAddress(iosession) + " | "
				+ iosession.getId() + " | " + obj);

		System.out.println("--->" + (obj instanceof Message));
		if (obj instanceof Message) {
			System.out.println("messageReceived | " + getAddress(iosession) + " | "
					+ iosession.getId() + " | Message | " + obj);
			Message message = (Message) obj;

			if (message.getType() == Message.QUIT) {
				iosession.write(new Message(iosession.getId(), Message.QUIT,
						"ClientQuitOK".getBytes()));
				System.out.println("ClientQuitOK");
				iosession.close(true);
				return;
			}
		}
		if (obj instanceof GdxData) {
			GdxData gdxData = (GdxData) obj;
			System.out.println("---->" + gdxData);
		}
	}

	@Override
	public void messageSent(IoSession iosession, Object obj) throws Exception {
		System.out.println("messageSent | " + iosession.getId() + " | " + obj);
		if (obj instanceof Message) {
			System.out.println("messageReceived | " + getAddress(iosession) + " | "
					+ iosession.getId() + " | Message | " + obj);
		}
	}

	@Override
	public void sessionClosed(IoSession iosession) throws Exception {
		System.out.println("sessionClosed | " + getAddress(iosession) + " | "
				+ iosession.getId() + " | ");
		iosession.close(true);
	}

	@Override
	public void sessionCreated(IoSession iosession) throws Exception {
		System.out.println("sessionCreated | " + getAddress(iosession) + " | "
				+ iosession.getId() + " | ");

	}

	@Override
	public void sessionIdle(IoSession iosession, IdleStatus idlestatus)
			throws Exception {
		System.out.println("sessionIdle | " + iosession.getId() + " | " + idlestatus);
		iosession.close(true);
	}

	@Override
	public void sessionOpened(IoSession iosession) throws Exception {
		System.out.println("sessionOpened | " + getAddress(iosession) + " | "
				+ iosession.getId() + " | ");

	}

	private String getAddress(IoSession iosession) {
		InetSocketAddress inetSocketAddress = (InetSocketAddress) iosession
				.getRemoteAddress();
		return inetSocketAddress.toString();
	}

	@Override
	public void inputClosed(IoSession iosession) throws Exception {
		System.out.println("inputClosed | " + getAddress(iosession) + " | "
				+ iosession.getId() + " | ");
		iosession.close(true);
	}

}
