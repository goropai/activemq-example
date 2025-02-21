package com.goropai.point2point;

import jakarta.jms.*;

public class MyMessageConsumer {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = JMSConnectionFactory.getConnectionFactory();
        try (Connection connection = connectionFactory.createConnection();
             Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {

            connection.start();
            Queue queue = session.createQueue("myqueue");
            MessageConsumer consumer = session.createConsumer(queue);
            Message message = consumer.receive();
            if (message instanceof TextMessage textMessage) {
                System.out.println("Message received: " + textMessage.getText());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
