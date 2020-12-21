JAR_NAME="parkingLot-jar-with-dependencies.jar"
APP_DEPLOY_PATH="/home/liuji/Work/Interview/parkingLot/bin/"
APP_DEPLOY_JAR_PATH=$APP_DEPLOY_PATH$JAR_NAME

if [ x"$1" = x ]; then
    echo "Pase add file param!"
    exit 1
fi

java -cp $APP_DEPLOY_JAR_PATH com.sunray.Start inputFilePath=$
