# Installation and Launch the Application
To be able to test this application, there is prerequisites that need to be installed on your system :
  - [maven](https://maven.apache.org/download.cgi)
  - [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)

After you have successfully clone the git repository on your system, you can proceed the following mvn command on you project directory.

```bash
mvn clean install
```

This should create a .jar in your target directory. Place yourself on this directory and use the following command (app.jar will be reffering to the name of the jar).

```bash
java -jar app.jar 
```
Then the application should launch itself and a directory output with a file output.txt should appear in your project directory.

If you want to use your own input and output file/directory for the application you can add the following argument (-i for inputfile, -o for outputfile) :

```bash
java -jar app.jar -i=file:dir/inputfile.txt -o=file:dir/outputfile.txt
```
### Run already built jar

You can also only download the JAR in the directory jar_zip to direclty run the previous java command. ( you'll still need Java 17 to run the command)

# Build and Deploy via Jenkins build

![image](https://github.com/AnaMa213/tondeuse/assets/15228021/6209ed95-a8c2-441c-9cc8-70308fc0f376)

![image](https://github.com/AnaMa213/tondeuse/assets/15228021/6f26fbf0-47d3-4893-b969-00082427ebe5)

Jenkins allows the build and packaging deploy of the application on github repository.

![image](https://github.com/AnaMa213/tondeuse/assets/15228021/d76c7eb0-0135-409d-a4b4-038b4524021e)


# Tests Coverage

 The test coverage has been controled thanks to the plugin jacoco that create a code coverage report in the target directory. Here is the report (80% test coverage) :

 ![image](https://github.com/AnaMa213/tondeuse/assets/15228021/1030aac0-f0cd-4a8d-ab58-d804f1e7cae4)


# Documentation
This spring batch application has been documented with Javadoc. You can generate Javadoc by using a maven command :

```bash
mvn site
```

