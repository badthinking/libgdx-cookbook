package com.mygdx.java.client.main;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;


import com.mygdx.java.client.ForClient;
import com.mygdx.java.client.handler.ForClientIoHandler;
import com.mygdx.java.client.impl.ForClientImpl;
import com.mygdx.java.common.data.Message;
import com.mygdx.java.utils.IoFilterChainBuilderUtils;

public class ForClientMain {
	public static void main(String[] args) throws Exception {
		ForClient client = new ForClientImpl(new InetSocketAddress("127.0.0.1",
				7577));
		// client.getIoConnector().setConnectTimeoutMillis(1000 * 2);
		DefaultIoFilterChainBuilder chain = client.getIoConnector()
				.getFilterChain();
		//IoFilterChainBuilderUtils.addClientSslFilter(chain);

		IoFilterChainBuilderUtils.addObjectSerializationCodec(chain);

		// IoFilterChainBuilderUtils.addCodec(chain);
		client.getIoConnector().setHandler(new ForClientIoHandler());
		client.getIoConnector().getSessionConfig()
				.setReadBufferSize(1024 * 512);
		client.getIoConnector().getSessionConfig()
				.setIdleTime(IdleStatus.BOTH_IDLE, 10);
		client.start();
		client.getConnectFuture().getSession().setAttribute("Type", "MSG");
		client.getConnectFuture().getSession().write("Test2 OK");
		for (int i = 0; i < 20; i++) {
			client.getConnectFuture()
					.getSession()
					.write(new Message(i, Message.CLIENT_GET_DATA,
							"Test get image".getBytes()));
		}

		client.stop();
	}
}
