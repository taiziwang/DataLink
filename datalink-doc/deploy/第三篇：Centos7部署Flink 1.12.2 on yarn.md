# 第三篇：Centos7 Flink 1.12.2 on yarn 部署

> 《第一篇：Centos7 Hadoop 2.7.7 集群部署》
>
> 《第二篇：Centos7 Hbase 1.4.3 部署》
>
> 本文参考网址：https://blog.csdn.net/Joe_circle/article/details/110188604

## 1.	查看Hadoop环境

详情见《第一篇：Centos7 Hadoop 2.7.7 集群部署》。

## 2.	Flink部署

### 2.1	上传、解压

```shell
[root@node1 src]# tar -zxf flink-1.12.2-bin-scala_2.11.tgz 
[root@node1 src]# mv flink-1.12.2 /data/app/hadoop
```

### 2.2	设置环境变量

```shell
[root@node1 local]# vi /etc/profile 
export FLINK_HOME=/data/app/hadoop/flink-1.12.2
export HADOOP_CLASSPATH=$HADOOP_HOME/lib:$HBASE_HOME/lib/*

export PATH=${JAVA_HOME}/bin:$PATH:$ZOOKEEPER_HOME/bin:$HADOOP_HOME/bin:$HBASE_HOME/bin:$SCALA_HOME/bin:$FLINK_HOME/bin

[root@node1 local]# source /etc/profile 
```

### 2.3	修改配置文件

#### 2.3.1	flink-conf.yaml

