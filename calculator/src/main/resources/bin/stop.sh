#!/bin/sh
APP_NAME=org.tranp.Main

PIDS=$(pgrep -f "$APP_NAME")

if [ -z "$PIDS" ]
then
    echo "Application is NOT running."
else
    echo "Stopping application..."
    kill $PIDS
    # Optionally wait for process to stop, then confirm
    sleep 2
    if pgrep -f "$APP_NAME" > /dev/null
    then
        echo "Failed to stop. Killing forcefully..."
        kill -9 $PIDS
    else
        echo "Application stopped."
    fi
fi
