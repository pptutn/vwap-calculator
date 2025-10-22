#!/bin/sh
APP_NAME="vwap-calculator"

DIR="$(cd "$(dirname "$0")"/.. && pwd)"
CLASSPATH="$DIR/lib/*"
MAIN_CLASS=org.tranp.Main

JAVA_ARGS="${JAVA_ARGS} -Xlog:gc*=info:file=$DIR/logs/$APP_NAME-gc.log:time,uptime,level,tags:filecount=10,filesize=50M"
# todo: do app_functions and start...
#JAVA_ARGS="${JAVA_ARGS} -XX:+FlightRecorder -XX:StartFlightRecording=duration=60s,filename=$APP_NAME.jfr"

mkdir -p "$DIR/logs"

# echo PID
nohup /usr/bin/java -DlogDir="$DIR/logs" ${JAVA_ARGS} -cp "$CLASSPATH" "$MAIN_CLASS" "$@" > "$DIR/logs/stdout.log" 2>&1 &

echo "Application Started"
