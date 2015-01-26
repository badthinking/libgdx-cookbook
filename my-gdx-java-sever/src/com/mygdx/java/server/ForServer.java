package com.mygdx.java.server;

import org.apache.mina.core.service.IoAcceptor;

public interface ForServer {
    public void start() throws Exception;

    public void stop() throws Exception;

    public IoAcceptor getIoAcceptor();
}
