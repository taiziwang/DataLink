# 第一篇：Centos7 Hadoop 2.7.7 集群部署

## 1.	准备工作

### 1.1	梳理服务器节点

#### 1.1.1	节点信息

| 序号 |       IP        | 管理员账号  | 操作系统  | 核数 | 内存 | 磁盘 | HOST别名 |
| :--: | :-------------: | ----------- | :-------: | :--: | :--: | :--: | :------: |
|  1   | 192.168.123.156 | root/123456 | Linux 7.6 | 16C  | 32G  |  2T  |  node1   |
|  2   | 192.168.123.157 | root/123456 | Linux 7.6 | 16C  | 32G  |  2T  |  node2   |
|  3   | 192.168.123.158 | root/123456 | Linux 7.6 | 16C  | 32G  |  2T  |  node3   |

#### 1.1.1	验证操作系统发行版本

```shell
[root@node1 ~]# uname -a
Linux node1 3.10.0-957.el7.x86_64 #1 SMP Thu Oct 4 20:48:51 UTC 2018 x86_64 x86_64 x86_64 GNU/Linux
```

​	由Linux和el7可得真实操作系统符合要求。

#### 1.1.2	查看节点磁盘挂载情况

```shell
[root@node1 ~]# df -h
文件系统                     容量  已用  可用 已用% 挂载点
/dev/mapper/rhel-root         44G  5.0G   40G   12% /
devtmpfs                      16G     0   16G    0% /dev
tmpfs                         16G     0   16G    0% /dev/shm
tmpfs                         16G  9.6M   16G    1% /run
tmpfs                         16G     0   16G    0% /sys/fs/cgroup
/dev/sda1                   1014M  177M  838M   18% /boot
/dev/mapper/data_vg-data_lv  2.0T   81M  1.9T    1% /data
tmpfs                        3.2G   16K  3.2G    1% /run/user/42
tmpfs                        3.2G     0  3.2G    0% /run/user/0
```

​	可知 2T 挂载在 data 目录下，所以接下来的部署主要在 data 目录下进行。

#### 1.1.3	检测网络环境

```shell
[root@node1 ~]# ping 202.108.22.5
PING 202.108.22.5 (202.108.22.5) 56(84) bytes of data.
^C
--- 202.108.22.5 ping statistics ---
36 packets transmitted, 0 received, 100% packet loss, time 34999ms
```

​	Ping外网百度IP，结果失败，说明内网环境。

```shell
[root@node1 ~]# systemctl status firewalld.service
● firewalld.service - firewalld - dynamic firewall daemon
   Loaded: loaded (/usr/lib/systemd/system/firewalld.service; disabled; vendor preset: enabled)
   Active: inactive (dead)
     Docs: man:firewalld(1)

4月 21 09:06:52 node1 systemd[1]: Starting firewalld - dynamic firewall daemon...
4月 21 09:06:53 node1 systemd[1]: Started firewalld - dynamic firewall daemon.
4月 21 10:10:21 node1 systemd[1]: Stopping firewalld - dynamic firewall daemon...
4月 21 10:10:21 node1 systemd[1]: Stopped firewalld - dynamic firewall daemon.

```

​	查看防火墙状态，关闭即可。

### 1.2	配置环境

#### 1.2.1	配置Host别名

```shell
[root@node1 home]# vi /etc/hosts
192.168.123.156 node1
192.168.123.157 node2
192.168.123.158 node3
```



#### 1.2.2	配置免密登录

> 参考网址：https://www.cnblogs.com/fisher01/p/13721418.html

