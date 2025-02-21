package com.goropai.topic;

import jakarta.jms.*;

public class MyMessageReceiver {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = JMSConnectionFactory.getConnectionFactory();
        try (Connection connection = connectionFactory.createConnection();
             Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {

            connection.setClientID("UniqueClientID");
            connection.start();

            Queue queue = session.createQueue("myqueue");
            MessageConsumer consumer = session.createConsumer(queue);
            Message message = consumer.receive();
            if (message instanceof TextMessage textMessage) {
                System.out.println("Received queue message: " + textMessage.getText());
            }

            Topic topic = session.createTopic("mytopic");
//            MessageConsumer topicConsumer = session.createConsumer(topic);
            MessageConsumer topicConsumer = session.createDurableSubscriber(topic, "mytopic-subscriber");
            Message message2 = topicConsumer.receive();
            if (message instanceof TextMessage textMessage2) {
                System.out.println("Received topic message: " + textMessage2.getText());
            }

            Thread.sleep(3000);//pause for durable subscriber
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
