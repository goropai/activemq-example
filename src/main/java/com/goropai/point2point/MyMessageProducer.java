package com.goropai.point2point;

import jakarta.jms.*;

public class MyMessageProducer {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = JMSConnectionFactory.getConnectionFactory();
        try (Connection connection = connectionFactory.createConnection();
             Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {

            connection.start();
            Queue queue = session.createQueue("myqueue");
            MessageProducer producer = session.createProducer(queue);
            TextMessage textMessage = session.createTextMessage("Test message from class: " +
                    MyMessageProducer.class.getSimpleName());
            producer.send(textMessage);
            System.out.println("Message sent: " + textMessage.getText());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
