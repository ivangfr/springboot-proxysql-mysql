#!/usr/bin/env bash

docker exec -i mysql-master mysql -uroot -psecret customerdb < master-config.sql
docker exec -i mysql-slave-1 mysql -uroot -psecret customerdb < slave-config.sql
docker exec -i mysql-slave-2 mysql -uroot -psecret customerdb < slave-config.sql

