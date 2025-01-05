#!/bin/sh


if [ "$#" -ne 1 ]; then
	echo "Usage: $0 <Name of main class>"
	exit;
fi

AGENT_EXT=.so

#Checks if OS is MacOS
if [ `uname -s` == "Darwin" ]; then
    AGENT_EXT=.jnilib
fi

ARCH=x86_64

#Check if architecture is aarch64

if [ `uname -p` == "aarch64" ]; then
    ARCH=aarch64
fi    

AGENT_FLAGS=

JAVA_VERSION=$("$JAVA_HOME/bin/java" -version 2>&1 | head -1 | cut -d'"' -f2 | sed '/^1\./s///' | cut -d'.' -f1)

if [ "$JAVA_VERSION" -gt "8" ]; then
  AGENT_FLAGS="$AGENT_FLAGS --patch-module java.base=lib/disl-bypass.jar --add-exports java.base/ch.usi.dag.disl.dynamicbypass=ALL-UNNAMED"
fi

$JAVA_HOME/bin/java -agentpath:lib/$ARCH/libdislagent$AGENT_EXT $AGENT_FLAGS -Xbootclasspath/a:lib/disl-bypass.jar:build/profiler.jar -cp build/app.jar -noverify $*
