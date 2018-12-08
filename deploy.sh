#!/bin/bash

# 指定Lyblog的根目录，请按实际修改
HALO_DIR="/www/wwwroot/lyblog"

# 拉取最新的源码
# git pull

# 进入Lyblog根目录
cd $HALO_DIR

# 停止Lyblog
sh $HALO_DIR/bin/lyblog.sh stop

# 执行打包
mvn package -Pprod

# 进入打包好的Lyblog目录
cd $HALO_DIR/target/dist/lyblog

# 运行Lyblog
nohup java -server -Xms256m -Xmx512m -jar `find ./ -name "lyblog*.jar"` > /dev/null 2>&1 &

echo "Lyblog部署完毕，Enjoy！"