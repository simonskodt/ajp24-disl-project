# Advanced Java Project 24: DiSL Project

In this project, we are a group of four students implementing different profilers using DiSL (Domain Specific Language for Java Bytecode Instrumentation).

## What is DiSL?

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