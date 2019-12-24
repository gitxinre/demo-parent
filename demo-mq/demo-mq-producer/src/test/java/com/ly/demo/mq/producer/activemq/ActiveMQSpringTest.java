package com.ly.demo.mq.producer.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

/**
 * @author xinre, created on 2019/12/23
 */
public class ActiveMQSpringTest {

    @Test
    public void testQueueProducer() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-mq.xml");
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        Destination destination = (Destination) applicationContext.getBean("test-queue");
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("test queue producer spring 001 ......");
                return textMessage;
            }
        });
    }

    @Test
    public void testTopicProducer() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-mq.xml");
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        Destination destination = (Destination) applicationContext.getBean("test-topic");
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("test topic producer spring 001 ......");
                return textMessage;
            }
        });
    }
}