```shell
[root@node1 conf]# vi flink-conf.yaml
################################################################################
#  Licensed to the Apache Software Foundation (ASF) under one
#  or more contributor license agreements.  See the NOTICE file
#  distributed with this work for additional information
#  regarding copyright ownership.  The ASF licenses this file
#  to you under the Apache License, Version 2.0 (the
#  "License"); you may not use this file except in compliance
#  with the License.  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
# limitations under the License.
################################################################################


#==============================================================================
# Common
#==============================================================================

# The external address of the host on which the JobManager runs and can be
# reached by the TaskManagers and any clients which want to connect. This setting
# is only used in Standalone mode and may be overwritten on the JobManager side
# by specifying the --host <hostname> parameter of the bin/jobmanager.sh executable.
# In high availability mode, if you use the bin/start-cluster.sh script and setup
# the conf/masters file, this will be taken care of automatically. Yarn/Mesos
# automatically configure the host name based on the hostname of the node where the
# JobManager runs.

jobmanager.rpc.address: node1

# The RPC port where the JobManager is reachable.

jobmanager.rpc.port: 6123


# The total process memory size for the JobManager.
#
# Note this accounts for all memory usage within the JobManager process, including JVM metaspace and other overhead.

jobmanager.memory.process.size: 1600m


# The total process memory size for the TaskManager.
#
# Note this accounts for all memory usage within the TaskManager process, including JVM metaspace and other overhead.

# taskmanager.memory.process.size: 1728m

# To exclude JVM metaspace and overhead, please, use total Flink memory size instead of 'taskmanager.memory.process.size'.
# It is not recommended to set both 'taskmanager.memory.process.size' and Flink memory.
#
# taskmanager.memory.flink.size: 1280m

taskmanager.memory.flink.size: 6144m
taskmanager.memory.framework.heap.size: 2900m

# The number of task slots that each TaskManager offers. Each slot runs one parallel pipeline.

taskmanager.numberOfTaskSlots: 32

# The parallelism used for programs that did not specify and other parallelism.

parallelism.default: 1

# The default file system scheme and authority.
# 
# By default file paths without scheme are interpreted relative to the local
# root file system 'file:///'. Use this to override the default and interpret
# relative paths relative to a different file system,
# for example 'hdfs://mynamenode:12345'
#
# fs.default-scheme

#==============================================================================
# High Availability
#==============================================================================

# The high-availability mode. Possible options are 'NONE' or 'zookeeper'.
#
high-availability: zookeeper

# The path where metadata for master recovery is persisted. While ZooKeeper stores
# the small ground truth for checkpoint and leader election, this location stores
# the larger objects, like persisted dataflow graphs.
# 
# Must be a durable file system that is accessible from all nodes
# (like HDFS, S3, Ceph, nfs, ...) 
#
high-availability.storageDir: hdfs:///flink12/ha/

# The list of ZooKeeper quorum peers that coordinate the high-availability
# setup. This must be a list of the form:
# "host1:clientPort,host2:clientPort,..." (default clientPort: 2181)
#
high-availability.zookeeper.quorum: node1:2181,node2:2181,node3:2181


# ACL options are based on https://zookeeper.apache.org/doc/r3.1.2/zookeeperProgrammers.html#sc_BuiltinACLSchemes
# It can be either "creator" (ZOO_CREATE_ALL_ACL) or "open" (ZOO_OPEN_ACL_UNSAFE)
# The default value is "open" and it can be changed to "creator" if ZK security is enabled
#
# high-availability.zookeeper.client.acl: open

#==============================================================================
# Fault tolerance and checkpointing
#==============================================================================

# The backend that will be used to store operator state checkpoints if
# checkpointing is enabled.
#
# Supported backends are 'jobmanager', 'filesystem', 'rocksdb', or the
# <class-name-of-factory>.
#
state.backend: filesystem

# Directory for checkpoints filesystem, when using any of the default bundled
# state backends.
#
state.checkpoints.dir: hdfs://flink12/flink-checkpoints

# Default target directory for savepoints, optional.
#
# state.savepoints.dir: hdfs://namenode-host:port/flink-savepoints

# Flag to enable/disable incremental checkpoints for backends that
# support incremental checkpoints (like the RocksDB state backend). 
#
# state.backend.incremental: false

# The failover strategy, i.e., how the job computation recovers from task failures.
# Only restart tasks that may have been affected by the task failure, which typically includes
# downstream tasks and potentially upstream tasks if their produced data is no longer available for consumption.

jobmanager.execution.failover-strategy: region

#==============================================================================
# Rest & web frontend
#==============================================================================

# The port to which the REST client connects to. If rest.bind-port has
# not been specified, then the server will bind to this port as well.
#
rest.port: 8081

# The address to which the REST client will connect to
#
#rest.address: 0.0.0.0

# Port range for the REST and web server to bind to.
#
#rest.bind-port: 8080-8090

# The address that the REST & web server binds to
#
#rest.bind-address: 0.0.0.0

# Flag to specify whether job submission is enabled from the web-based
# runtime monitor. Uncomment to disable.

web.submit.enable: true

#==============================================================================
# Advanced
#==============================================================================

# Override the directories for temporary files. If not specified, the
# system-specific Java temporary directory (java.io.tmpdir property) is taken.
#
# For framework setups on Yarn or Mesos, Flink will automatically pick up the
# containers' temp directories without any need for configuration.
#
# Add a delimited list for multiple directories, using the system directory
# delimiter (colon ':' on unix) or a comma, e.g.:
#     /data1/tmp:/data2/tmp:/data3/tmp
#
# Note: Each directory entry is read from and written to by a different I/O
# thread. You can include the same directory multiple times in order to create
# multiple I/O threads against that directory. This is for example relevant for
# high-throughput RAIDs.
#
# io.tmp.dirs: /tmp

# The classloading resolve order. Possible values are 'child-first' (Flink's default)
# and 'parent-first' (Java's default).
#
# Child first classloading allows users to use different dependency/library
# versions in their application than those in the classpath. Switching back
# to 'parent-first' may help with debugging dependency issues.
#
# classloader.resolve-order: child-first

# The amount of memory going to the network stack. These numbers usually need 
# no tuning. Adjusting them may be necessary in case of an "Insufficient number
# of network buffers" error. The default min is 64MB, the default max is 1GB.
# 
# taskmanager.memory.network.fraction: 0.1
# taskmanager.memory.network.min: 64mb
# taskmanager.memory.network.max: 1gb

#==============================================================================
# Flink Cluster Security Configuration
#==============================================================================

# Kerberos authentication for various components - Hadoop, ZooKeeper, and connectors -
# may be enabled in four steps:
# 1. configure the local krb5.conf file
# 2. provide Kerberos credentials (either a keytab or a ticket cache w/ kinit)
# 2. make the credentials available to various JAAS login contexts
# 4. configure the connector to use JAAS/SASL

# The below configure how Kerberos credentials are provided. A keytab will be used instead of
# a ticket cache if the keytab path and principal are set.

# security.kerberos.login.use-ticket-cache: true
# security.kerberos.login.keytab: /path/to/kerberos/keytab
# security.kerberos.login.principal: flink-user

# The configuration below defines which JAAS login contexts

# security.kerberos.login.contexts: Client,KafkaClient

#==============================================================================
# ZK Security Configuration
#==============================================================================

# Below configurations are applicable if ZK ensemble is configured for security

# Override below configuration to provide custom ZK service name if configured
# zookeeper.sasl.service-name: zookeeper

# The configuration below must match one of the values set in "security.kerberos.login.contexts"
# zookeeper.sasl.login-context-name: Client

#==============================================================================
# HistoryServer
#==============================================================================

# The HistoryServer is started and stopped via bin/historyserver.sh (start|stop)

# Directory to upload completed jobs to. Add this directory to the list of
# monitored directories of the HistoryServer as well (see below).
jobmanager.archive.fs.dir: hdfs:///flink12/completed-jobs/

# The address under which the web-based HistoryServer listens.
#historyserver.web.address: 0.0.0.0

# The port under which the web-based HistoryServer listens.
historyserver.web.port: 8082

# Comma separated list of directories to monitor for completed jobs.
historyserver.archive.fs.dir: hdfs:///flink12/completed-jobs/

# Interval in milliseconds for refreshing the monitored directories.
historyserver.archive.fs.refresh-interval: 10000
historyserver.web.tmpdir:/tmp/flinkhistoryserver12/

# jobmanager debug端口
# env.java.opts.jobmanager: "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5006"
# taskmanager debug端口
# env.java.opts.taskmanager: "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
```

