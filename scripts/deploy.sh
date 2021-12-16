#!/bin/bash

DATE=$(date + "%Y%m%d")

DEPLOY_PATH=/home/ec2-user/app/deploy/
LOG_FILE=$DEPLOY_PATH/deploy_$DATE.log
ERR_LOG_FILE=$DEPLOY_PATH/deploy_$DATE_err.log

BUILD_JAR=$(ls $DEPLOY_PATH/film-api/build/libs/*.jar)
JAR_NAME=$(basename $BUILD_JAR)
echo "> build file_name: $JAR_NAME" >> $LOG_FILE
echo "> build copy file" >> $LOG_FILE

cp $BUILD_JAR $DEPLOY_PATH

echo "> pid check" >> $LOG_FILE
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> There is no application currently running, so do not exit." >> $LOG_FILE
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 15
fi

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME

echo "> DEPLOY_JAR deploy"    >> $LOG_FILE

nohup java -jar $DEPLOY_JAR >> $LOG_FILE 2>$ERR_LOG_FILE &
