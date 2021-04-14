docker network create rz

docker run --name mariadb --network rz --network-alias mariadb -e MYSQL_ROOT_PASSWORD=Abcd1234 -p 3306:3306 -d mariadb

docker run --name redis --network rz --network-alias redis -p 6379:6379 -d redis --requirepass "abc123"

docker run --name eureka --network rz --network-alias eureka -p 8761:8761 -d eureka:1.0
