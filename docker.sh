echo "Killing all Docker Instances"
killall "docker run" > /dev/null 2>&1
docker stop $(docker ps -a -q) > /dev/null 2>&1
docker rm $(docker ps -a -q) > /dev/null 2>&1
echo "Starting MySQL server"
docker run --name=mysql -d -p 13306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=airportdemodb mysql > /dev/null 2>&1
sleep 2
echo "Creating Database"
# docker exec mysql mysql -uroot -proot -e "CREATE DATABASE airportdemodb"
# mysql -uroot -proot -hlocalhost -e "CREATE DATABASE airportdemodb"
echo "Starting SpringBoot server"
docker run -v ~/git/SpringBoot-AirportDemoProject/:/var/www/site -p 8080:8082 -p 5005:5005 java /usr/bin/java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar /var/www/site/target/airportdemo-1.0.war