package com.forsrc.client.impl;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.forsrc.client.ForClient;
import com.mygdx.java.common.data.Message;

public class ForClientImpl implements ForClient {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ForClientImpl.class);
	private IoConnector connector;
	private InetSocketAddress inetSocketAddress;
	private ConnectFuture connectFuture;

	public ForClientImpl(InetSocketAddress inetSocketAddress) {
		this.connector = new NioSocketConnector();
		this.inetSocketAddress = inetSocketAddress;
	}

	@Override
	public void start() throws Exception {
		LOGGER.debug("Client start...");
		this.connectFuture = this.connector.connect(this.inetSocketAddress);
		this.connectFuture.awaitUninterruptibly();

	}

	@Override
	public void stop() throws Exception {
		LOGGER.debug("Client stop...");
		IoSession ioSession = getIoSession();
		if (ioSession != null) {
			ioSession.getCloseFuture().awaitUninterruptibly();
			if (ioSession.isConnected()) {
				ioSession.write(new Message(ioSession.getId(), Message.QUIT,
						"ClientQuit".getBytes()));
			}
			//
			ioSession.close(true);
			LOGGER.debug("Client stoped");
		}
		this.connector.dispose(true);
	}

	private IoSession getIoSession() {
		return this.connectFuture.getSession();
	}

	@Override
	public IoConnector getIoConnector() {

		return this.connector;
	}

	@Override
	public ConnectFuture getConnectFuture() {

		return this.connectFuture;
	}

}
