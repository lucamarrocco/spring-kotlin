## Setting up simple Kotlin multi-target project

### Create a Js Library

this library will include browser / js specific code, that's code that is cross compiled into vanilla Javascript and run in an environment which is aware of browser things and able to execute javascript programs.

### Create a Java Library

this library will include backend / java specific code, that's code that use Java Development Kit and run inside a Java Virtual Machine

### Create a Common Library

this library will include agnostic browser / backend code, that's is code written in a way to be independent from target environment, hence code that can run either in a java / jvm environment or compiled in javascript into a browser / javascript native environment
