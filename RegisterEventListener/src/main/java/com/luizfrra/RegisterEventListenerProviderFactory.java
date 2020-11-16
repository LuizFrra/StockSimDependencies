package com.luizfrra;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RegisterEventListenerProviderFactory implements EventListenerProviderFactory {

    static ConnectionFactory factory = new ConnectionFactory();
    static Connection connection;
    static Channel channel;
    static RegisterEventListenerProvider registerEventListenerProvider;


    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {

        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("rabbitmq");

        try {

            if(connection == null)
                connection = factory.newConnection();
            if(channel == null)
                channel = connection.createChannel();

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

        if(registerEventListenerProvider == null) {
            try {
                channel.queueDeclare("stockSim", true, false, false, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            registerEventListenerProvider = new RegisterEventListenerProvider(channel);
        }

        return registerEventListenerProvider;
    }

    @Override
    public void init(Config.Scope scope) {

    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "Register_Event_Listener";
    }
}
