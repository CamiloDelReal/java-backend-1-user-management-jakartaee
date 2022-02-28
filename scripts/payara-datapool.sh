#!/bin/bash

~/Apps/payara5/bin/asadmin create-jdbc-connection-pool \
    --datasourceclassname com.mysql.cj.jdbc.MysqlDataSource \
    --restype javax.sql.DataSource  \
    --property databaseName=users_management:serverName=LocalMySQL:portNumber=3306:AllowPublicKeyRetrieval=true:UseSSL=false:Password=root:User=root:URL='jdbc\:mysql\://localhost\:3306/users_management' users_management_datapool

~/Apps/payara5/bin/asadmin create-jdbc-resource --connectionpoolid users_management_datapool jdbc/users_management_jta