```shell
[root@node1 home]# ssh-keygen
Generating public/private rsa key pair.
Enter file in which to save the key (/root/.ssh/id_rsa): 
Enter passphrase (empty for no passphrase): 
Enter same passphrase again: 
Your identification has been saved in /root/.ssh/id_rsa.
Your public key has been saved in /root/.ssh/id_rsa.pub.
The key fingerprint is:
SHA256:149XI3irZ4EF4q0Rnj5Ux5WANKW6dFO/kFQN7xpDq6g root@node1
The key's randomart image is:
+---[RSA 2048]----+
|          .o+o++o|
|         o +oo.o.|
|        o *.oo. .|
|         *.o+ooo |
|        So+++=+oo|
|        .=ooo=+=o|
|         .o o.=. |
|         .  .+   |
|        E  .o    |
+----[SHA256]-----+
[root@node1 home]# cat /root/.ssh/id_rsa.pub >> authorized_keys
[root@node1 home]# ssh-copy-id 192.168.123.157
/usr/bin/ssh-copy-id: INFO: Source of key(s) to be installed: "/root/.ssh/id_rsa.pub"
/usr/bin/ssh-copy-id: INFO: attempting to log in with the new key(s), to filter out any that are already installed
/usr/bin/ssh-copy-id: INFO: 1 key(s) remain to be installed -- if you are prompted now it is to install the new keys
root@192.168.123.157's password: 

Number of key(s) added: 1

Now try logging into the machine, with:   "ssh '192.168.123.157'"
and check to make sure that only the key(s) you wanted were added.
[root@node1 home]# ssh-copy-id 192.168.123.158
```

#### 1.2.3	配置JDK

> 参考网址：https://blog.csdn.net/love3765/article/details/88783126

```shell
[root@node1 ~]# java -version
openjdk version "1.8.0_181"
OpenJDK Runtime Environment (build 1.8.0_181-b13)
OpenJDK 64-Bit Server VM (build 25.181-b13, mixed mode)
```

​	检测安装包：

```shell
[root@node1 ~]# rpm -qa | grep java
javapackages-tools-3.4.1-11.el7.noarch
tzdata-java-2018e-3.el7.noarch
python-javapackages-3.4.1-11.el7.noarch
java-1.8.0-openjdk-1.8.0.181-7.b13.el7.x86_64
java-1.7.0-openjdk-1.7.0.191-2.6.15.5.el7.x86_64
java-1.7.0-openjdk-headless-1.7.0.191-2.6.15.5.el7.x86_64
java-1.8.0-openjdk-headless-1.8.0.181-7.b13.el7.x86_64
```

​	卸载openjdk：

```shell
[root@node1 ~]# rpm -e --nodeps tzdata-java-2018e-3.el7.noarch
[root@node1 ~]# rpm -e --nodeps java-1.8.0-openjdk-1.8.0.181-7.b13.el7.x86_64
[root@node1 ~]# rpm -e --nodeps java-1.7.0-openjdk-1.7.0.191-2.6.15.5.el7.x86_64
[root@node1 ~]# rpm -e --nodeps java-1.7.0-openjdk-headless-1.7.0.191-2.6.15.5.el7.x86_64
[root@node1 ~]# rpm -e --nodeps java-1.8.0-openjdk-headless-1.8.0.181-7.b13.el7.x86_64
```

​	验证卸载结果：

```shell
[root@node1 ~]# rpm -qa | grep java
javapackages-tools-3.4.1-11.el7.noarch
python-javapackages-3.4.1-11.el7.noarch
```

​	创建jdk目录：

```shell
[root@node1 ~]# mkdir /usr/local/java
```

​	上传jdk到该目录，然后远程复制到其他节点：

```shell
[root@node1 java]# scp /usr/local/java/jdk-8u291-linux-x64.tar.gz root@node2:/usr/local/java
jdk-8u291-linux-x64.tar.gz                                                                                          100%  138MB 138.2MB/s   00:01 
```

​	解压：

```shell
[root@node1 java]# tar -xf jdk-8u291-linux-x64.tar.gz
```

​	修改环境变量：

