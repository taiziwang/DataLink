# 2021520，DataLink将与您邂逅

## 前言

在2021年5月20日的夜晚，我们不是恋人缠绵，而是伙伴共码，只为早早与 DataLink 有个邂逅~

昨天，Flink 中文社区发布了最新文章 《[官宣｜Apache Flink 1.13.0 正式发布，流处理应用更加简单高效！](https://mp.weixin.qq.com/s/zR_W_xZwkFkHeWMYhBnB-A)》，文中对 Flink 1.13 的多项核心修复与优化进行了总结与描述，如被动扩缩容、分析性能等重要特性，以及 Sql/Table API、PyFlink 的进展等。其细节内容可以自行阅读原文进行了解。

对于批流一体的目标来说，FlinkSql 和 Table API 的进展十分关键。在1.13中的此类新特性主要有以下：

**1.通过 Table-valued 函数来允许用户定义新的时间窗口**

**2.简化了 Table 和 DataStream 的转换**

**3.实现了 SQL  Client 执行 sql 脚本的作业形式**

**4.支持常用的Hive DDL、DML 和 DQL**

**5.SQL 的CURRENT_TIMESTAMP等时间函数更加精确**

**6.Hbase支持异步查询与查询缓存**

但这些对于我们将 Flink 应用到项目中的建设成本还是高昂的，较为理想的方式是通过建设 Flink 开发运维平台来降低后续项目成本，压缩技术人员需求，做到落地即开发、运维即轻松的状态。然而现在并没有完整或实用的 Flink 平台开源项目，致使 Flink 的应用变为一线大厂的生产利器，而大多数企业却处于无从下手的状态，这很显然不利于 Flink 在各种企业环境中的成长。

所以，为了促使  Flink 社区可以更快更全面的发展，并将 Flink 打造为更强大地新一代批流计算框架，于是，它即将到来！！~ DataLink数据中台。

## 简介

DataLink 是一个创新的数据中台解决方案，它基于 SpringCloud Alibaba 和 Apache Flink 实现。它使用了时下最具影响力的实时计算框架Flink，而且紧跟社区发展，试图只通过一种计算框架来解决离线与实时的问题，实现Sql语义化的批流一体，帮助用户减少技术与运维成本，推动数据产业更高效的发展。

面对业内数据人员紧缺的现状，DataLink 的目标不仅要实现企业级数据中台的全部功能，而且还要通过设计多种辅助决策功能来缓解人才的需求，助力数据生产。

DataLink 开源项目及社区正在建设，希望本项目可以帮助你更快发展。

## 功能

<img src="https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTp6w4PuJruFaLV6uShCJDkzGwuGyPnbnTF2j7ia4u3VkWcsvI3oU5SRbP0BTL31H789qicC32poLJUA/0?wx_fmt=png"  />

## Flink on DataLink

Flink 在 DataLink 数据中台的实践主要为 FlinkSql 任务的管理与 FlinkSql 的功能增强。由于DataLink 内部对 Flink 1.12.2 源码进行重写、封装与优化，用户无需修改官方源码并打包，只需配置外部集群地址即可使用增强特性。主要内容如下：

### **1.支持 FlinkSql 的在线web开发者工具**

实现了开发者工具。该工具具备 Sql Client 的所有功能，并将不友好的命令行开发与测试转变为线上界面化行为，避免了一些 Sql Client 的bug与不足如过多换行将导致客户端异常关闭、Sql难以修改、团队开发无法共享环境等问题。并拓展了更多实用功能，如历史执行记录、数据导出、Flink Sql自动补填等。该工具支持本地环境与集群环境运行。

### **2.支持 FlinkSql 的批流任务智能运行**

实现了界面化运维 FlinkSql 任务的内容，一键提交任务至 Flink 集群，提交过程中没有二次打包、生成 sql 脚本文件等不稳定步骤，并可动态配置执行环境参数。

### **3.支持 FlinkSql 的任务调度**

实现了 FlinkSql 的分布式任务调度，包括时间调度和依赖调度。具有调度日志查询、监控等功能。

### **4.支持 Flink 全量历史执行记录的过滤查询**

实现了 Flink 集群 Hostory 服务的数据消费能力，可以提供多样的查询方式，便于排查问题。

### **5.支持 Flink集群所有任务的监控预警**

实现了对 Flink 任务的实时执行状态监控，可进一步提供预警通知等功能。

### **6.支持 FlinkSql 的多任务的血缘分析**

实现了多个 FlinkSql 任务的血缘分析，并通过分析FlinkSql的物理执行图与 DataLink 内部的元数据模块实现全面的真实执行状态的血缘分析。包括树图、旭日图等展示。

### **7.增强 FlinkSql 语法功能**-Sql片段机制

实现了Flink集群内对 Sql 片段的管理，通过片段机制减少相同代码的出现以及避免多余修改。并且在执行前用户可以指定是否启用片段机制

```sql
:tb=student;:tbpk=primary key (sid) not enforce;
show fragments;
tb
tbpk
```

```sql
create table ${tb} (
	sid int,
	name string,
	${tbpk}
	) with ('connector' = 'jdbc',
	...
	'table-name' = '${tb}'
	)
select * from ${tb}
```

### **8.增强 FlinkSql 语法功能-AGGTABLE表值聚合函数**

实现了Flink社区最新版本仍未支持的Sql语义的标值聚合函数处理。

以下示例为不规则一维表转规则二维表的Demo部分Sql。

```sql
CREATE AGGTABLE agg1 AS 
SELECT sid,data
FROM score
GROUP BY sid
AGG BY TO_Map(cls,score) as (data)

insert into studentscore
select 
a.sid,a.name,
cast(GET_KEY(b.data,'chinese','0') as int),
cast(GET_KEY(b.data,'math','0') as int),
cast(GET_KEY(b.data,'english','0') as int)
from student a
left join agg1 b on a.sid=b.sid 
```

### **9.支持 FlinkSql语法校验与执行图生成**

实现了 FlinkSql 语法校验，支持本地校验或集群校验，可不需要执行即可获取其物理执行图。未来将可视化渲染至页面。

### **10.支持 FlinkSql 函数文档管理**

实现了 FlinkSql 官网的所有函数文档梳理与管理，并支持扩展自定义函数。

### **11.支持基于元数据的 FlinkSql 智能生成器**

实现了 FlinkSql 与 DataLink内部元数据的联动，可以自动依据 select 语句和元数据生成其 create 和 insert 语句。用户只需要编写 select 语句即可。

### **12.支持多类型的 FlinkSql 翻译器**

实现了 Mysql、Oracle等传统数据库的语句翻译至 FlinkSql 可执行语句。在初级需求下降低使用门槛，与学习成本。

## 未来

未来，Flink on DataLink 将夯实已实现的增强特性，并紧随 Flink 社区发展，也将进行更加深度定制的 Flink 应用优化，让 DataLink 为 Flink 提供更广泛的舞台空间，助力Flink 社区发展。

  当然，DataLink 将以崭新的面貌与大家邂逅，已实现的功能将更加健壮地重构，更多强大功能也将同步规划实现。

## 交流

欢迎您加入社区交流分享，也欢迎您为社区贡献自己的力量。

QQ社区群：965889874，申请备注 “ 数据中台 ”

微信社区群：扫码拉群

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

![](https://mmbiz.qpic.cn/mmbiz_png/dyicwnSlTFTr7we6IJ7icjRic0Xex1NVgrfSCg1PzfSyIXsz7dezSnbBBIZHOBCbDq22u9mQhlFsM64moV1HAzHRw/0?wx_fmt=png)


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

> FlinkSql与元数据

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

