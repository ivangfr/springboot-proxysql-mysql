# springboot-proxysql-mysql

The goal of this project is to use [`ProxySQL`](https://proxysql.com/) to load balance requests from a Spring-Boot
application to a [`MySQL`](https://www.mysql.com/) replication cluster.

# Environment Architecture

// TODO picture

### MySQL

### ProxySQL

### Customer Rest API

# Start Environment

- Open one terminal

- Inside `/springboot-proxysql-mysql` root folder run

```bash
./env-init.sh
```
> To stop and remove containers, networks and volumes type
> ```
> ./env-shutdown.sh
> ```

- Wait a little bit until the environment is up and running

## Check MySQL Replication

To check the replication status run
```bash
./env-check-replication-status.sh
```

You should see something like
```bash

------------
mysql-master
------------
mysql: [Warning] Using a password on the command line interface can be insecure.
File    Position        Binlog_Do_DB    Binlog_Ignore_DB        Executed_Gtid_Set
mysql-bin.000003        945                     38f50e33-7fc4-11e9-a810-0242ac1b0003:1-9

-------------
mysql-slave-1
-------------
mysql: [Warning] Using a password on the command line interface can be insecure.
*************************** 1. row ***************************
               Slave_IO_State: Waiting for master to send event
                  Master_Host: mysql-master
                  Master_User: repl
                  Master_Port: 3306
                Connect_Retry: 60
              Master_Log_File: mysql-bin.000003
          Read_Master_Log_Pos: 945
               Relay_Log_File: 5c42804f49ab-relay-bin.000003
                Relay_Log_Pos: 1158
        Relay_Master_Log_File: mysql-bin.000003
             Slave_IO_Running: Yes
            Slave_SQL_Running: Yes
                            ...

-------------
mysql-slave-2
-------------
mysql: [Warning] Using a password on the command line interface can be insecure.
*************************** 1. row ***************************
               Slave_IO_State: Waiting for master to send event
                  Master_Host: mysql-master
                  Master_User: repl
                  Master_Port: 3306
                Connect_Retry: 60
              Master_Log_File: mysql-bin.000003
          Read_Master_Log_Pos: 945
               Relay_Log_File: d08e85c4beb8-relay-bin.000003
                Relay_Log_Pos: 1158
        Relay_Master_Log_File: mysql-bin.000003
             Slave_IO_Running: Yes
            Slave_SQL_Running: Yes
                            ...
```

## Check ProxySQL consiguration

- Run the script below to connect to `ProxySQL` command line terminal
```bash
./proxysql-admin.sh
```

- In `ProxySQL Admin> ` terminal run the following command to see the mysql servers 
```bash
SELECT * FROM mysql_servers;
```

# Start Application

- In a terminal, inside `springboot-proxysql-mysql`, run
```bash
./mvnw clean spring-boot:run
```

# Configure/Check MySQL Logs

- Connect to each one `MySQL` container and ...
```bash
docker exec -it mysql-master mysql -u root -psecret --database=customerdb
docker exec -it mysql-slave-1 mysql -u root -psecret --database=customerdb
docker exec -it mysql-slave-2 mysql -u root -psecret --database=customerdb
```

- ... run the following commands to enable the logs
```bash
SET GLOBAL general_log = 'ON';
SET global log_output = 'table';

SELECT event_time, command_type, SUBSTRING(argument,1,250) FROM mysql.general_log WHERE command_type = 'Query' AND (argument LIKE 'insert into customers %' OR argument LIKE 'select customer0_.id %' OR argument LIKE 'update customers %' OR argument LIKE 'delete from customers %'); 
```

