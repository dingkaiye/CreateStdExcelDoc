丁凯业 20160105 完成, 第一版 

1. 将 SVN "0302 模型设计\01缓冲层系统记录层"目录中的 "ODS标准数据结构文档" 生成 行方要求格式文档的工具 .

2. 行方文档的模板, 参见 本目录下: 附件2：数据库表结构模板.xlsx

3. src\ctrateStdExcel\CreateStdDoc.java 为主程序, 程序运行的入口, 参数分别为 
    ODS标准数据结构文档的路径,   生成的T层表结构文件名称, 生成的O层表结构文件名称

4. 可以重新运行, 如果 T层表结构文件, O层表结构文件 已经存在, 将会被删除而重新生成 . 


https://61.183.226.58:8443/svn/project/QingDao/ODS/04%E9%A1%B9%E7%9B%AE%E5%BC%80%E5%8F%91/0403%20java/Tools/CreateStdExcelDoc
