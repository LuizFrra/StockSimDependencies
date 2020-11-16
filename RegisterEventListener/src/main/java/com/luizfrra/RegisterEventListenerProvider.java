package com.luizfrra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizfrra.Models.RegisterDetails;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;

import java.io.IOException;

public class RegisterEventListenerProvider implements EventListenerProvider {

    Channel channel;

    static AMQP.BasicProperties basicProperties;

    static ObjectMapper objectMapper;

    public RegisterEventListenerProvider(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void onEvent(Event event) {

        if(event.getType() == EventType.REGISTER) {

            String realmId = event.getRealmId();
            String clientId = event.getClientId();
            String userId = event.getUserId();
            String email = event.getDetails().getOrDefault("email", "");
            String userName = event.getDetails().getOrDefault("username", "");

            RegisterDetails registerDetails = new RegisterDetails(realmId, clientId, userId, email, userName);

            try {

                if(objectMapper == null) {
                    objectMapper = new ObjectMapper();
                }

                if(basicProperties == null) {
                    basicProperties = new AMQP.BasicProperties()
                            .builder()
                            .contentType("text/plain")
                            .deliveryMode(2)
                            .priority(1)
                            .build();
                }

                byte[] message = objectMapper.writeValueAsBytes(registerDetails);

                channel.basicPublish("", realmId, basicProperties, message);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

    }

    @Override
    public void close() {

    }
}
