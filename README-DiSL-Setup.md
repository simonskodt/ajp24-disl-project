This bundle contains the template of the various exercises that compose the DiSL project.
Please refer to the text of the DiSL project for more information on the the purpose of each exercise.

## Bundle structure
Each exercise is composed of two parts: 1) profiler and 2) observed application.
- Profiler code is located in the `src-profiler` folder. In each exercise, there is always a single DiSL class (i.e., the class containing DiSL snippets) which is called `Instrumentation`. You can add as many profiler classes as you like to the `src-profiler` folder. However, all DiSL snippets *must* be added to the existing `Instrumentation` class.
- The observed application (already compiled) is located in the `bin-app` folder. In each example, the main class of the observed application is always called `Main`.
- Both the profiler and the observed applications are contained in a package called `ex<i>` (where <i> is a progressive integer number). For example, both the profiler and the observed applications that refer to the first exercise are contained in package `ex1`.

## How to build
Building requires the `ant` program.
You have to compile and build the sources each time you want to use a different profiler.

To run the build process, execute
`ant -Ddislclass=<DiSL class>`
in the root of the folder, where *<DiSL class>* is the fully qualified name of the DiSL class corresponding to the profiler you want to run.

For example, if you want to use the profiler that refers to the first exercise, run:
`ant -Ddislclass=ex1.Instrumentation`.

The build process will generate the JARs  `build/profiler.jar`, containing the profiler.


## How to run the observed application (uninstrumented)
The observed application (without any profiler) can be normally run via:
`bash ./run.sh <main class>`
where *<main class>* is the fully qualified name of the main class you want to run.

For example, to run the main class of the first exercise, run:
`bash ./run.sh ex1.Main`


## How to run the observed application (instrumented)
To run the observed application instrumented with a profiler:

- *First*, start the DiSL Server by executing `bash ./startDiSLServer.sh`.
- *Then*, start the observed application (with the profiler attached) by executing `bash ./runInstrumented.sh <main class>`
where *<main class>* is the fully qualified name of the main class you want to run.

**Note**: the profiler that will be applied to the observed application is *always* the one corresponding to the DiSL class specified at build time!


## Cleanup
You can run `ant clean` to discard generated profiler JAR.


## Summary
To run e.g. the instrumented observed application of the first exercise, execute, in order:

- `ant clean`
- `ant -Ddislclass=ex1.Instrumentation`
- `bash ./startDiSLServer.sh`
- `bash ./runInstrumented.sh ex1.Main`
