# Advanced Java Project 24: DiSL Project

In this project, we are a group of four students implementing different profilers using DiSL (Domain Specific Language for Java Bytecode Instrumentation).

## What is DiSL

DiSL is a bytecode instrumentation framework for the JVM, and allows us to: 

1. have agents send classes (observed application) to the DiSL server
2. the server instruments the code, and 
3. the server returns the instrumented class.

In this way, we can, for instance, intercept the execution of every method in the following observed application: 

```java
package ex1;

public class Main {
    public static void main(String[] args) {
	System.out.println("Application: Hello World!");
    }   
}
```

With the following instrumentation:

```java
package ex1;

import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.marker.BodyMarker;

public class Instrumentation {

    @Before(marker = BodyMarker.class)
    static void beforeEveryMethod() {
        System.err.println("Instrumentation: A new method has been executed.");
    }
}
```

## DiSL Properties

- Based on aspect-oriented programming
- Can instrument every method (of every class) that has associated bytecode instructions (no abstract or native methods)
  - Thanks to:
    - Load-time out-of-process instrumentation
    - Dynamic bypass to avoid infinite recursion of instrumentation

## How to Run DiSL

1. Compile application and create JAR files in the build folder: `ant`
    - `ant -Ddislclass=ex1.Instrumentation`
2. Start the DiSL server: [`startDiSLServer.sh`](http://startDiSLServer.sh)
3. Start the observed application: `runInstrumented.sh`
    - `bash ./runInstrumented ex1.Main 2>.out.err`
        - Redirect both the standard output and standard error to the same file.