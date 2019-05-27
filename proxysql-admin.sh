#!/usr/bin/env bash

docker exec -it mysql-master bash -c 'mysql -hproxysql -P6032 -uradmin -pradmin --prompt "ProxySQL Admin> "'