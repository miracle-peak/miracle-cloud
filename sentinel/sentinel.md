构建sentinel镜像
```
docker build -it sentinel .
```
启动sentinel
```
docker run -d --name sentinel -p 9670:8080 sentinel镜像id
```

在 nacos 配置sentinel限流持久化规则
```yaml
         [
            {
                "resource": "/system/user/login",
                "limitApp": "default",
                "grade": 1,
                "count": 2,
                "strategy": 0,
                "controlBehavior": 0,
                "clusterMode": false
            }
         ]
```
![nacos中配置sentinel限流规则](https://images.gitee.com/uploads/images/2020/1106/150010_0eb895f1_4776729.png "2020-11-06 14-59-34 的屏幕截图.png")
 * resource : 资源名，限流规则作用对象，一般为请求URI
 * limitApp : 控流针对的调用来源，default则不区分调用来源
 * grade :  限流阈值类型；0表示根据并发量来限流，1表示根据QPS来进行限流
 * count :  限流阈值
 * strategy ： 调用关系限流策略
 * controlBehavior ： 限流控制行为（快速失败 、warm up 、排队等候）
 * clusterMode ： 是否为集群模式
 
 
 如对某个模块的功能限流,需引入依赖
 ```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>
```
```xml
<!-- nacos做sentinel流控等规则的持久化 用-->
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-datasource-nacos</artifactId>
</dependency>
```

配置文件
```yaml
spring:
  cloud:
    nacos:
      discovery:
            server-addr: 127.0.0.1:8848

    sentinel:
      transport:
        dashboard: 127.0.0.1:9670
        # 客户端监控API的端口
        port: 8877
      # 使用nacos持久化sentinel限流规则
      datasource:
        ds1:
          nacos:
            server-addr: 127.0.0.1:8466
            # dataId是 nacos 配置列表的Data ID:
            dataId: sentinel-service
            groupId: DEFAULT_GROUP
            # nacos中配置内容的数据格式
            data-type: json
            rule-type: flow

```