#!/bin/bash

# Get the current script's folder
SHELL_FOLDER=$(
    cd $(dirname $0)
    pwd
)
export LANG='zh_CN.utf8'

# Set the main class for the Java application
MAIN_CLASS_NAME=""
MAIN_CLASS_PATH=""

# Class search path
APPS_FOLDER=$SHELL_FOLDER/app
CLASS_PATH=$SHELL_FOLDER'/app/*:lib/*:conf'
WINDS_CLASS_PATH=$SHELL_FOLDER'/app/*;lib/*;conf'

# Running statuses
STATUS_STARTING="Starting"
STATUS_RUNNING="Running"
STATUS_STOPPED="Stopped"

# Timeout duration
TIMEOUT=30

# Define the environment variable for the application
# If no environment is provided, default to an empty string
ENVIRONMENT="${1:-}"

# Function to print log messages in green
LOG_INFO() {
    echo -e "\033[32m$@\033[0m"
}

# Function to print error messages in red
LOG_ERROR() {
    echo -e "\033[31m$@\033[0m"
}

# Check if a command exists, otherwise print an error message and exit
check_command() {
    command -v "$1" >/dev/null
    if [ $? -ne 0 ]; then
        LOG_ERROR "Command not found, please install $1 command and try again."
        exit 1
    fi
}

# Display help message
show_help() {
    LOG_INFO "Usage: ./start.sh [OPTIONS]"
    LOG_INFO "Starts a Java application with the specified environment."
    LOG_INFO
    LOG_INFO "Options:"
    LOG_INFO "  -h, --help      Display this help message."
    LOG_INFO "  -e, --env ENV   Set the spring environment for the Java application."
    LOG_INFO
    LOG_INFO "Example:"
    LOG_INFO "  ./start.sh -e server.port=8080"
}

# Parse command line options
parse_options() {
    while [[ "$#" -gt 0 ]]; do
        case $1 in
        -h | --help)
            show_help
            exit 0
            ;;
        -e | --env)
            if [ -n "$2" ]; then
                ENVIRONMENT="$2"
                shift 2
            else
                LOG_ERROR "Error: Option -e requires an argument"
                show_help
                exit 1
            fi
            ;;
        *)
            LOG_ERROR "Error: Unknown option $1"
            show_help
            exit 1
            ;;
        esac
    done
}

get_main_class() {
    # get jar path
    local jar_path=$APPS_FOLDER/*.jar
    # get jar package Application class, example:  875 Tue Mar 26 09:54:18 CST 2024 xx/xx/xx/xx/xxxApplication.class
    local file_name=$(jar -tvf $jar_path | grep Application)
    # get base name, example: xxxApplication.class
    local base_name=$(basename "$file_name")
    # get package name, example: xxxApplication
    MAIN_CLASS_NAME=${base_name%.*}
    # get paht name, example: xx/xx/xx/xx/xxxApplication.class
    # replace /, example: xx.xx.xx.xx.xxxApplication.class
    # remove .class, xx.xx.xx.xx.xxxApplication
    local package_path=$(echo "$file_name" | grep -oE '[^ ]+\.class$' | sed 's#/#.#g; s/\.class$//')
    MAIN_CLASS_PATH="$package_path main"
}

# Display information about Java, environment, main class, and classpath
show_info() {
    java_version=$(java -version 2>&1 | head -n 1)

    LOG_INFO "--------------------------------------------------------------------"
    LOG_INFO "Java version: ${java_version}"
    LOG_INFO "Spring Environment: ${ENVIRONMENT}"
    LOG_INFO "Spring Boot MainClassName: ${MAIN_CLASS_NAME}"
    LOG_INFO "Spring Boot MainClassPath: ${MAIN_CLASS_PATH}"
    LOG_INFO "Spring Boot ClassPath: ${CLASS_PATH}"
    LOG_INFO "--------------------------------------------------------------------"
}

# Get the process ID of the Java application
java_pid() {
    ps -ef | grep "${MAIN_CLASS_PATH}" | grep ${APPS_FOLDER} | grep -v grep | awk '{print $2}'
}

# Get the status of the Java application
java_status() {
    if [ ! -z $(java_pid) ]; then
        if [ ! -z "$(grep "Started $MAIN_CLASS_NAME" $SHELL_FOLDER/start.out)" ]; then
            echo ${STATUS_RUNNING}
        else
            echo ${STATUS_STARTING}
        fi
    else
        echo ${STATUS_STOPPED}
    fi
}

# Function to run the Java application
run_jar() {
    # Determine the operating system
    local os=$(uname)
    # Set resource variables
    local cp=$CLASS_PATH

    # If the operating system is Windows (MINGW), use a different classpath separator
    [ "$(echo $os | grep MINGW)" != "" ] && cp=$WINDS_CLASS_PATH

    # Run the Java application
    nohup java ${ENVIRONMENT:+-D$ENVIRONMENT} -cp $cp $MAIN_CLASS_PATH 1>/dev/null 2>&1 &
    # Record PID to file
    echo $! >$SHELL_FOLDER/pid/java.pid
}

# Tail logs for error and start
tail_log() {
    LOG_INFO "Error log"
    tail -n 200 logs/error.log
    LOG_INFO "Start log"
    tail -n 50 start.out
}

# Perform checks before starting the application
before_start() {
    local status=$(java_status)

    case ${status} in
    ${STATUS_STARTING})
        LOG_ERROR "$MAIN_CLASS_NAME is starting, pid is $(java_pid)"
        exit 0
        ;;
    ${STATUS_RUNNING})
        LOG_ERROR "$MAIN_CLASS_NAME is running, pid is $(java_pid)"
        exit 0
        ;;
    ${STATUS_STOPPED})
        # do nothing
        ;;
    *)
        exit 1
        ;;
    esac
}

# Start the application
start() {
    rm -rf $SHELL_FOLDER/pid/
    [ ! -d $SHELL_FOLDER/pid/ ] && mkdir -p $SHELL_FOLDER/pid/
    rm -f start.out
    get_main_class
    show_info
    run_jar
    echo -e "\033[32mApplication booting up ..\033[0m\c"
    i=0
    while [ $i -lt ${TIMEOUT} ]; do
        sleep 1
        local status=$(java_status)

        case ${status} in
        ${STATUS_STARTING})
            echo -e "\033[32m.\033[0m\c"
            ;;
        ${STATUS_RUNNING})
            break
            ;;
        ${STATUS_STOPPED})
            break
            ;;
        *)
            exit 1
            ;;
        esac

        ((i = i + 1))
    done
    echo ""
}

# Perform actions after starting the application
after_start() {
    local status=$(java_status)

    case ${status} in
    ${STATUS_STARTING})
        kill -9 $(java_pid)
        LOG_ERROR "Exceed waiting time. Killed. Please try to start $MAIN_CLASS_NAME again"
        tail_log
        exit 1
        ;;
    ${STATUS_RUNNING})
        LOG_INFO "$MAIN_CLASS_NAME started successfully, PID: $(java_pid)"
        ;;
    ${STATUS_STOPPED})
        LOG_ERROR "$MAIN_CLASS_NAME start failed"
        LOG_ERROR "See logs/error.log for details"
        tail_log
        exit 1
        ;;
    *)
        exit 1
        ;;
    esac
}

# Main function
main() {
    check_command java
    check_command jar
    before_start
    start
    after_start
}

# Parse command line options and execute main function
parse_options "$@"
main
