package com.mygdx.java.client.handler;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mygdx.java.common.data.Message;

public class ForClientIoHandler implements IoHandler {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ForClientIoHandler.class);

	@Override
	public void exceptionCaught(IoSession iosession, Throwable throwable)
			throws Exception {
		LOGGER.error("exceptionCaught | " + getAddress(iosession) + " | "
				+ iosession.getId() + " | " + throwable.getMessage());
		iosession.close(true);
	}

	@Override
	public void messageReceived(IoSession iosession, Object obj)
			throws Exception {
		LOGGER.debug("messageReceived | " + getAddress(iosession) + " | "
				+ iosession.getId() + " | " + obj);

		LOGGER.info("--->" + (obj instanceof Message));
		if (obj instanceof Message) {
			LOGGER.info("messageReceived | " + getAddress(iosession) + " | "
					+ iosession.getId() + " | Message | " + obj);
			Message message = (Message) obj;

			if (message.getType() == Message.QUIT) {
				iosession.write(new Message(iosession.getId(), Message.QUIT,
						"ClientQuitOK".getBytes()));
				LOGGER.info("ClientQuitOK");
				iosession.close(true);
				return;
			}
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(message.toString());

			System.out.println("---->" + message + " sha1 = "
					+ jsonNode.path("sha1"));
		}
	}

	@Override
	public void messageSent(IoSession iosession, Object obj) throws Exception {
		LOGGER.debug("messageSent | " + iosession.getId() + " | " + obj);
		if (obj instanceof Message) {
			LOGGER.info("messageReceived | " + getAddress(iosession) + " | "
					+ iosession.getId() + " | Message | " + obj);
		}
	}

	@Override
	public void sessionClosed(IoSession iosession) throws Exception {
		LOGGER.debug("sessionClosed | " + getAddress(iosession) + " | "
				+ iosession.getId() + " | ");
		iosession.close(true);
	}

	@Override
	public void sessionCreated(IoSession iosession) throws Exception {
		LOGGER.debug("sessionCreated | " + getAddress(iosession) + " | "
				+ iosession.getId() + " | ");

	}

	@Override
	public void sessionIdle(IoSession iosession, IdleStatus idlestatus)
			throws Exception {
		LOGGER.debug("sessionIdle | " + iosession.getId() + " | " + idlestatus);
		iosession.close(true);
	}

	@Override
	public void sessionOpened(IoSession iosession) throws Exception {
		LOGGER.debug("sessionOpened | " + getAddress(iosession) + " | "
				+ iosession.getId() + " | ");

	}

	private String getAddress(IoSession iosession) {
		InetSocketAddress inetSocketAddress = (InetSocketAddress) iosession
				.getRemoteAddress();
		return inetSocketAddress.toString();
	}

	@Override
	public void inputClosed(IoSession iosession) throws Exception {
		LOGGER.debug("inputClosed | " + getAddress(iosession) + " | "
				+ iosession.getId() + " | ");
		iosession.close(true);
	}

}
