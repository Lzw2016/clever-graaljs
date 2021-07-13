#!/bin/bash
#time=`date +%Y-%m-%d.%H-%M`
ProjectName=fast-api-demo

### 启动Java进程
java_pid=`ps -ef | grep "DappName=${ProjectName}" | grep -v 'grep' | awk '{print $2}'`
if [ -z $java_pid ];then
  #-------------------------------------------------------------------
  JAVA_MEM_OPTS=" -DappName=${ProjectName} -server -Xmx2g -Xms2g"
  DATABASE_OPTS=" -Ddatabase.codeset=ISO-8859-1 -Ddatabase.logging=false"
  JAVA_OPTS_EXT=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Dapplication.codeset=UTF-8 -Dfile.encoding=UTF-8"
  #-------------------------------------------------------------------
  java $JAVA_MEM_OPTS $DATABASE_OPTS $JAVA_OPTS_EXT -jar ./fast-api-demo.jar --spring.config.location=./application.yml >/dev/null 2>&1 &
  echo "$ProjectName 启动成功!"
else
  echo "$ProjectName 正在运行..."
fi

echo "查看日志:  tail -F ./logs/fast-api-demo/fast-api-demo.log -n 100"
