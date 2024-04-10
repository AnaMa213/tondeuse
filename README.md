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

If you want to use your own input and output file/directory for the application you can add the following argument :

```bash
java -jar app.jar ./inputfile.txt ./outputfile.txt
```
