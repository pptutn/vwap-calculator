#!/bin/sh

DIR="$(cd "$(dirname "$0")"/.. && pwd)"
CLASSPATH="$DIR/lib/*"
MAIN_CLASS=org.tranp.Main

mkdir -p "$DIR/logs"

nohup /usr/bin/java -DlogDir="$DIR/logs" -cp "$CLASSPATH" "$MAIN_CLASS" "$@" > "$DIR/logs/stdout.log" 2>&1 &

echo "Application Started"
