#!/bin/bash

# Get the current script's folder
SHELL_FOLDER=$(
    cd $(dirname $0)
    pwd
)

# Function to print log messages in green
LOG_INFO() {
    echo -e "\033[32m$@\033[0m"
}

# Function to print error messages in red
LOG_ERROR() {
    echo -e "\033[31m$@\033[0m"
}

PID_FOLDER="$SHELL_FOLDER/pid"

# Function to stop the Java application
stop() {
    if [ -f $PID_FOLDER"/java.pid" ]; then
        pid=$(cat $PID_FOLDER"/java.pid")
        LOG_INFO "Application pid is $pid"
        kill $pid
        rm -r $PID_FOLDER"/java.pid"
        echo -e "\033[32mStopping application ..\033[0m\c"
        while true; do
            [ ! -d "/proc/$pid/fd" ] && break
            echo -e "\033[32m.\033[0m\c"
            sleep 1
        done
        LOG_INFO "\rApplication is already stopped."
    else
        LOG_ERROR "Application is not running."
    fi
}

# Execute stop function
stop

