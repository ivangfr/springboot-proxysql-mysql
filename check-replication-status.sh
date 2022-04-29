#!/usr/bin/env bash

echo
echo "mysql-master"
echo "------------"
docker exec -i -e MYSQL_PWD=secret mysql-master mysql -uroot <<< "SHOW MASTER STATUS"
echo

echo "mysql-slave-1"
echo "-------------"
docker exec -i -e MYSQL_PWD=secret mysql-slave-1 mysql -uroot <<< "SHOW SLAVE STATUS \G"
echo

echo "mysql-slave-2"
echo "-------------"
docker exec -i -e MYSQL_PWD=secret mysql-slave-2 mysql -uroot <<< "SHOW SLAVE STATUS \G"
echo