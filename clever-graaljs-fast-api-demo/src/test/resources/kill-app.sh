#!/bin/bash
ProjectName=fast-api-demo

### 停止Java进程
java_pid=`ps -ef | grep "DappName=${ProjectName}" | grep -v 'grep' | awk '{print $2}'`
if [ -z $java_pid ];then
  echo "$ProjectName 未运行"
else
  ps -ef | grep "DappName=${ProjectName}" | grep -v 'grep' | awk '{print $2}' | xargs kill
  echo "$ProjectName 已停止!"
fi