```shell
[root@node1 java]# vi /etc/profile
export JAVA_HOME=/usr/local/java/jdk1.8.0_291
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:$PATH
[root@node1 java]# source /etc/profile
[root@node1 java]# java -version
java version "1.8.0_291"
Java(TM) SE Runtime Environment (build 1.8.0_291-b10)
Java HotSpot(TM) 64-Bit Server VM (build 25.291-b10, mixed mode)
```

​	配置成功后，删除压缩包：

```shell
[root@node1 java]# rm -rf jdk-8u291-linux-x64.tar.gz 
```

#### 1.2.4	配置时间同步

> 参考网址：https://blog.csdn.net/apache2tomcat/article/details/89477732

​	现场时钟服务器为192.168.123.10

```shell
[root@node1 java]# ntpdate 192.168.123.10
21 Apr 15:01:19 ntpdate[23087]: adjust time server 10.10.10.53 offset -0.000252 sec
```

​	配置定时同步任务：

```shell
[root@node1 java]# crontab -e
* */1 * * * ntpdate 192.168.123.10
[root@node1 java]# /bin/systemctl reload crond.service
[root@node1 java]# crontab -l
* */1 * * * ntpdate 192.168.123.10
[root@node1 java]# /bin/systemctl start crond.service
```

## 2.	部署Hadoop高可用集群

> 参考网址：https://blog.csdn.net/qq_37554565/article/details/90411923

### 2.1	节点规划

| host  | namenode | datanode | resourcemanager | nodemanager | journalnode | DFSZKFailOverController |
| :---: | :------: | :------: | :-------------: | :---------: | :---------: | :---------------------: |
| node1 |    √     |    √     |                 |      √      |      √      |            √            |
| node2 |    √     |    √     |        √        |      √      |      √      |            √            |
| node3 |          |    √     |        √        |      √      |      √      |                         |

### 2.2	目录规划

```shell
[root@node1 data]# mkdir /data/app
[root@node1 data]# mkdir /data/app/hadoop
```

​	由于上文说到现场的磁盘挂载在了data目录下，所以本文及以后的文章均在data目录下进行，当然推荐在usr目录下进行。

### 2.3	部署Zookeeper

​	本文使用的zk版本为3.4.14

#### 	2.3.1	上传、分发、解压

```shell
[root@node1 hadoop]# scp /data/app/hadoop/zookeeper-3.4.14.tar.gz root@node2:/data/app/hadoop
[root@node1 hadoop]# scp /data/app/hadoop/zookeeper-3.4.14.tar.gz root@node3:/data/app/hadoop
[root@node1 hadoop]# tar -zxf zookeeper-3.4.14.tar.gz
```

#### 	2.3.2	修改配置文件

```shell
[root@node1 hadoop]# mv zookeeper-3.4.14 zookeeper
[root@node1 hadoop]# cd zookeeper/conf/
[root@node1 conf]# mv zoo_sample.cfg zoo.cfg
dataDir=/data/app/hadoop/zookeeper/data
server.0=node1:2888:3888
server.1=node2:2888:3888
server.2=node3:2888:3888
```

#### 	2.3.3	创建myid

​	(node1是0，nide2是1，node3是2)

```shell
[root@node1 zookeeper]# mkdir data
[root@node1 zookeeper]# cd data
[root@node1 data]# vi myid
0
```

#### 	2.3.4	配置环境变量

```shell
[root@node1 data]# vi /etc/profil
export ZOOKEEPER_HOME=/data/app/hadoop/zookeeper
export PATH=${JAVA_HOME}/bin:$PATH:$ZOOKEEPER_HOME/bin
[root@node1 data]# source /etc/profil
```

#### 	2.3.5	启动

```shell
[root@node1 ~]# zkServer.sh start
ZooKeeper JMX enabled by default
Using config: /data/app/hadoop/zookeeper/bin/../conf/zoo.cfg
Starting zookeeper ... STARTED
[root@node1 ~]# zkServer.sh status
ZooKeeper JMX enabled by default
Using config: /data/app/hadoop/zookeeper/bin/../conf/zoo.cfg
Mode: follower
```

