# enhanced-tomcat
Additional performance-enhancing changes have been made to tomcat

base on tomcat 
遵循apache2.0协议

提前无堵塞的读取部分的http的body,
读取的数量取决于读填充数组中剩余的数量的填充数据
