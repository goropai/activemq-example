package com.goropai.topic;

import jakarta.jms.*;

public class MyMessageSender {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = JMSConnectionFactory.getConnectionFactory();
        try (Connection connection = connectionFactory.createConnection();
             Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);) {

            connection.start();
            Queue queue = session.createQueue("myqueue");
            MessageProducer producer = session.createProducer(queue);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            TextMessage textMessage = session.createTextMessage("Test message from class: " +
                    MyMessageSender.class.getName());
            producer.send(textMessage);
            System.out.println("Queue message sent: " + textMessage.getText());

            Topic topic = session.createTopic("mytopic");
            MessageProducer topicProducer = session.createProducer(topic);
            TextMessage message = session.createTextMessage("Test message from class: " +
                    MyMessageSender.class.getName());
            topicProducer.send(message);
            System.out.println("Topic message sent: " + message.getText());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
