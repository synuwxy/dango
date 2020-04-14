## Dango 

一个十分简单的 docker 环境下进行 CI 的工具

### 使用要求

开发环境需要 JDK 1.8
部署环境需要 docker

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
4. 在 script 目录下执行命令 或者 使用docker-compose
```shell script
sh deploy.sh v0.0.1 # 参数是版本号
```

### 使用方式

1. 请保证 **docker 2375端口** 已经打开并开始监听tcp请求
2. 修改 application.yml 文件将 **docker.host** 指向自己的机器

> 只支持public且由maven构建的springboot仓库

服务启动后可见swagger接口文档 访问 http://{ip}:{port}/swagger-ui.html 
样例见测试包

目前具备的功能

* 从git仓库直接构建镜像
* 由已有镜像启动容器
* 由已有镜像启动容器时替换之前的容器
* 配置 github WebHook 实现推送代码直接构建镜像
* 配置 github WebHook 实现推送代码直接更新容器(容器网络使用host模式)