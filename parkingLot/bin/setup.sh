JAR_NAME="parkingLot.jar"
CODE_BASE_PATH="/home/liuji/Work/Interview/parkingLot/"
APP_DEPLOY_PATH="/home/liuji/Work/Interview/parkingLot/bin/"
APP_DEPLOY_JAR_PATH=$APP_DEPLOY_PATH$JAR_NAME
TARGET_JAR_PATH=$CODE_BASE_PATH"target/"$JAR_NAME

cd $CODE_BASE_PATH
mvn clean package
rm -rf $APP_DEPLOY_JAR_PATH
cp $TARGET_JAR_PATH $APP_DEPLOY_JAR_PATH
