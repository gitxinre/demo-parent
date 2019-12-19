package com.ly.demo.common.universal;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理（尽量使用exception包下的全局异常处理）
 *
 * @author xinre
 */
@Slf4j
@Component
public class GlobalExceptionProcessor implements HandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionProcessor.class);

    private class RequestSuffix {

        //application/json;charset=UTF-8
        private static final String SUFFIX_JSON = ".json";
        //text/html;charset=UTF-8
        private static final String SUFFIX_PAGE = ".page";

    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //1、控制台打印异常
        //2、向日志文件中写入异常
        //3、发短信
        //4、发邮件
        //5、错误页面展示

        ModelAndView mv;
        String defaultMsg = "system error";
        String url = request.getRequestURL().toString();
        // .json  .page
        if (url.endsWith(RequestSuffix.SUFFIX_JSON)) {
            if (ex instanceof RuntimeException) {
                log.error("unknow Permission exception, url:[{}]", url, ex);
                Result rr = Result.fail(ex.getMessage());
                mv = new ModelAndView("fileapi", rr.toMap());
            } else {
                log.error("unknow other json exception, url:[{}]", url, ex);
                Result rr = Result.fail(defaultMsg);
                mv = new ModelAndView("jsonView", rr.toMap());
            }
        } else if (url.endsWith(RequestSuffix.SUFFIX_PAGE)) {
            log.error("unknow page exception, url:[{}]", url, ex);
            Result rr = Result.fail(defaultMsg);
            mv = new ModelAndView("exception", rr.toMap());
        } else {
            log.error("unknow other exception, url:[{}]", url, ex);
            Result rr = Result.fail(defaultMsg);
            mv = new ModelAndView("jsonView", rr.toMap());
        }

        return mv;
    }
}
