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
import java.util.logging.Logger;

public class RegisterEventListenerProviderFactory implements EventListenerProviderFactory {

    static ConnectionFactory factory = new ConnectionFactory();
    static Connection connection;
    static Channel channel;
    static RegisterEventListenerProvider registerEventListenerProvider;
    static Logger logger = Logger.getLogger("RegisterEventListenerProvider");

    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {

        String host = System.getenv("RABBITMQ.HOST");
        String userName = System.getenv("RABBITMQ.USER");
        String password = System.getenv("RABBITMQ.PASSWORD");

        logger.info("Getting RabbitMQ Env Variables ...");

        logger.info("Host Value : " + host);
        logger.info("UserName Value : " + userName);
        logger.info("Password Value : " + password);

        factory.setUsername(userName != null ? userName : "guest");
        factory.setPassword(password != null ? password : "guest");
        factory.setHost(host != null ? host : "rabbitmq");

        logger.info("Finishing Load Env Variables RabbitMQ.");

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
