package com.lansoft.fs.esl.config;

import com.lansoft.fs.esl.InboundListener;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.internal.IModEslApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
public class ClientConfig {
    @Autowired
    InboundListener listener;
    @Bean
    public Client inboudClient() throws Exception{
        Client inboudClient = new Client ();
        inboudClient.connect(new InetSocketAddress("localhost", 8021), "ClueCon", 10);
        inboudClient.addEventListener(listener);
        inboudClient.setEventSubscriptions(IModEslApi.EventFormat.PLAIN,"ALL");
        return inboudClient;
    }
}