​	此时，node2 为leader。删除所有节点的压缩包。

```shell
[root@node1 hadoop]# rm -rf zookeeper-3.4.14.tar.gz
```

### 2.4	部署Hadoop

​	本文使用的Hadoop版本为2.7.7。

#### 	2.4.1	上传、分发、解压

```shell
[root@node1 hadoop]# scp /data/app/hadoop/hadoop-2.7.7.tar.gz root@node2:/data/app/hadoop 
[root@node1 hadoop]# scp /data/app/hadoop/hadoop-2.7.7.tar.gz root@node3:/data/app/hadoop  
[root@node1 hadoop]# tar -zxf hadoop-2.7.7.tar.gz
```

#### 	2.4.2	配置环境变量

```shell
[root@node1 hadoop]# vi /etc/profile
export HADOOP_HOME=/data/app/hadoop/hadoop-2.7.7
export HADOOP_CONF_DIR=/data/app/hadoop/hadoop-2.7.7/etc/hadoop

export PATH=${JAVA_HOME}/bin:$PATH:$ZOOKEEPER_HOME/bin:$HADOOP_HOME/bin:$HADOOP_HOME/sbin
[root@node1 hadoop]# source /etc/profile
```

#### 	2.4.3	配置hadoop-env.sh文件

```shell
[root@node1 hadoop]# vi /data/app/hadoop/hadoop-2.7.7/etc/hadoop/hadoop-env.sh
```

#### 	2.4.4	配置yarn-env.sh文件

```shell
[root@node1 hadoop]# vi /data/app/hadoop/hadoop-2.7.7/etc/hadoop/yarn-env.sh
```

#### 	2.4.5	配置core-site.xml文件

```shell
[root@node1 hadoop]# vi /data/app/hadoop/hadoop-2.7.7/etc/hadoop/core-site.xml
<configuration>
<!--用来指定hdfs的老大，ns为固定属性名，表示两个namenode -->
        <property>
                <name>fs.defaultFS</name>
                <value>hdfs://ns</value>
        </property>
        <!--用来指定hadoop运行时产生的存放目录 -->
        <property>
                <name>hadoop.tmp.dir</name>
                <value>/data/app/hadoop/hadoop-2.7.7/tmp</value>
        </property>
        <!--流文件的缓冲区单位KB-->
        <property>
                <name>io.file.buffer.size</name>
                <value>4096</value>
        </property>
        <!--执行zookeeper地址 -->
        <property>
                <name>ha.zookeeper.quorum</name>
                <value>node1:2181,node2:2181,node3:2181</value>
        </property>
</configuration>
```

#### 	2.4.6	配置hdfs-site.xml

