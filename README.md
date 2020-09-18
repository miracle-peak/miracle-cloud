# miracle-cloud

#### 介绍
一个小型springcloud脚手架，基于spring cloud alibaba，采用Nacos注册和配置中心，集成流量卫兵Sentine，zipkin链路跟踪,路由使用gateway，ribbon负载

#### 技术架构

- 后端：springcloud + mybatis-plus + redis + mysql + nacos + zipkin + sentinel + ribbon + gateway + jwt;
- [前端](https://gitee.com/miracle-peak/miracle-vue)：vue + axios + element-ui;


#### 安装教程

1.  下载sentinel控制台jar包或者使用项目sentinel目录下的sentinel-dashboard-1.8.0，[制作镜像并启动](https://gitee.com/miracle-peak/miracle-cloud/blob/master/sentinel/sentinel.md)
2.  启动nacos端口：8848
3.  启动redis，zipkin

#### 使用说明

1.  miracle-system:系统模块，包含角色管理，用户管理，菜单管理;
2.  miracle-common:公共模块，提供工具如jwt工具，加密工具等工具，包含zikpin链路跟踪，以及全局异常处理，其他模块使用时需要引入依赖及扫包”pers.miracle.miraclecloud.common.exception“，参考system/ribbon模块的启动类;
3.  miracle-ribbon:负载均衡模块，负责将请求均衡分发到服务，避免大量请求同时请求一个服务使服务压力过大。适用于部署了多个相同的服务;如果新增模块需要使用负载，则在RibbonController中添加方法，匹配对应模块的请求路径，具体参考system系统模块的负载。
4.  miracle-gateway:网关路由模块,根据路由匹配分发到相应的服务;
5. [sentinel限流使用](https://gitee.com/miracle-peak/miracle-cloud/blob/master/sentinel/sentinel.md)




