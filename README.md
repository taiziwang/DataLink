# DataLink

## 简介

DataLink 是一个创新的数据中台解决方案，它基于 SpringCloud Alibaba 和 Apache Flink 实现。它使用了时下最具影响力的实时计算框架Flink，而且紧跟社区发展，试图只通过一种计算框架来解决离线与实时的问题，实现Sql语义化的批流一体，帮助用户减少技术与运维成本，推动数据产业更高效的发展。

面对业内数据人员紧缺的现状，DataLink 的目标不仅要实现企业级数据中台的全部功能，而且还要通过设计多种辅助决策功能来缓解人才的需求，助力数据生产。

DataLink 开源项目及社区正在建设，希望本项目可以帮助你更快发展。

## 功能

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTp6w4PuJruFaLV6uShCJDkzGwuGyPnbnTF2j7ia4u3VkWcsvI3oU5SRbP0BTL31H789qicC32poLJUA/0?wx_fmt=png)

正在维护中....

## 开始

### 打包

```java
maven clean install 
```

```java
config/ -- 配置文件
bin/ --外部依赖
user-center-0.1-SNAPSHOT.jar
```

正在维护中...

### 执行

```java
java -jar -Dloader.path=lib user-center-0.1-SNAPSHOT.jar
```

正在维护中...

### 单机模式

正在维护中...

### 集群模式

正在维护中...

## 结构

```java
DataLink --父项目
|-datalink-commons --通用中心
| |-datalink-base --基础封装模块
| |-datalink-db-plug --DB工具封装模块
| |-datalink-mybatis-spring-boot-starter --MybatisPlus封装模块
| |-datalink-swagger-spring-boot-starter --Swagger封装模块
|-datalink-config --配置中心
|-datalink-dbase --基础资源中心
| |-db-center --DB中心[8001]
| |-user-center --用户中心[8000]
|-datalink-doc --文档中心
```

正在维护中...

## 交流

欢迎您加入社区交流分享，也欢迎您为社区贡献自己的力量。

QQ社区群：965889874，申请备注 “ 数据中台 ”

公众号：[DataLink数据中台](https://mmbiz.qpic.cn/mmbiz_jpg/dyicwnSlTFTp6w4PuJruFaLV6uShCJDkzqwtnbQJrQ90yKDuuIC8tyMU5DK69XZibibx7EPPBRQ3ic81se5UQYs21g/0?wx_fmt=jpeg)

邮箱：aiwenmo@163.com

## 友情链接

[Apache Flink](https://github.com/apache/flink)

[Mybatis Plus](https://github.com/baomidou/mybatis-plus)

[microservices-platform](https://gitee.com/zlt2000/microservices-platform)

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

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpp82PZfrRR5tUaTWyHasItP5K1phbU1rJ7QAfk3cQC1lGZCuMyibwpUsYictPaWCIsuZKTHalPg2Og/0?wx_fmt=png)

> Flink任务历史

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpp82PZfrRR5tUaTWyHasItyTiccy68aRew8xcSnTjNrVkRjNWLR12dwdgNGapeoQjIDJ6iaMyvRShA/0?wx_fmt=png)

> Sql翻译

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpp82PZfrRR5tUaTWyHasItxoVfRD0XQKIibFCbBicsxH6iahS5LEj3OF00ibaIDjc5P7U7nb1wDsf8ag/0?wx_fmt=png)

> DB控制台

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpp82PZfrRR5tUaTWyHasIt1GH5etlMEh8GwcxA3xALayEibr8IZzSK5icsMkUEEIRJibdGPY0SXYoAg/0?wx_fmt=png)

> 微服务监控

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpp82PZfrRR5tUaTWyHasIt1aiaywj37DXKtEMMxaTypBiaWAO2AZAfprXIN8NSuqhrBItVYDB805icA/0?wx_fmt=png)

> GPE监控

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTpp82PZfrRR5tUaTWyHasItZvk2pBiakXDL7ZVj7g4kwnCARzd4SjoCLrleOMJj25iayACuPhRC43ibg/0?wx_fmt=png)

