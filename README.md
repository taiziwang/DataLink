# DataLink

## 简介

DataLink 是一个创新的数据中台解决方案，它基于 SpringCloud Alibaba 和 Apache Flink 实现。它使用了时下最具影响力的实时计算框架Flink，而且紧跟社区发展，试图只通过一种计算框架来解决离线与实时的问题，实现Sql语义化的批流一体，帮助用户减少技术与运维成本，推动数据产业更高效的发展。

面对业内数据人员紧缺的现状，DataLink 的目标不仅要实现企业级数据中台的全部功能，而且还要通过设计多种辅助决策功能来缓解人才的需求，助力数据生产。

DataLink 开源项目及社区正在建设，希望本项目可以帮助你更快发展。

## 功能

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTp6w4PuJruFaLV6uShCJDkzGwuGyPnbnTF2j7ia4u3VkWcsvI3oU5SRbP0BTL31H789qicC32poLJUA/0?wx_fmt=png)

正在维护中....

## 最新进展

### 已发布：

暂无
	
### 即将发布：

releases-0.1.0 （FlinkSql语句集管理及集群执行）
	
## 开始

### 下载安装包

正在维护中...

### 从源码编译安装包

```java
maven clean install 
```

```java
bin/ -- 启动脚本
|- auto.sh 
config/ -- 配置文件
|- application.properties
|- application-dev.properties
|- bootstrap.properties
lib/ --外部依赖
|- *.jar
logs/ -- 日志
module/ -- 微服务模块
|- datalink-gateway.jar -- 网关模块
|- datalink-uaa.jar -- 授权模块
|- datalink-user.jar -- 用户模块
|- datalink-task.jar -- Dlink作业模块
```

正在维护中...

### 启动

```shell
sh auto.sh start base
```

正在维护中...

### 单机模式

正在维护中...

### 集群模式

正在维护中...

## 结构

```java
DataLink -- 父项目
|-datalink-commons -- 通用中心
| |-datalink-auth-spring-boot-starter -- Spring Security 封装模块
| |-datalink-base -- 基础封装模块
| |-datalink-common-spring-boot-starter -- 通用封装模块
| |-datalink-db-plug -- DB工具封装模块
| |-datalink-elasticsearch-spring-boot-starter -- Elasticsearch 封装模块
| |-datalink-log-spring-boot-starter -- Log 封装模块
| |-datalink-mybatis-spring-boot-starter -- MybatisPlus 封装模块
| |-datalink-redis-spring-boot-starter -- Redis 封装模块
| |-datalink-ribbon-spring-boot-starter -- Ribbon和Feign 封装模块
| |-datalink-sentinel-spring-boot-starter -- Sentinel 封装模块
| |-datalink-swagger-spring-boot-starter -- Swagger 封装模块
|-datalink-config -- 配置中心
|-datalink-dbase -- 基础资源中心
| |-datalink-db -- DB 中心[8003]
| |-datalink-user -- 用户中心[8001]
|-datalink-dlink -- Dlink治理中心
|-|-datalink-task -- FlinkSql 作业中心[8004]
|-datalink-doc -- 文档中心
|-datalink-gateway -- 网关中心[9900]
|-datalink-uaa -- 权限中心[8002]
|-datalink-web -- React Web [8000]
```

正在维护中...

## 交流

欢迎您加入社区交流分享，也欢迎您为社区贡献自己的力量。

QQ社区群：965889874，申请备注 “ 数据中台 ”

微信社区群：添加微信号 wenmo_ai 邀请进群，申请备注 “ 数据中台 ”

