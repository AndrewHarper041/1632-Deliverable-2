@echo off

javac -source 1.6 -cp .;lib/junit-4.12.jar;lib/mockito-all-1.10.19.jar;lib/objenesis-2.1.jar;lib/powermock-mockito-1.6.3-full.jar;lib/cglib-nodep-2.2.2.jar;lib/javassist-3.20.0-GA.jar -d lib *.java
java -cp .;lib/junit-4.12.jar;lib/hamcrest-core-1.3.jar;lib org.junit.runner.JUnitCore Tests 
java emmarun -cp lib CitySim9000 1
 
echo.
pause