```shell
[root@node1 hadoop]# vi /data/app/hadoop/hadoop-2.7.7/etc/hadoop/hdfs-site.xml
<configuration>
    <!--执行hdfs的nameservice为ns,和core-site.xml保持一致-->
    <property>
        <name>dfs.nameservices</name>
        <value>ns</value>
    </property>

    <!--ns下有两个namenode,分别是nn1,nn2-->
    <property>
        <name>dfs.ha.namenodes.ns</name>
        <value>nn1,nn2</value>
    </property>
    
    <!--nn1的RPC通信地址-->
    <property>
        <name>dfs.namenode.rpc-address.ns.nn1</name>
        <value>node1:9000</value>
    </property>
        <!--nn1的http通信地址-->
    <property>
        <name>dfs.namenode.http-address.ns.nn1</name>
        <value>node1:50070</value>
    </property>
    <!--nn2的RPC通信地址-->
    <property>
        <name>dfs.namenode.rpc-address.ns.nn2</name>
        <value>node2:9000</value>
    </property>
    <!--nn2的http通信地址-->
    <property>
        <name>dfs.namenode.http-address.ns.nn2</name>
        <value>node2:50070</value>
    </property>
    <!--指定namenode的元数据在JournalNode上的存放位置,这样，namenode2可以 从jn集群里获取
最新的namenode的信息，达到热备的效果-->
    <property>
        <name>dfs.namenode.shared.edits.dir</name>
        <value>qjournal://node1:8485;node2:8485;node3:8485/ns</value>
    </property>
    <!--指定JournalNode存放数据的位置-->
    <property>
        <name>dfs.journalnode.edits.dir</name>
        <value>/data/app/hadoop/hadoop-2.7.7/journal</value>
    </property>
    <!--开启 namenode 故障时自动切换-->
    <property>
        <name>dfs.ha.automatic-failover.enabled.ns</name>
        <value>true</value>
    </property>
    <!--配置切换的实现方式-->
    <property>
        <name>dfs.client.failover.proxy.provider.ns</name>
        <value>org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider</value>
    </property>
    <!--配置隔离机制-->
    <property>
        <name>dfs.ha.fencing.methods</name>
        <value>sshfence</value>
    </property>
    <!--配置隔离机制的ssh登录秘钥所在的位置-->
    <property>
        <name>dfs.ha.fencing.ssh.private-key-files</name>
        <value>/root/.ssh/id_rsa</value>
    </property>
    <!--配置namenode数据存放的位置,可以不配置，如果不配置，默认用的是core-site.xml里配置的hadoop.tmp.dir的路径-->
    <property>
        <name>dfs.namenode.name.dir</name>
        <value>file:///data/app/hadoop/hadoop-2.7.7/tmp/namenode</value>
    </property>
    <!--配置datanode数据存放的位置,可以不配置，如果不配置，默认用的是core-site.xml里配置的hadoop.tmp.dir的路径-->
    <property>
        <name>dfs.datanode.data.dir</name>
        <value>file:///data/app/hadoop/hadoop-2.7.7/tmp/datanode</value>
    </property>
    <!--配置block副本数量-->
    <property>
        <name>dfs.replication</name>
        <value>3</value>
    </property>
    <!--设置 hdfs 的操作权限， false 表示任何用户都可以在 hdfs 上操作文件-->
    <property>
        <name>dfs.permissions</name>
        <value>false</value>
    </property>
</configuration>
```

#### 	2.4.7	配置mapred-site.xml文件

```shell
[root@node1 hadoop]# mv mapred-site.xml.template mapred-site.xml
[root@node1 hadoop]# vi /data/app/hadoop/hadoop-2.7.7/etc/hadoop/mapred-site.xml
<configuration>
    <!-- 指定mr框架为yarn方式 -->
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
    </property>
</configuration>
```

#### 	2.4.8	配置yarn-site.xml文件

```shell
[root@node1 hadoop]# vi /data/app/hadoop/hadoop-2.7.7/etc/hadoop/yarn-site.xml
<configuration>

<!-- Site specific YARN configuration properties -->
    <!--开启YARN HA -->
    <property>
        <name>yarn.resourcemanager.ha.enabled</name>
        <value>true</value>
    </property>
    <!--指定两个 resourcemanager 的名称-->
    <property>
        <name>yarn.resourcemanager.ha.rm-ids</name>
        <value>rm1,rm2</value>
    </property>
    <!--配置rm1，rm2的主机-->
    <property>
        <name>yarn.resourcemanager.hostname.rm1</name>
        <value>node3</value>
    </property>
    <property>
        <name>yarn.resourcemanager.hostname.rm2</name>
        <value>node2</value>
    </property>
        <!--开启yarn恢复机制-->
    <property>
        <name>yarn.resourcemanager.recovery.enabled</name>
        <value>true</value>
    </property>
    <!--配置zookeeper的地址-->
    <property>
        <name>yarn.resourcemanager.zk-address</name>
        <value>node1:2181,node2:2181,node3:2181</value>
        <description>For multiple zk services, separate them with comma</description>
    </property>
        <!--指定YARN HA的名称-->
    <property>
        <name>yarn.resourcemanager.cluster-id</name>
        <value>yarn-ha</value>
    </property>
    <property>
    <!--指定yarn的老大resoucemanager的地址-->
        <name>yarn.resourcemanager.hostname</name>
        <value>node1</value>
    </property>
    <!--NodeManager 获取数据的方式-->
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>
    <property>
        <name>yarn.nodemanager.aux-services.mapreduce.shuffle.class</name>
        <value>org.apache.hadoop.mapred.ShuffleHandler</value>
    </property>
     <property>
        <name>yarn.nodemanager.resource.cpu-vcores</name>
        <value>32</value>
    </property>
</configuration>
```

