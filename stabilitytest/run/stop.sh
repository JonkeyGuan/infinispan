#!/bin/bash

count=`ps -ef |grep java |grep com.test.datagrid.Run | grep -v grep | wc -l`
if [ "${count}" == "0" ]; then
  echo " com.test.datagrid.Run is not running. Exiting... "
  exit 1;
fi

echo "Stopping ... "
for pid in $(ps axu |grep java |grep com.test.datagrid.Run | grep -v grep |awk '{print $2}');
do
   kill $pid;
done

for ((i=1; i<20; i++))
do
    sleep 6;
    count=`ps -ef | grep java |  grep com.test.datagrid.Run |grep -v grep |wc -l`
    if [ "${count}" == "0" ]; then
        echo -e "com.test.datagrid.Run Process Stop \033[32m  [Success]  \033[0m "
        exit 0;
    else
        continue;
    fi
done

echo -e " com.test.datagrid.Run Process Stop \033[31m  [Failure] \033[0m "
exit 3;

