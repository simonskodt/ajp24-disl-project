#!/bin/sh

if [ "$#" -ne 1 ]; then
	echo "Usage: $0 <Name of main class>"
	exit;
fi

$JAVA_HOME/bin/java -cp build/app.jar $*
