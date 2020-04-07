## Dango 

一个十分简单的 docker 环境下进行 CI 的工具

### 使用要求

安装 docker

### 部署方式

1. clone 仓库 (参数修改参考 使用方式)
2. 代码根目录下执行打包命令
 ```shell script
mvn clean package -Dmaven.test.skip=true
 ```
3. 将打包后的 target/dango.jar 复制到代码 script 目录
  ```shell script
cp target/dango.jar script/dango.jar
  ```
4. 在 script 目录下执行命令 
```shell script
sh deploy.sh v0.0.1 # 参数是版本号
```

### 使用方式

1. 请保证 docker 2375端口 已经打开并开始监听tcp请求
2. 修改 application.yml 文件将docker host指向自己的机器

> 目前只有一个接口，直接通过git仓库地址构建镜像，只支持公开的springboot仓库

例子: 
```
POST http://${ip}:10010/ci/docker/build
Content-Type: application/json

{
  "repository": "https://gitee.com/synuwxy/event-bus.git",
  "dockerTag": "test:v2",
  "type": "maven",
  "branch": "master"
}

###
```