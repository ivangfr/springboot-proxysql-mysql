#!/usr/bin/env bash

echo
echo "Starting the environment shutdown"
echo "================================="

echo
echo "Removing containers"
echo "-------------------"
docker rm -fv mysql-master mysql-slave-1 mysql-slave-2 proxysql

echo
echo "Removing network"
echo "----------------"
docker network rm springboot-proxysql-mysql

echo
echo "Environment shutdown successfully"
echo "================================="
echo