公众号：[DataLink数据中台](https://mmbiz.qpic.cn/mmbiz_jpg/dyicwnSlTFTp6w4PuJruFaLV6uShCJDkzqwtnbQJrQ90yKDuuIC8tyMU5DK69XZibibx7EPPBRQ3ic81se5UQYs21g/0?wx_fmt=jpeg)

邮箱：aiwenmo@163.com

## 友情链接

[Apache Flink](https://github.com/apache/flink)

[Mybatis Plus](https://github.com/baomidou/mybatis-plus)

[microservices-platform](https://gitee.com/zlt2000/microservices-platform)

[ant-design-pro](https://github.com/aiwenmo/ant-design-pro)

本项目的设计思路与代码实现受以上开源项目源码指导与启发，站在巨人的肩膀，才能看得更远。

## 截图

以下截图为已实现的部分截图，是本开源项目最终效果。

> 登录页

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTq98f7TCHmw3WqfMkBHVzZIEcQ9pEeiciavaYyMrvTj3e6GgevmVDN2Ky8Q1mR7kjxNUleTd1XhRkOg/0?wx_fmt=png)


> 用户管理

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTq98f7TCHmw3WqfMkBHVzZIYc8bjY47OdibJmkRBFc6S8Hy0zFicCKw9ckBoJp3PvBOjKrP7kbHqPaw/0?wx_fmt=png)


## 预览

以下预览图为已实现的部分截图，并非开源项目最终效果，仅供参考。

> 主页

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpp82PZfrRR5tUaTWyHasItGV5KnUUrxUPyFN2UjFGoSDXKzoQJqgEKaicp2BzJmPqmLJXH65niaqww/0?wx_fmt=png)

> 数据源

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpp82PZfrRR5tUaTWyHasItic0d6aRASKpVJdnFs9eWpicVa3U0ZDbnknH3cHky58icP7zfE3mIt8TLg/0?wx_fmt=png)

> 元数据

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpp82PZfrRR5tUaTWyHasItoM2zeCG5jKlNlD1cee2xUOPmQBcW3C8HJ14urGriaEkcrF7fQ75l5fA/0?wx_fmt=png)

> 来源构建

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpp82PZfrRR5tUaTWyHasItPia7ibWLqgpduibULSFibjvwy2KPzEwET0cQciaQe0JiaGsSd0SSL2rkzYRA/0?wx_fmt=png)

> FlinkSql

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpeODtahZc77tWyTMPxR5Kic2vHIgibsjhfg6yiaI3B4N5HORjUQRibjZZYZNRGOP9VERcTeBHVKQCRVw/0?wx_fmt=png)

> FlinkSql在线开发器

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpeODtahZc77tWyTMPxR5KicuRRo8Y1FmtkT1BYDQkp5cI5r6pbz0XDxqicgKUWlWuxMdMicSl1xZUSA/0?wx_fmt=png)

> FlinkSql语法校验与执行解释

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpeODtahZc77tWyTMPxR5KicmeuPDSsS2jNU79Rulial2aU7QiaOibzYxPrZyLILbW5KQa10roOWXxo2A/0?wx_fmt=png)

> FlinkSql函数文档

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpeODtahZc77tWyTMPxR5KicdwSp76kaL2Q1DsKjJRZLSpdjnF1A6U378gzombaZgunFv0gbadgHMQ/0?wx_fmt=png)

> Flink任务历史

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpp82PZfrRR5tUaTWyHasItyTiccy68aRew8xcSnTjNrVkRjNWLR12dwdgNGapeoQjIDJ6iaMyvRShA/0?wx_fmt=png)

> Sql翻译

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpeODtahZc77tWyTMPxR5Kicc7UCs5iaNUWJoLdey1h0ibfSYBdqdSdaibhunsnJn6ZTp71ichaLKMB4Zg/0?wx_fmt=png)

> FlinkSql与元数据的血缘分析

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpeODtahZc77tWyTMPxR5KicTbVRsT6GvhTVP6CQsUqoSBZ40Yratv6sFW8AFg5dj17BjHP1XD8RGQ/0?wx_fmt=png)

> NLP字典映射与运维

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpeODtahZc77tWyTMPxR5KicsmWEhCTQeYsNT1TiazL68rWJTZfRXUSibUyr695O6OYwuJUjHUQUicdag/0?wx_fmt=png)

> DB控制台

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpp82PZfrRR5tUaTWyHasIt1GH5etlMEh8GwcxA3xALayEibr8IZzSK5icsMkUEEIRJibdGPY0SXYoAg/0?wx_fmt=png)

> 查询服务注册

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpeODtahZc77tWyTMPxR5Kic7xoITGgpe5ibAia4uOF6ymxZbjwkxl1pVHUX25WshsprGCFlFEiaXUewg/0?wx_fmt=png)

> 共享文件中心

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpeODtahZc77tWyTMPxR5Kic5IuF4Uyf2D9a0icZv5kVhsTEufeibOAA79Ofp3veR8xzZHGicoJ2TAEicA/0?wx_fmt=png)

> 微服务监控

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpp82PZfrRR5tUaTWyHasIt1aiaywj37DXKtEMMxaTypBiaWAO2AZAfprXIN8NSuqhrBItVYDB805icA/0?wx_fmt=png)

> GPE监控

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpp82PZfrRR5tUaTWyHasItZvk2pBiakXDL7ZVj7g4kwnCARzd4SjoCLrleOMJj25iayACuPhRC43ibg/0?wx_fmt=png)

