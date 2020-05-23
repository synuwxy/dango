FROM ubuntu:18.04

MAINTAINER <synuwxy>

# 切换国内源
RUN sed -i 's#http://archive.ubuntu.com/ubuntu/#http://mirrors.aliyun.com/ubuntu/#' /etc/apt/sources.list;

ENV LANG C.UTF-8

RUN apt-get update && apt-get install -y \
    openjdk-8-jdk \
    gcc \
    curl \
    unzip \
    git \
    vim \
    wget \
    gradle \
    maven \
    && rm -rf /var/lib/apt/lists/*

#COPY ./settings.xml /root/.m2/settings.xml

# 安装 Golang 环境
#ADD ./go1.13.6.linux-amd64.tar.gz /opt
#ENV GOLANG_VERSION=1.13.6 \
#	GOROOT=/opt/go \
#	GOPATH=/home/go \
#	GO111MODULE=on \
#	WORKSPACE=workspace \
#	GOPROXY=https://goproxy.cn \
#    PATH=$PATH:/opt/go/bin

# 安装 nodejs 环境
#ADD ./node-v12.16.3-linux-x64.tar /opt
#ENV PATH=$PATH:/opt/node-v12.16.3-linux-x64/bin
#RUN chmod 777 -R /opt/node-v12.16.3-linux-x64 \
#    && npm config set registry https://registry.npm.taobao.org \
#	&& npm install -g yarn@latest \
#	&& yarn config set registry https://registry.npm.taobao.org

# run
WORKDIR /app

COPY dango.jar /app/

CMD java -Xms256m -Xmx256m -jar dango.jar