package com.itic.im.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Yangwl
 * @description 实时通讯接入层
 * @date 2021年3月10日15点40分
 */
@SpringBootApplication
public class ImGateApplication {

    protected final static Logger logger = LoggerFactory.getLogger(ImGateApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ImGateApplication.class, args);
        logger.info("****** IM 接入层应用(ImGateApplication) 启动成功!!! ******");
    }

}
