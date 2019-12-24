package com.ly.demo.mq.consumer.activemq.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author xinre, created on 2019/12/23
 */
public class MyTopicMessageListener01 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                String text = textMessage.getText();
                System.out.println("topic messageListener01 text = " + text);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
