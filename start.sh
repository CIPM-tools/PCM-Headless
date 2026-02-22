#!/bin/bash
echo "Starting REST interface."
echo "Arguments: $1"

# running
cd /app

# var
jarFile="$(find . -name "*.jar" -maxdepth 1 -type f)"

java -version
java -javaagent:/app/agent/agent.jar -Xms1024m -Xmx4096m -jar $jarFile $1