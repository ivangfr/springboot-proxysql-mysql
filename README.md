# springboot-proxysql-mysql

The goal of this project is to use [`ProxySQL`](https://proxysql.com/) to load balance requests from a [`Spring Boot`](https://docs.spring.io/spring-boot/index.html) application to [`MySQL`](https://www.mysql.com/) Replication Master-Slave Cluster.

## Proof-of-Concepts & Articles

On [ivangfr.github.io](https://ivangfr.github.io), I have compiled my Proof-of-Concepts (PoCs) and articles. You can easily search for the technology you are interested in by using the filter. Who knows, perhaps I have already implemented a PoC or written an article about what you are looking for.

## Additional Readings

- \[**Medium**\] [**Optimizing Spring Boot’s Connection to MySQL Master-Slave Clusters with ProxySQL**](https://medium.com/@ivangfr/optimizing-spring-boots-connection-to-mysql-master-slave-clusters-with-proxysql-af275a0a4cea)

## Project Architecture

![project-diagram](documentation/project-diagram.jpeg)

## Applications

- ### MySQL

  [`MySQL`](https://www.mysql.com/) is the most popular Open Source SQL database management system, supported by `Oracle`. In this project, we set a **MySQL Replication Master-Slave Cluster** that contains three `MySQL` instances: one master and two slaves. In the replication process, data is automatically copied from the master to the slaves.

- ### ProxySQL

  [`ProxySQL`](https://proxysql.com/) is an open-source, high-performance `MySQL` proxy server. It sits between application and database servers, accepting incoming traffic from `MySQL` clients and forwarding it to backend `MySQL` servers. In this project, we set two `hostgroups`: `writer=10` and `reader=20`. Those hostgroups say to which database servers write or read requests should go. The `MySQL` master belongs to the `writer` hostgroup. On the other hand, the slaves belong to the `reader` hostgroup.

- ### customer-api

  `Spring Boot` Web Java application that exposes a REST API for managing customers. Instead of connecting directly to `MySQL`, as usual, the application will be connected to `ProxySQL`.

  `customer-api` has the following endpoints:
  ```text
     GET /api/customers
     GET /api/customers/{id}
    POST /api/customers {"firstName":"...", "lastName":"..."}
     PUT /api/customers/{id} {"firstName":"...", "lastName":"..."}
  DELETE /api/customers/{id}
  ```

## Prerequisites

- [`Java 21`](https://www.oracle.com/java/technologies/downloads/#java21) or higher.
- A containerization tool (e.g., [`Docker`](https://www.docker.com), [`Podman`](https://podman.io), etc.)

## Start Environment

- Open a terminal and, inside the `springboot-proxysql-mysql` root folder, run the following script:
  ```bash
  ./init-environment.sh
  ```

- Wait until the environment is up and running

## Check MySQL Replication

- In a terminal, make sure you are inside the `springboot-proxysql-mysql` root folder;

- To check the replication status run:
  ```bash
  ./check-replication-status.sh
  ```

  You should see something like:
  ```text
  mysql-master
  ------------
  File  Position  Binlog_Do_DB  Binlog_Ignore_DB  Executed_Gtid_Set
  mysql-bin-1.000003  1397      62a2f52f-b16b-11ed-91fc-0242c0a85002:1-14
  
  mysql-slave-1
  -------------
  *************************** 1. row ***************************
                 Slave_IO_State: Waiting for master to send event
                    Master_Host: mysql-master
                    Master_User: replication
                    Master_Port: 3306
                  Connect_Retry: 60
                Master_Log_File: mysql-bin-1.000003
            Read_Master_Log_Pos: 1397
                 Relay_Log_File: fa249eba35d6-relay-bin.000003
                  Relay_Log_Pos: 1614
          Relay_Master_Log_File: mysql-bin-1.000003
               Slave_IO_Running: Yes
              Slave_SQL_Running: Yes
                                 ...
  
  mysql-slave-2
  -------------
  *************************** 1. row ***************************
                 Slave_IO_State: Waiting for master to send event
                    Master_Host: mysql-master
                    Master_User: replication
                    Master_Port: 3306
                  Connect_Retry: 60
                Master_Log_File: mysql-bin-1.000003
            Read_Master_Log_Pos: 1397
                 Relay_Log_File: cbfd1f4bb01a-relay-bin.000003
                  Relay_Log_Pos: 1614
          Relay_Master_Log_File: mysql-bin-1.000003
               Slave_IO_Running: Yes
              Slave_SQL_Running: Yes
                                 ...
  ```

## Check ProxySQL configuration

- In a terminal and inside the `springboot-proxysql-mysql` root folder, run the script below to connect to `ProxySQL` command line terminal:
  ```bash
  ./proxysql-admin.sh
  ```

- In `ProxySQL Admin> ` terminal run the following command to see the `MySQL` servers: 
  ```bash
  SELECT * FROM mysql_servers;
  ```

- The following select shows the global variables:
  ```bash
  SELECT * FROM global_variables;
  ```
  
- In order to exit `ProxySQL` command line terminal, type `exit`.

## Start customer-api

- In a terminal and navigate to the `springboot-proxysql-mysql` root folder;

- Run the following Maven command to start the application:
  ```bash
  ./mvnw clean spring-boot:run --projects customer-api
  ```

## Simulation

1. Open three terminals: one for `mysql-master`, one for `mysql-slave-1` and another for `mysql-slave-2`;

2. In `mysql-master` terminal, connect to `MySQL Monitor` by running:
   ```bash
   docker exec -it -e MYSQL_PWD=secret mysql-master mysql -uroot --database customerdb
   ```

3. Do the same for `mysql-slave-1`...
   ```bash
   docker exec -it -e MYSQL_PWD=secret mysql-slave-1 mysql -uroot --database customerdb
   ```

4. ... and `mysql-slave-2`
   ```bash
   docker exec -it -e MYSQL_PWD=secret mysql-slave-2 mysql -uroot --database customerdb
   ```

5. Inside each `MySQL Monitor's` terminal, run the following commands to enable `MySQL` logs:
   ```bash
   SET GLOBAL general_log = 'ON';
   SET global log_output = 'table';
   ```

6. Open a new terminal. In it, we will just run `curl` commands;

7. In the `curl` terminal, let's create a customer:
   ```bash
   curl -i -X POST http://localhost:8080/api/customers \
     -H 'Content-Type: application/json' \
     -d '{"firstName": "Ivan", "lastName": "Franchin"}'
   ```
   
8. Go to `mysql-master` terminal and run the following `SELECT` command:
   ```bash
   SELECT event_time, command_type, SUBSTRING(argument,1,250) argument FROM mysql.general_log
   WHERE command_type = 'Query' AND (argument LIKE 'insert into customers %' OR argument LIKE 'select c1_0.id%' OR argument LIKE 'update customers %' OR argument LIKE 'delete from customers %');
   ```

   It should return:
   ```text
   +----------------------------+--------------+-------------------------------------------------------------------------------------------------------------------------------------------------+
   | event_time                 | command_type | argument                                                                                                                                        |
   +----------------------------+--------------+-------------------------------------------------------------------------------------------------------------------------------------------------+
   | 2023-02-20 22:13:15.400178 | Query        | insert into customers (created_at, first_name, last_name, updated_at) values ('2023-02-20 22:13:15', 'Ivan', 'Franchin', '2023-02-20 22:13:15') |
   +----------------------------+--------------+-------------------------------------------------------------------------------------------------------------------------------------------------+
   ```
   
   > **Note**: If you run the same `SELECT` in the slave's terminal, you will see that just the `mysql-master` processed the `insert` command. By the way, all inserts, updates, and deletes are executed on `mysql-master`.

9. Now, let's call to the `GET` endpoint to retrieve `customer 1`. For it, go to `curl` terminal and run:
   ```bash
   curl -i http://localhost:8080/api/customers/1
   ```

10. If you run, in one of the slave's terminal, the `SELECT` command below:
    ```bash
    SELECT event_time, command_type, SUBSTRING(argument,1,250) argument FROM mysql.general_log
    WHERE command_type = 'Query' AND (argument LIKE 'insert into customers %' OR argument LIKE 'select c1_0.id%' OR argument LIKE 'update customers %' OR argument LIKE 'delete from customers %');
    ```

    It should return:
    ```text
    +----------------------------+--------------+-------------------------------------------------------------------------------------------------------------------+
    | event_time                 | command_type | argument                                                                                                          |
    +----------------------------+--------------+-------------------------------------------------------------------------------------------------------------------+
    | 2023-02-20 22:14:06.582449 | Query        | select c1_0.id,c1_0.created_at,c1_0.first_name,c1_0.last_name,c1_0.updated_at from customers c1_0 where c1_0.id=1 |
    +----------------------------+--------------+-------------------------------------------------------------------------------------------------------------------+
    ```
    > **Note**: Just one slave should process it.

11. Next, let's `UPDATE` the `customer 1`. For it, go to the `curl` terminal and run:
    ```bash
    curl -i -X PUT http://localhost:8080/api/customers/1 \
      -H 'Content-Type: application/json' \
      -d '{"firstName": "Ivan2", "lastName": "Franchin2"}'
    ```

12. Running the following `SELECT` inside the `mysql-master` terminal:
    ```bash
    SELECT event_time, command_type, SUBSTRING(argument,1,250) argument FROM mysql.general_log
    WHERE command_type = 'Query' AND (argument LIKE 'insert into customers %' OR argument LIKE 'select c1_0.id%' OR argument LIKE 'update customers %' OR argument LIKE 'delete from customers %');
    ```

    It should return:
    ```text
    +----------------------------+--------------+-------------------------------------------------------------------------------------------------------------------------------------------------+
    | event_time                 | command_type | argument                                                                                                                                        |
    +----------------------------+--------------+-------------------------------------------------------------------------------------------------------------------------------------------------+
    | 2023-02-20 22:13:15.400178 | Query        | insert into customers (created_at, first_name, last_name, updated_at) values ('2023-02-20 22:13:15', 'Ivan', 'Franchin', '2023-02-20 22:13:15') |
    | 2023-02-20 22:14:33.019875 | Query        | update customers set created_at='2023-02-20 22:13:15', first_name='Ivan2', last_name='Franchin2', updated_at='2023-02-20 22:14:33' where id=1   |
    +----------------------------+--------------+-------------------------------------------------------------------------------------------------------------------------------------------------+
    ```
    > **Note**: During an update, Hibernate/JPA does a select before performing the record update. So, you should see another select on one of the slaves. 

13. Finally, let's `DELETE` the `customer 1`. For it, go to the `curl` terminal and run:
    ```bash
    curl -i -X DELETE http://localhost:8080/api/customers/1
    ```

14. Running the following `SELECT` inside the `mysql-master` terminal:
    ```bash
    SELECT event_time, command_type, SUBSTRING(argument,1,250) argument FROM mysql.general_log
    WHERE command_type = 'Query' AND (argument LIKE 'insert into customers %' OR argument LIKE 'select c1_0.id%' OR argument LIKE 'update customers %' OR argument LIKE 'delete from customers %');
    ```

    It should return:
    ```text
    +----------------------------+--------------+-------------------------------------------------------------------------------------------------------------------------------------------------+
    | event_time                 | command_type | argument                                                                                                                                        |
    +----------------------------+--------------+-------------------------------------------------------------------------------------------------------------------------------------------------+
    | 2023-02-20 22:13:15.400178 | Query        | insert into customers (created_at, first_name, last_name, updated_at) values ('2023-02-20 22:13:15', 'Ivan', 'Franchin', '2023-02-20 22:13:15') |
    | 2023-02-20 22:14:33.019875 | Query        | update customers set created_at='2023-02-20 22:13:15', first_name='Ivan2', last_name='Franchin2', updated_at='2023-02-20 22:14:33' where id=1   |
    | 2023-02-20 22:14:52.358207 | Query        | delete from customers where id=1                                                                                                                |
    +----------------------------+--------------+-------------------------------------------------------------------------------------------------------------------------------------------------+
    ```
    > **Note**: As with an update, during a deletion, Hibernate/JPA performs a select before deleting the record. So, you should see another select in one of the slaves.

## Shutdown

- To stop the `customer-api` application, go to the terminal where it's running and press `Ctrl+C`;
- In order to get out of the `MySQL Monitors` type `exit`;
- To stop and remove `MySQL`s and `ProxySQL` containers, network and volumes, make sure you are inside the `springboot-proxysql-mysql` root folder and run the following script:
  ```bash
  ./shutdown-environment.sh
  ```
