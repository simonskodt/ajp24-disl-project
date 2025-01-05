#!/bin/sh

$JAVA_HOME/bin/java -Ddisl.exclusionList="exclusion.lst" -cp build/profiler.jar:lib/disl-server.jar ch.usi.dag.dislserver.DiSLServer &
