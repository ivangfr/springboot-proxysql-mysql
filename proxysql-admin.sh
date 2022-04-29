#!/usr/bin/env bash

docker exec -it -e MYSQL_PWD=radmin mysql-master bash -c 'mysql -hproxysql -P6032 -uradmin --prompt "ProxySQL Admin> "'