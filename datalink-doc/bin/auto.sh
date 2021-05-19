#!/bin/bash
# 定义变量
VERSION="0.1-SNAPSHOT"
PREFIX="datalink"
SUFFIX="-${VERSION}.jar"
LIB_PATH="$(dirname $(pwd))/lib"
JAR_PATH="$(dirname $(pwd))/module/"${PREFIX}"-"
JARS_NAME=("gateway" "uaa" "user")
XMS=("128M" "128M" "128M")
XMX=("512M" "512M" "512M")
XXPS=("128M" "128M" "128M")
XXMPS=("256M" "256M" "256M")
JAR_NAME="gateway"
OPERATION=$1
TYPE=$2

# 如果输入格式不对，给出提示！
tips() {
	echo ""
	echo "WARNING!!!......Tips, please use command: sh auto.sh [start|stop|restart|status] [all|base|gateway|uaa|user].   For example: sh auto.sh start base "
	echo ""
	exit 1
}

runjar(){
	# 重新获取一下pid，因为其它操作如stop会导致pid的状态更新
	pid=`ps -ef | grep ${PREFIX}-${JAR_NAME} | grep -v grep | awk '{print $2}'`
				# -z 表示如果$pid为空时执行
	if [ -z $pid ]; then
		nohup java -Djava.ext.dirs=${LIB_PATH} -Doracle.jdbc.thinLogonCapability=o3 -jar -Xms${XMS[$1]} -Xmx${XMX[$1]} -XX:PermSize=${XXPS[$1]} -XX:MaxPermSize=${XXMPS[$1]} ${JAR_PATH}${JAR_NAME}/${PREFIX}-${JAR_NAME}${SUFFIX} > /dev/null 2>&1 &
		pid=`ps -ef | grep ${PREFIX}-${JAR_NAME}${SUFFIX} | grep -v grep | awk '{print $2}'`
		echo ""
		echo "Service ${JAR_NAME} is starting！pid=${pid}"
		echo "........................Start successfully！........................."
	else
		echo ""
		echo "Service ${JAR_NAME} is already running,it's pid = ${pid}. If necessary, please use command: sh auto.sh restart ${JAR_NAME}."
		echo ""
	fi
}

# 启动方法
start() {
	int=0
	while(( $int<${#JARS_NAME[*]} ))
	do
		if [ $JAR_NAME == ${JARS_NAME[$int]} ]; then
			runjar $int
			break
		else
			let "int++"
		fi
	done
}
 
# 停止方法
stop() {
		# 重新获取一下pid，因为其它操作如start会导致pid的状态更新
	pid=`ps -ef | grep ${PREFIX}-${JAR_NAME} | grep -v grep | awk '{print $2}'`
        # -z 表示如果$pid为空时执行。 注意：每个命令和变量之间一定要前后加空格，否则会提示command找不到
	if [ -z $pid ]; then
		echo ""
        echo "Service ${JAR_NAME} is not running! It's not necessary to stop it!"
		echo ""
	else
		kill -9 $pid
		echo ""
		echo "Service stop successfully！pid:${pid} which has been killed forcibly!"
		echo ""
	fi
}
 
# 输出运行状态方法
status() {
        # 重新获取一下pid，因为其它操作如stop、restart、start等会导致pid的状态更新
	pid=`ps -ef | grep ${PREFIX}-${JAR_NAME} | grep -v grep | awk '{print $2}'`
        # -z 表示如果$pid为空时执行。注意：每个命令和变量之间一定要前后加空格，否则会提示command找不到
	if [ -z $pid ];then
		echo ""
        echo "Service ${JAR_NAME} is not running!"
		echo ""
	else
		echo ""
        echo "Service ${JAR_NAME} is running. It's pid=${pid}"
		echo ""
	fi
}
 
# 重启方法
restart() {
	echo ""
	echo ".............................Restarting.............................."
	echo "....................................................................."
		# 重新获取一下pid，因为其它操作如start会导致pid的状态更新
	pid=`ps -ef | grep ${PREFIX}-${JAR_NAME} | grep -v grep | awk '{print $2}'`
        # -z 表示如果$pid为空时执行。 注意：每个命令和变量之间一定要前后加空格，否则会提示command找不到
	if [ ! -z $pid ]; then
		kill -9 $pid
	fi
	start
	echo "....................Restart successfully！..........................."
}


runcase(){
	case "${OPERATION}" in
	   "start")
		 start
		 ;;
	   "stop")
		 stop
		 ;;
	   "status")
		 status
		 ;;
	   "restart")
		 restart
		 ;;
	   *)
		 tips
		 ;;
	esac
}

runone(){
	JAR_NAME=$TYPE
	runcase
}

runbase(){
	JAR_NAME="gateway"
	runcase
	JAR_NAME="uaa"
	runcase
	JAR_NAME="user"
	runcase
}

runall(){
	int=0
	while(( $int<${#JARS_NAME[*]} ))
	do
		JAR_NAME=${JARS_NAME[$int]}
		runcase
		let "int++"
	done
}

way(){
	case "${TYPE}" in
	   "all")
		 runall
		 ;;
	   "base")
		 runbase
		 ;;
	   *)
		 runone
		 ;;
	 esac
}
# 根据输入参数执行对应方法，不输入则执行tips提示方法
case "$1" in
   "start")
	 way
     ;;
   "stop")
     way
     ;;
   "status")
     way
     ;;
   "restart")
     way
     ;;
   *)
     tips
     ;;
esac