package com.ly.demo.webpagestatic.freemarker.controller;

import com.google.common.collect.Maps;
import com.ly.demo.common.universal.Result;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author xinre, created on 2019/12/25
 */
@Controller
public class FreemarkerController {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * freemarker测试
     *
     * @param args
     * @throws IOException
     * @throws TemplateException
     */
    public static void main(String[] args) throws IOException, TemplateException {
        // 1、创建模板文件（一般是使用ftl作为模板文件的后缀，eg：WEB-INF/ftl/hello.ftl
        // 2、创建freemarker的configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 3、设置参数：模板所在路径、字符集（UTF-8）
        configuration.setDirectoryForTemplateLoading(new File("C:\\F\\IdeaProjects\\demo-parent\\demo-webpage-static\\demo-webpage-static-freemarker\\src\\main\\webapp\\WEB-INF\\ftl"));
        configuration.setDefaultEncoding("UTF-8");
        // 4、使用configuration对象加载一个模板文件，需要指定模板文件名
        Template template = configuration.getTemplate("hello.ftl");
        // 5、创建数据集，pojo或者map，传递到模板中的数据集
        Map<String, Object> mapData = Maps.newHashMap();
        mapData.put("hello", "hello world !");
        // 6、创建一个writer对象，指定输出文件的路径和文件名
        Writer writer = new FileWriter(new File("D:\\mfl-study\\freemarker\\hello.txt"));
        // 7、使用模板对象的process方法输出文件
        template.process(mapData, writer);
        // 8、关闭资源
        writer.close();
        System.out.println(" === OK === ");
    }

    /**
     * 与spring整合测试
     */
    @RequestMapping("/helloFreemarker")
    @ResponseBody
    public Result helloFreemarker() throws IOException, TemplateException {

        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("hello.ftl");
        Map<String, Object> mapData = Maps.newHashMap();
        mapData.put("hello", "freemarker spring hello world !");
        Writer writer = new FileWriter(new File("D:\\mfl-study\\freemarker\\spring-hello.txt"));
        template.process(mapData, writer);
        writer.close();

        return Result.success();
    }

}
