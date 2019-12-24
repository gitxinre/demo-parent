package com.ly.demo.mq.consumer.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

/**
 * @author xinre, created on 2019/12/23
 */
public class ActiveMQTest {

    @Test
    public void testQueueConsumer() throws JMSException, InterruptedException {

        String brokerUrl = "tcp://192.168.65.10:61616";
        String destinationName = "test-queue";
        String destinationType = "queue";

        activeMQConsumer(brokerUrl, destinationName, destinationType);


    }

    @Test
    public void testTopicConsumer() throws JMSException {
        String brokerUrl = "tcp://192.168.65.10:61616";
        String destinationName = "test-topic";
        String destinationType = "topic";

        System.out.println("--- consumer 003 ---");
        activeMQConsumer(brokerUrl, destinationName, destinationType);
    }

    private void activeMQConsumer(String brokerUrl, String destinationName, String destinationType) throws JMSException {

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

        // 5、使用session对象，创建Destination对象（queue与topic两种形式）,该Destination应该与producer一致
        // 参数就是消息队列的名称，接口Queue继承自Destination接口
        Destination destination = null;
        switch (destinationType) {
            case "queue":
                destination = session.createQueue(destinationName);
                break;
            case "topic":
                destination = session.createTopic(destinationName);
                break;
            default:
                destination = session.createQueue(destinationName);
                break;
        }


        // 6、使用session对象，创建一个生产者consumer对象
        MessageConsumer consumer = session.createConsumer(destination);

        // 7、向consumer对象中设置一个messageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                // 8、获取消息内容
                if (message instanceof TextMessage) {
                    TextMessage msg = (TextMessage) message;
                    try {
                        // 9、打印消息内容
                        String text = msg.getText();
                        System.out.println("activemq text = " + text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        // 10、系统等待接收消息
        /*while (true) {
            Thread.sleep(100);
        }*/
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 11、关闭资源（session、connect等资源）
        consumer.close();
        session.close();
        connection.close();
    }
}
