-- Replication configuration
CHANGE MASTER TO MASTER_HOST='mysql-master', MASTER_USER='replication', MASTER_PASSWORD='replication', MASTER_AUTO_POSITION = 1;
START SLAVE;
