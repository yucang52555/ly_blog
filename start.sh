# 拉取最新的源码
git pull

# 执行打包
mvn package -Pprod

java -server -Xms256m -Xmx512m -jar target/lyblog-latest.jar --spring.profiles.active=prod > logs/log.log 2>&1 &

echo "Lyblog部署完毕，Enjoy！"