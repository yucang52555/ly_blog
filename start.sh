# 拉取最新的源码
git pull

# 执行打包
mvn clean package -Pprod

java -jar target/lyblog.jar --spring.profiles.active=prod  &

echo "Lyblog部署完毕，Enjoy！"