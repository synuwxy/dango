docker build -t synuwxy/dango:$1 .
docker run -itd -p 10010:8080 -v /root/.m2:/root/.m2 --name dango synuwxy/dango:$1
docker logs -f dango
