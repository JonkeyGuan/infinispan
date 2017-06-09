@echo off

set "myDir=%~dp0"


java -cp "%myDir%lib\*;%myDir%conf" com.test.datagrid.Run 1000 test1

pause


