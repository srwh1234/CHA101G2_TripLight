package com.tw.ai.aop;


import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AiAspect {
    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Before("execution(* com.tw.ai.controller.SessionController.processFormData(..)) || execution(* com.tw.ai.controller.ChatGPTController.getLongitude(..))")
    public void beforeStartMethod() {
        logger.info("chatGPT開始執行");
    }
    @After("execution(* com.tw.ai.controller.SessionController.processFormData(..)) || execution(* com.tw.ai.controller.ChatGPTController.getLongitude(..))")
    public void afterStartMethod() {
        logger.info("chatGPT結束執行");
    }


}
