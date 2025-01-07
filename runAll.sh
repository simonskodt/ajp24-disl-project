#!/bin/bash

if [ $# -lt 1 ]; then
    echo "Remember to provide the exercise."
    exit 1
fi

exercise="$1"

ant clean
ant -Ddislclass=${exercise}.Instrumentation
bash startDiSLServer.sh
sleep 1
bash runInstrumented.sh ${exercise}.Main