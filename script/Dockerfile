FROM openjdk:8-jre-alpine


RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.tuna.tsinghua.edu.cn/g' /etc/apk/repositories

RUN apk add --no-cache curl grep sed unzip bash git curl vim wget openssh gradle maven busybox-extras

MAINTAINER <synuwxy>

WORKDIR /app

COPY dango.jar /app/

CMD java -Xms256m -Xmx256m -jar dango.jar