#### 	2.4.9	配置slaves文件

```shell
[root@node1 hadoop]# vi /data/app/hadoop/hadoop-2.7.7/etc/hadoop/slaves
node1
node2
node3
```

#### 	2.4.10	创建配置目录

​	在   /data/app/hadoop/hadoop-2.7.7/下创建 tmp目录

```shell
[root@node1 hadoop-2.7.7]# mkdir tmp
```

​	在   /data/app/hadoop/hadoop-2.7.7/ 下创建 journal

```shell
[root@node1 hadoop-2.7.7]# mkdir journal
```

​	在   /data/app/hadoop/hadoop-2.7.7/tmp 下创建 namenode 和dataname

```shell
[root@node1 hadoop-2.7.7]# cd tmp
[root@node1 tmp]# mkdir namenode
[root@node1 tmp]# mkdir datanode
```

#### 	2.4.11	通过scp 命令 将hadoop传给另外两台服务器，并配置环境变量

```shell
[root@node1 hadoop]# scp -r hadoop-2.7.7/ root@node2:/data/app/hadoop/
[root@node1 hadoop]# scp -r hadoop-2.7.7/ root@node3:/data/app/hadoop/
```

#### 	2.4.12	启动进程

​	1.确认所有节点的Zookeeper是否启动：

```shell
[root@node1 tmp]# zkServer.sh status
```

​	2.在某一个namenode节点（node1或者node2）执行如下命令，创建命名空间

```shell
[root@node1 tmp]# hdfs zkfc -formatZK
```

​	3.在每个journalnode节点（node1和node2和node3）用如下命令启动journalnode

```shell
[root@node1 hadoop-2.7.7]# sbin/hadoop-daemon.sh start journalnode
```

​	4.在主namenode节点（node1）格式化namenode和journalnode目录

```shell
[root@node1 hadoop-2.7.7]# hdfs namenode -format ns
```

​	5.在主namenode节点（node1）启动namenode进程

```shell
[root@node1 hadoop-2.7.7]# sbin/hadoop-daemon.sh start namenode
```

​	6.在备namenode节点（node2）执行第一行命令，这个是把备namenode节点的目录格式化并把元数据从主namenode节点copy过来，并且这个命令不会把journalnode目录再格式化了！然后用第二个命令启动备namenode进程！

```shell
[root@node1 hadoop-2.7.7]# hdfs namenode -bootstrapStandby
[root@node1 hadoop-2.7.7]# sbin/hadoop-daemon.sh start namenode
```

​	7.在两个namenode节点（node1和node2）都执行以下命令

```shell
[root@node1 hadoop-2.7.7]# sbin/hadoop-daemon.sh start zkfc
```

​	8.在所有datanode节点都执行以下命令启动datanode

```shell
[root@node1 hadoop-2.7.7]# sbin/hadoop-daemon.sh start datanode
```

​	9.当前所有节点进程

​	node1的进程

```shell
[root@node1 hadoop-2.7.7]# jps
25296 QuorumPeerMain
22337 JournalNode
22865 DFSZKFailoverController
22646 NameNode
23115 Jps
23006 DataNode
```

​	node2的进程

