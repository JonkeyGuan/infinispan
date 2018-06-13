@echo off

set "myDir=%~dp0"


java -cp "%myDir%lib\*;%myDir%conf" com.test.datagrid.Run 10 AionUser

pause