#### 2.3.2	masters

```shell
[root@node1 conf]# vi masters
node1:8081
node2:8081
```

#### 2.3.3	workers

```shell
[root@node1 conf]# vi workers
node1
node2
node3
```

#### 2.3.4	zoo.cfg

```shell
[root@node1 conf]# vi zoo.cfg
################################################################################
#  Licensed to the Apache Software Foundation (ASF) under one
#  or more contributor license agreements.  See the NOTICE file
#  distributed with this work for additional information
#  regarding copyright ownership.  The ASF licenses this file
#  to you under the Apache License, Version 2.0 (the
#  "License"); you may not use this file except in compliance
#  with the License.  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
# limitations under the License.
################################################################################

# The number of milliseconds of each tick
tickTime=2000

# The number of ticks that the initial  synchronization phase can take
initLimit=10

# The number of ticks that can pass between  sending a request and getting an acknowledgement
syncLimit=5

# The directory where the snapshot is stored.
dataDir=/data/data/zookeeper/flinkdata

# The port at which the clients will connect
clientPort=2181

# ZooKeeper quorum peers
#server.1=localhost:2888:3888
# server.2=host:peer-port:leader-port
server.1=node1:2888:3888
server.2=node2:2888:3888
server.3=node3:2888:3888
```

#### 2.3.5	core-site.xml

```shell
[root@node1 hadoop]# cp core-site.xml /data/app/hadoop/flink-1.12.2/conf/
```

#### 2.3.6	hdfs-site.xml

```shell
[root@node1 hadoop]# cp hdfs-site.xml /data/app/hadoop/flink-1.12.2/conf/
```

#### 2.3.7	yarn-site.xml

```shell
[root@node1 hadoop]# cp yarn-site.xml /data/app/hadoop/flink-1.12.2/conf/
```

#### 2.3.8	hbase-site.xml

```shell
[root@node1 hadoop]# cd /data/app/hadoop/hbase-1.4.3/conf/
[root@node1 conf]# cp hbase-site.xml /data/app/hadoop/flink-1.12.2/conf/
```

### 2.4	分发其他节点

```shell
[root@node1 conf]# scp -r /data/app/hadoop/flink-1.12.2/ root@node2:/data/app/hadoop/
[root@node1 conf]# scp -r /data/app/hadoop/flink-1.12.2/ root@node3:/data/app/hadoop/
```

### 2.5	启动

```shell
[root@node1 data]# nohup yarn-session.sh -n 3 &
```

​	node1:

```shell
[root@node1 flink-1.12.2]# jps
23744 NodeManager
22227 JournalNode
25395 HRegionServer
2101 QuorumPeerMain
25733 Jps
25191 HMaster
4888 DFSZKFailoverController
21801 NameNode
21948 DataNode
24159 FlinkYarnSessionCli
```

​	node2:

```shell
[root@node2 ~]# jps
25936 NodeManager
28064 Jps
25697 ResourceManager
10198 DFSZKFailoverController
23975 NameNode
24231 JournalNode
27850 HRegionServer
8204 QuorumPeerMain
24092 DataNode
```

​	node3:

```shell
[root@node3 ~]# jps
11057 ResourceManager
13092 HRegionServer
11305 NodeManager
12377 YarnSessionClusterEntrypoint
9754 JournalNode
13306 Jps
9613 DataNode
30735 QuorumPeerMain
```

### 2.6	测试

```shell
[root@node1 flink-1.12.2]# flink run examples/batch/WordCount.jar 
```

### 2.7	UI界面

​	http://192.168.123.156:8088/cluster/scheduler

​	http://192.168.123.156:8081/#/overview

### 2.8	启动History Server

```shell
[root@node1 flink-1.12.2]# historyserver.sh start
Starting historyserver daemon on host node1.
```

### 2.9	停止

```shell
yarn application -kill application_对应的ID
```