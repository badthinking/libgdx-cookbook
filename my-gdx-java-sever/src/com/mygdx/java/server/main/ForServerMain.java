package com.mygdx.java.server.main;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;

import com.mygdx.java.server.ForServer;
import com.mygdx.java.server.handler.ForServerIoHandler;
import com.mygdx.java.server.impl.ForServerImpl;
import com.mygdx.java.utils.IoFilterChainBuilderUtils;

public class ForServerMain {
	public static void main(String[] args) throws Exception {
		ForServer server = new ForServerImpl(new InetSocketAddress(7577));
		DefaultIoFilterChainBuilder chain = server.getIoAcceptor()
				.getFilterChain();
		//IoFilterChainBuilderUtils.addServerSslFilter(chain);

		IoFilterChainBuilderUtils.addObjectSerializationCodec(chain);
		// IoFilterChainBuilderUtils.addCodec(chain);

		server.getIoAcceptor().getSessionConfig().setReadBufferSize(1024 * 512);
		//server.getIoAcceptor().getSessionConfig()
			//	.setIdleTime(IdleStatus.BOTH_IDLE, 10);
		server.getIoAcceptor().setHandler(new ForServerIoHandler());
		server.start();

	}
}
