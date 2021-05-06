# 第二篇：Centos7 Hbase 1.4.3 部署

> ​	继上篇文章：《Centos7 Hadoop 2.7.7 集群部署》
>
> ​	本文参考网址：https://blog.csdn.net/pucao_cug/article/details/72229223

## 1	上传、分发、解压

```shell
[root@node1 hadoop]# scp /data/app/hadoop/hbase-1.4.3-bin.tar.gz root@node2:/data/app/hadoop   
[root@node1 hadoop]# scp /data/app/hadoop/hbase-1.4.3-bin.tar.gz root@node3:/data/app/hadoop 
[root@node1 hadoop]# tar -xf hbase-1.4.3-bin.tar.gz
```

## 2	配置环境变量

```shell
[root@node1 hadoop]# vi /etc/profile
export HBASE_HOME=/data/app/hadoop/hbase-1.4.3

export PATH=${JAVA_HOME}/bin:$PATH:$ZOOKEEPER_HOME/bin:$HADOOP_HOME/bin:$HBASE_HOME/bin
[root@node1 hadoop]# source /etc/profile
```

## 3	修改配置文件

### 3.1	修改hbase-env.sh

```shell
[root@node1 conf]# vi hbase-env.sh
export JAVA_HOME=/usr/local/java/jdk1.8.0_291
export HADOOP_HOME=/data/app/hadoop/hadoop-2.7.7
export HBASE_HOME=/data/app/hadoop/hbase-1.4.3
export HBASE_CLASSPATH=/data/app/hadoop/hadoop-2.7.7/etc/hadoop
export HBASE_PID_DIR=/data/app/hadoop/hbase/pids
export HBASE_MANAGES_ZK=false
```

### 3.2	修改hbase-site.xml

​	

```shell
[root@node1 conf]# vi hbase-site.xml
<configuration>
<property>
 <name>hbase.rootdir</name>
 <value>hdfs://node1:9000/hbase</value>
 <description>The directory shared byregion servers.</description>
</property>
<property>
 <name>hbase.zookeeper.property.clientPort</name>
 <value>2181</value>
 <description>Property from ZooKeeper's config zoo.cfg. The port at which the clients will connect.
 </description>
</property>
<property>
 <name>zookeeper.session.timeout</name>
 <value>120000</value>
</property>
<property>
 <name>hbase.zookeeper.quorum</name>
 <value>node1,node2,node3</value>
</property>
<property>
 <name>hbase.tmp.dir</name>
 <value>/data/app/hadoop/hbase/tmp</value>
</property>
<property>
 <name>hbase.cluster.distributed</name>
 <value>true</value>
</property>
</configuration>
```

新建目录：

```shell
[root@node1 hadoop]# mkdir hbase
[root@node1 hadoop]# mkdir hbase/tmp
[root@node1 hadoop]# mkdir hbase/pids
```

### 3.3	修改regionservers文件

```shell
[root@node1 conf]# vi regionservers
node1
node2
node3
```

### 3.4	启动

​	启动HBase集群：

```shell
[root@node1 bin]# sh start-hbase.sh
```

​	单独启动一个HMaster进程：

```shell
[root@node1 bin]# sh hbase-daemon.sh start master
```


​	单独停止一个HMaster进程：

```shell
[root@node1 bin]# sh hbase-daemon.sh stop master
```


​	单独启动一个HRegionServer进程：

```shell
[root@node1 bin]# sh hbase-daemon.sh start regionserver
```


​	单独停止一个HRegionServer进程：

```shell
[root@node1 bin]# sh hbase-daemon.sh stop regionserver
```

### 3.5 节点进程

​	node1:

```shell
[root@node1 bin]# jps
25296 QuorumPeerMain
22337 JournalNode
22865 DFSZKFailoverController
29221 HMaster
22646 NameNode
29718 HRegionServer
30265 Jps
23006 DataNode
23358 NodeManager
```

​	node2:

```shell
[root@node2 bin]# jps
22289 NameNode
22450 DFSZKFailoverController
29492 HRegionServer
22006 JournalNode
22967 ResourceManager
23067 NodeManager
30155 Jps
22572 DataNode
24910 QuorumPeerMain
```

​	node3:

```shell
[root@node3 bin]# jps
22240 DataNode
24867 QuorumPeerMain
27477 HRegionServer
28008 Jps
21948 JournalNode
22525 ResourceManager
22845 NodeManager
```

### 3.6	Web页面

​	http://192.168.123.156:16010

​	http://192.168.123.156:16030/rs-status

### 3.7	打开shell

```shell
[root@node3 bin]# hbase shell
hbase(main):001:0> status
1 active master, 0 backup masters, 3 servers, 0 dead, 0.6667 average load
```

