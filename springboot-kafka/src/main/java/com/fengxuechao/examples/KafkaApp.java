package com.fengxuechao.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 这是一个Kafka的学习笔记
 * <ul>
 *     <li>基本使用</li>
 *     <li>和 zookeeper 的 SASL 认证</i>
 * </ul>
 *
 * @author fengxuechao
 * @date 2020/6/2
 */
@SpringBootApplication
public class KafkaApp {

    //初始化系统属性
    /*static {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.setProperty("java.security.auth.login.config",
                loader.getResource("").getPath()+ File.separator+"kafka_client_jaas.conf");
    }*/

    public static void main(String[] args) {
        SpringApplication.run(KafkaApp.class, args);
    }
}
