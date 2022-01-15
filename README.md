### 功能特点
``` bush
    1. starter插件式，即插即拔
    2. 基于netty-socketIO实现， 有良好的高并发抗压能力
    3. 实现socket动态认证监听器
    4. 支持单机版本(内存模式)，集群版本(redis模式)
    5. 消息自动存储，也可自定义重写
    6. 支持web api方式通讯, 只需添加依赖im-starter-api
    7. 默认单机模式，集群模式只需添加依赖im-starter-redis
``` 
## 目前只有这些基础功能， 其他开发中............
### 更新
2022-01-13
``` bush
    1. 创建im-starter
    2. ImConfig配置
    3. 重写 SocketIOServer 支持动态添加认证监听
    4. 添加SoMap
    5. 添加ImTemplate 模板方法消息通讯， 支持直连模式，redis模式
    6. 添加Dao，数据持久化，支持内存模式 redis模式
    7. im-starter-api 实现endpoint 公共api
    8. 动态添加频道
    9. 实现redis模式，一个房间对应一个频道，提高并发能力
```
2022-01-14
``` bush
    1. 扩展DefaultEventHandler 全局事件监听
```
QQ：[597392641]
技术交流QQ群：[570968411]
