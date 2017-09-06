FROM mysql
ENV MYSQL_ROOT_PASSWORD="" MYSQL_ALLOW_EMPTY_PASSWORD=TRUE MYSQL_DATABASE="airportdemo"
ADD airportdemo.sql /docker-entrypoint-initdb.d/airportdemo.sql