package com.forsrc.client;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;

public interface ForClient {
    public void start() throws Exception;

    public void stop() throws Exception;

    public IoConnector getIoConnector();

    public ConnectFuture getConnectFuture();
}
