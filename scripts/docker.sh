docker network create rz

docker run --name mariadb --network rz --network-alias mariadb -e MYSQL_ROOT_PASSWORD=Abcd1234 -p 3306:3306 --restart always -d mariadb
docker run --name redis --network rz --network-alias redis -p 6379:6379 --restart always -d redis --requirepass "abc123"

docker run --name eureka --network rz --network-alias eureka -p 8761:8761 --restart always -d eureka
docker run --name admin --network rz --network-alias admin -p 8762:8762 --restart always -d admin
docker run --name gateway --network rz --network-alias gateway -p 9999:9999 --restart always -d gateway

docker run --name exam --network rz --network-alias exam --restart always -d exam
docker run --name topic --network rz --network-alias topic --restart always -d topic
