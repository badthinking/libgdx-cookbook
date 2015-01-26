package com.mygdx.java.server.handler;

import org.apache.mina.handler.chain.IoHandlerChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.forsrc.client.handler.ForClientIoHandler;

public class ForServerIoHandlerCommand extends IoHandlerChain {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ForClientIoHandler.class);

}
