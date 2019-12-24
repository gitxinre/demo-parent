package com.ly.demo.mq.consumer.activemq.listener;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author xinre, created on 2019/12/23
 */
public class MyMessageListenerTest {


    @Test
    public void testConsumer() throws IOException {

        // 1、与spring整合后activemq只需启动spring容器，listener自会监听消息信息
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-mq.xml");

        System.in.read();

    }

}
