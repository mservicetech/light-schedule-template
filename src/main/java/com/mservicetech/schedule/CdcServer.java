package com.mservicetech.schedule;

import com.networknt.handler.HandlerProvider;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;

/**
 *  This is entry point to cdc server
 * CdcServer handle
 */
public class CdcServer implements HandlerProvider {
    @Override
    public HttpHandler getHandler() {
        return Handlers.path()
                .addPrefixPath("/", exchange -> exchange.getResponseSender().send("OK!")
                );
    }
}
