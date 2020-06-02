# zookeeper_kafka

Zookeeper和Kafka的学习笔记

## 什么是zookeeper？

## zookeeper的使用场景

## zookeeper节点有哪些特性，什么时候使用什么特性的节点？

## 使用zookeeper实现服务注册中心，原理是什么？用到了zk的哪些特性？

## zookeeper集群搭建

## kafka集群搭建

## zookeeper和kafka的安全认证机制SASL

zookeeper在生产环境中，如果不是只在内网开放的话，就需要设置安全认证，可以选择`SASL`的安全认证。
以下是和kafka的联合配置，如果不需要kafka可以去掉kafka相关的权限即可。
下面就是详细的部署步骤：

### Zookeeper 的安全认证配置

zookeeper所有节点都是对等的，只是各个节点角色可能不相同。以下步骤所有的节点配置相同。

1. 导入kafka的相关jar

    从kafka/lib目录下复制以下几个jar包到zookeeper的lib目录下：
    
    ```
    kafka-clients-*.jar
    lz4-java-*.jar
    snappy-java-*.jar
    ```

## SpringBoot 使用 Kafka

### 引入依赖

```xml
<!-- parent org.springframework.boot:spring-boot-starter-parent:1.5.22.RELEASE -->
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>
```

### 添加配置

```yaml
server:
  port: 8088
spring:
  application:
    name: KafkaApp
  kafka:
    listener:
      concurrency: 10    # 设置消费的线程数
      poll-timeout: 1500 # 自动提交设置，如果消息队列中没有消息，等待timeout毫秒后，调用poll()方法。如果队列中有消息，立即消费消息，每次消费的消息的多少可以通过max.poll.records配置。
    template:
      default-topic: test
    producer:
      bootstrap-servers: localhost:9092
    consumer:
      bootstrap-servers: localhost:9092
      group-id: group-0
      enable-auto-commit: true
      auto-offset-reset: latest
    properties:
      sasl.mechanism: PLAIN
      security.protocol: SASL_PLAINTEXT
```

### 测试发送和接收

程序启动命令：`java -Djava.security.auth.login.config=/path/to/kafka_client_jaas.conf -jar zookeeper_kafka-1.0-SNAPSHOT.jar`

```java
/**
 * @author fengxuechao
 * @date 2020/6/2
 */
@Slf4j
@RestController
public class KafkaController {

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    /**
     * Kafka 消息生产者发送消息
     *
     * @param input 消息内容
     */
    @GetMapping("/send/{input}")
    public void sendFoo(@PathVariable String input) {
        log.info("Kafka producer send message: {}", input);
        this.kafkaTemplate.send("test", input);
    }

    /**
     * Kafka 消息消费者消费消息
     *
     * @param message 消息内容
     */
    @KafkaListener(id = "group-0", topics = "test")
    public void listen(String message) {
        log.info("Kafka consumer receive message: {}", message);
    }
}
```