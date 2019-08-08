package com.hbc.chatroom.config;


import freemarker.template.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Beer
 * @Date: 2019/8/8 15:45
 * @Description:
 */

@WebListener
public class FreeMarkerListener implements ServletContextListener {
    //根据KEY值取得相应的VALUE值
    public static final String TEMPLATE_KEY = "_template_";

    /**
     * 项目一加载就自动调用
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //配置版本
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
        //配置加载ftl的路径(绝对路径)
        try {
            cfg.setDirectoryForTemplateLoading(
                    new File("C:\\Users\\DELL\\Desktop\\Projects\\My-Projects\\chatRoom_Webapp\\src\\main\\webapp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //配置页面的编码
        cfg.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        servletContextEvent.getServletContext().setAttribute(TEMPLATE_KEY,cfg);

    }

    /**
     * 项目马上终止的时候就调用
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
