#!/usr/bin/env bash

echo
echo "mysql-master"
echo "------------"
docker exec -i mysql-master mysql -uroot -psecret <<< "SHOW MASTER STATUS"
echo

echo "mysql-slave-1"
echo "-------------"
docker exec -i mysql-slave-1 mysql -uroot -psecret <<< "SHOW SLAVE STATUS \G"
echo

echo "mysql-slave-2"
echo "-------------"
docker exec -i mysql-slave-2 mysql -uroot -psecret <<< "SHOW SLAVE STATUS \G"
echo