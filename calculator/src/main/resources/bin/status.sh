#!/bin/sh
APP_NAME=org.tranp.Main

# Check for java process containing the app name
if pgrep -f "$APP_NAME" > /dev/null
then
    echo "Application is running."
else
    echo "Application is NOT running."
fi