```shell
[root@node2 hadoop-2.7.7]# jps
22289 NameNode
22450 DFSZKFailoverController
22006 JournalNode
22572 DataNode
22716 Jps
24910 QuorumPeerMain
```

​	node3的进程

```shell
[root@node3 hadoop-2.7.7]# jps
22240 DataNode
24867 QuorumPeerMain
21948 JournalNode
22381 Jps
```

​	10.启动yarn的resourcemanager的进程

在node3上启动resourcemanager

```shell
[root@node3 sbin]# sh yarn-daemon.sh start resourcemanager
```

在node2上启动resourcemanager

```shell
[root@node2 sbin]# sh yarn-daemon.sh start resourcemanager
```

​	11.所有节点上启动nodemanager进程

```shell
[root@node1 sbin]# sh yarn-daemon.sh start nodemanager
```

​	12.当前所有节点的进程

​	node1：

```shell
[root@node1 sbin]# jps
25296 QuorumPeerMain
22337 JournalNode
22865 DFSZKFailoverController
23585 Jps
22646 NameNode
23006 DataNode
23358 NodeManager
```

​	node2：

```shell
[root@node2 sbin]# jps
22289 NameNode
22450 DFSZKFailoverController
22006 JournalNode
22967 ResourceManager
23067 NodeManager
22572 DataNode
24910 QuorumPeerMain
23326 Jps
```

​	node3：

```shell
[root@node3 sbin]# jps
22240 DataNode
24867 QuorumPeerMain
23078 Jps
21948 JournalNode
22525 ResourceManager
22845 NodeManager
```

​	13.查看resourcemanager的主从

```shell
[root@node3 sbin]# yarn rmadmin -getServiceState rm1
active
[root@node3 sbin]# yarn rmadmin -getServiceState rm2
standby
```

#### 2.4.13	查看相关网页

​	1.http://192.168.123.156:50070

​	2.http://192.168.123.157:50070

​	3.http://192.168.123.158:8088

#### 2.4.14	集群停止

1. 在hadoop的sbin下sh stop-all.sh 关闭集群全部实例。

   ```shell
   sh stop-all.sh
   ```

2. 如有残余，可在相应节点执行

```shell
sh hadoop-daemon.sh stop DataNode
sh hadoop-daemon.sh stop NameNode
sh hadoop-daemon.sh stop JournalNode
```

​	或以下快捷命令：

```shell
sh stop-all.sh
sh stop-dfs.sh
sh stop-yarn.sh
yarn-daemon.sh stop resourcemanager
```

#### 2.4.15 集群启动

```shell
[root@node1 sbin]# sh start-all.sh
[root@node1 sbin]# cd ..
[root@node1 hadoop-2.7.7]# sbin/hadoop-daemon.sh start zkfc
[root@node2 bin]# cd /data/app/hadoop/hadoop-2.7.7/
[root@node2 hadoop-2.7.7]# sbin/hadoop-daemon.sh start zkfc
[root@node2 hadoop-2.7.7]# cd sbin/
[root@node2 sbin]# sh yarn-daemon.sh start resourcemanager
[root@node3 ~]# cd /data/app/hadoop/hadoop-2.7.7/sbin/
[root@node3 sbin]# sh yarn-daemon.sh start resourcemanager
```

### 2.5	调优Hadoop

#### 2.5.1	优化调度队列

​	修改yarn-site.xml：

