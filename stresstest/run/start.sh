#!/bin/bash

myDir=`dirname "$0"`

nohup java -cp "${myDir}/lib/*:${myDir}/conf" com.test.datagrid.Run 10 >/dev/null 2>&1 &

sleep 3;
count=`ps -ef | grep java | grep com.test.datagrid.Run |grep -v grep |wc -l`
if [ "${count}" = "0" ]; then
    echo -e " \033[31m  [com.test.datagrid.Run Process Start - Failure] \033[0m " 
else
    echo -e " \033[32m  [com.test.datagrid.Run Start - Success] \033[0m " 
fi

