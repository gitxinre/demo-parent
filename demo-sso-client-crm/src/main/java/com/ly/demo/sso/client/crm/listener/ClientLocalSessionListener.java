package com.ly.demo.sso.client.crm.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author xinre
 */
public class ClientLocalSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

        System.out.println("Created ClientLocalSessionListener Session().getId() = " + httpSessionEvent.getSession().getId());


    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

        System.out.println("Destroyed ClientLocalSessionListener Session().getId() = " + httpSessionEvent.getSession().getId());

    }
}