```shell
[root@node3 hadoop]# vi yarn-site.xml
<!--  指定使用fairScheduler的调度方式  -->
	<property>
		   <name>yarn.resourcemanager.scheduler.class</name>
		   <value>org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.FairScheduler</value>
	</property>
	 
	<!--  指定配置文件路径  -->
	<property>
		   <name>yarn.scheduler.fair.allocation.file</name>
		   <value>/data/app/hadoop/hadoop-2.7.7/etc/hadoop/fair-scheduler.xml</value>
	</property>
	 
	<!-- 是否启用资源抢占，如果启用，那么当该队列资源使用
	yarn.scheduler.fair.preemption.cluster-utilization-threshold 这么多比例的时候，就从其他空闲队列抢占资源
	  -->
	<property>
		   <name>yarn.scheduler.fair.preemption</name>
		   <value>true</value>
	</property>
	 
	<property>
		   <name>yarn.scheduler.fair.preemption.cluster-utilization-threshold</name>
		   <value>0.8f</value>
	</property>
	 
	<!-- 默认提交到default队列  -->
	<property>
		   <name>yarn.scheduler.fair.user-as-default-queue</name>
		   <value>true</value>
	</property>
	 
	<!-- 如果提交一个任务没有到任何的队列，是否允许创建一个新的队列，设置false不允许  -->
	<property>
		   <name>yarn.scheduler.fair.allow-undeclared-pools</name>
		   <value>false</value>
	</property>
```

​	添加fair-scheduler.xml：

```shell
[root@node3 hadoop]# vi fair-scheduler.xml
<?xml version="1.0"?>
<allocations>
<!-- users max running apps  -->
<userMaxAppsDefault>30</userMaxAppsDefault>
<!-- 定义队列  -->
<queue name="root">
         <minResources>512mb,4vcores</minResources>
         <maxResources>102400mb,100vcores</maxResources>
         <maxRunningApps>100</maxRunningApps>
         <weight>1.0</weight>
         <schedulingMode>fair</schedulingMode>
         <aclSubmitApps> </aclSubmitApps>
         <aclAdministerApps> </aclAdministerApps>
 
         <queue name="default">
                   <minResources>1024mb,4vcores</minResources>
                   <maxResources>30720mb,30vcores</maxResources>
                   <maxRunningApps>100</maxRunningApps>
                   <schedulingMode>fair</schedulingMode>
                   <weight>5.0</weight>
                   <!--  所有的任务如果不指定任务队列，都提交到default队列里面来 -->
                   <aclSubmitApps>*</aclSubmitApps>
         </queue>
 
<!--
 
weight
资源池权重
 
aclSubmitApps
允许提交任务的用户名和组；
格式为： 用户名 用户组
 
当有多个用户时候，格式为：用户名1,用户名2 用户名1所属组,用户名2所属组
 
aclAdministerApps
允许管理任务的用户名和组；
 
格式同上。
 -->
         <queue name="hadoop">
                   <minResources>512mb,4vcores</minResources>
                   <maxResources>20480mb,20vcores</maxResources>
                   <maxRunningApps>100</maxRunningApps>
                   <schedulingMode>fair</schedulingMode>
                   <weight>2.0</weight>
                   <aclSubmitApps>hadoop</aclSubmitApps>
                   <aclAdministerApps>hadoop</aclAdministerApps>
         </queue>
 
         <queue name="develop">
                   <minResources>512mb,4vcores</minResources>
                   <maxResources>20480mb,20vcores</maxResources>
                   <maxRunningApps>100</maxRunningApps>
                   <schedulingMode>fair</schedulingMode>
                   <weight>1</weight>
                   <aclSubmitApps>develop</aclSubmitApps>
                   <aclAdministerApps>develop</aclAdministerApps>
         </queue>
 
         <queue name="test1">
                   <minResources>512mb,4vcores</minResources>
                   <maxResources>20480mb,20vcores</maxResources>
                   <maxRunningApps>100</maxRunningApps>
                   <schedulingMode>fair</schedulingMode>
                   <weight>1</weight>
                   <aclSubmitApps>test1,hadoop,develop test1</aclSubmitApps>
                   <aclAdministerApps>test1 group_businessC,supergroup</aclAdministerApps>
         </queue>
</queue>
</allocations>
```

重启yarn：

```shell
[root@node1 sbin]# sh stop-yarn.sh
[root@node1 sbin]# sh start-yarn.sh
```

