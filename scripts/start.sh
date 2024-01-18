#!/bin/bash
SHELL_FOLDER=$(
    cd $(dirname $0)
    pwd
)

# Function to log informational messages
LOG_INFO() {
    local content=${1}
    local date=$(date +"%Y-%m-%d %H:%M:%S")
    echo -e "\033[32m$date [INFO] ${content}\033[0m"
}

# Function to log error messages
LOG_ERROR() {
    local content=${1}
    local date=$(date +"%Y-%m-%d %H:%M:%S")
    echo -e "\033[31m$date [ERROR] ${content}\033[0m"
}

# Function to check if a command is available
check_command() {
    command -v "$1" >/dev/null
    if [ $? -ne 0 ]; then
        LOG_ERROR "Command not found, please install $1 command and try again."
        exit 1
    fi
}

# Set the main class for the Java application
MAIN_CLASS="cc.taketo.Application main"

# Define the environment variable for the application
# If no environment is provided, default to an empty string
ENVIRONMENT="${1:-}"

# Function to run the Java application
run_jar() {
    # Determine the operating system
    local os=$(uname)
    # Set the resource variable for the classpath
    local resource='app/*:lib/*:conf'

    # If the operating system is Windows (MINGW), use a different classpath separator
    [ "$(echo $os | grep MINGW)" != "" ] && CP='app/*;lib/*;conf'

    # Run the Java application
    java ${ENVIRONMENT:+-Dspring.profiles.active=$ENVIRONMENT} -cp $resource $MAIN_CLASS
}

# Main function
main() {
    # Check for the availability of the 'java' command
    check_command java
    # Run the program
    run_jar
}

# Call the main function
main
