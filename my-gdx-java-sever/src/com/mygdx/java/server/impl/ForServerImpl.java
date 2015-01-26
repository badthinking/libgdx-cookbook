package com.mygdx.java.server.impl;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.mygdx.java.server.ForServer;

public class ForServerImpl implements ForServer {

    private IoAcceptor acceptor;
    private InetSocketAddress inetSocketAddress;

    public ForServerImpl(InetSocketAddress inetSocketAddress) {
        this.acceptor = new NioSocketAcceptor();
        this.inetSocketAddress = inetSocketAddress;
    }

    @Override
    public void start() throws Exception {
        this.acceptor.bind(this.inetSocketAddress);

    }

    @Override
    public void stop() throws Exception {
        this.acceptor.unbind();

    }

    @Override
    public IoAcceptor getIoAcceptor() {

        return this.acceptor;
    }

}
