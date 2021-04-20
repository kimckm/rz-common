docker network create rz

docker run --name mariadb --network rz --network-alias mariadb -e MYSQL_ROOT_PASSWORD=Abcd1234 -p 3306:3306 -d mariadb
docker run --name redis --network rz --network-alias redis -p 6379:6379 -d redis --requirepass "abc123"

docker run --name eureka --network rz --network-alias eureka -p 8761:8761 -d eureka:1.0
docker run --name admin --network rz --network-alias admin -p 8762:8762 -d admin
docker run --name gateway --network rz --network-alias gateway -p 9999:9999 -d gateway

docker run --name exam --network rz --network-alias exam -d exam
docker run --name topic --network rz --network-alias topic -d topic

docker start redis
docker start mariadb
docker start eureka
docker start gateway
docker start admin
docker start exam
docker start topic
