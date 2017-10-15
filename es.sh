#!/usr/bin/env bash
docker stop search
docker rm search
rm -rf esdata
docker run --name=search -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -v "$PWD/esdata":/usr/share/elasticsearch/data elasticsearch:2.4.6