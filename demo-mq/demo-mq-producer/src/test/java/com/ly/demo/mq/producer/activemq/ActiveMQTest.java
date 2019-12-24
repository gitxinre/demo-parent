package com.ly.demo.mq.producer.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Assert;
import org.junit.Test;

import javax.jms.*;

/**
 * @author xinre, created on 2019/12/23
 */
public class ActiveMQTest {

    @Test
    public void testQueueProducer() throws JMSException {

        String brolerUrl = "tcp://192.168.65.10:61616";
        String destinationName = "test-queue";
        String destinationType = "queue";
        String message = "hello activemq queue 001 ......";
        activeMQProducer(brolerUrl, destinationName, destinationType, message);

    }

    @Test
    public void testTopicProducer() throws JMSException {

        String brolerUrl = "tcp://192.168.65.10:61616";
        String destinationName = "test-topic";
        String destinationType = "topic";
        String message = "hello activemq topic 001 ......";
        activeMQProducer(brolerUrl, destinationName, destinationType, message);

    }

    /**
     * @param brokerUrl       url
     * @param destinationName name
     * @param destinationType type:queue、topic
     * @throws JMSException 异常信息
     */
    private void activeMQProducer(String brokerUrl, String destinationName, String destinationType, String message) throws JMSException {

        Assert.assertNotNull(brokerUrl);
        Assert.assertNotNull(destinationName);
        Assert.assertNotNull(destinationType);

        // jms规范要求步骤，所有q大体都一致

        // 1、创建连接工厂对象，需要指定mq服务的ip与端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);

        // 2、使用connectFactory对象创建connect
        Connection connection = connectionFactory.createConnection();

        // 3、开启连接
        connection.start();

        // 4、创建session
        // 第一个参数是是否开启事务
        // 如果第一个参数为true（开启事务），第二个参数自动忽略
        // 如果第一个参数为false（不开启事务），第二个参数为消息应答模式（分：自动和手动两种）
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 5、使用session对象，创建Destination对象（queue与topic两种形式）
        // 参数就是消息队列的名称，接口Queue继承自Destination接口
        // queue是有持久化的、topic默认没有持久化（可以配置持久化，变成：发布订阅模式）
        Destination destination = null;
        switch (destinationType) {
            case "topic":
                destination = session.createTopic(destinationName);
                break;
            default:
                destination = session.createQueue(destinationName);
                break;
        }
        // 6、使用session对象，创建一个生产者producer对象
        MessageProducer producer = session.createProducer(destination);

        // 7、创建一个TextMessage对象
        /*TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("hello activeMQ ......");*/
        TextMessage textMessage = session.createTextMessage(message);

        // 8、发送消息
        producer.send(textMessage);

        // 9、关闭资源（session、connect等资源）
        producer.close();
        session.close();
        connection.close();
    }
}
