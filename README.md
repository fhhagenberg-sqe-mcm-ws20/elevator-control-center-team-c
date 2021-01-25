# Elevator Control Center - Team C

### Team Members

- Julia Hauptmannn
- Dominik Grüneis
- Lukas Hubl

# Project

### Structure

As the name already suggests, the project is about controling elevators. The system is designed to control multiple elevators within a single building. Both passive status reads and active elevator control mechanisms are provided and depicted via a graphical user interface.

### RMI

This project uses Remote Method Invocation as a technique for interaction between the house's elevators and the control center. The interface provides the possibility to read out elevator stats in real time and also provides the neccessary endpoints to actively control the elevators.

### Graphical User Interface

The project comes with a graphical user interface which depicts real-time elevator stats and lets the user control the elevators in the building. 

### Simulation

For development reasons, especially for testing, the project is able to connnect to a simulation software provided within the course. The simulation is used to configre specific house-elevator settings for testing correct application behavior.

### Testing

The project is using Github actions as CI tool of choise and runs several unit tests and also GUI tests using TestFX to ensure a constantly working codebase. Moreover, Sonarcloud is used to check for code quality.

# Installation

To run the Elevator Control Center, the .jar file provided with the newest release needs to be downloaded. 
Before or after executing the .jar file, the simulator needs to be started. The ECC will connect automatically to the simulator once started.


# Graphical User Interface with JavaFx

### Prerequisites

- [x] Java 13 SDK (e.g. Oracle or OpenJDK).
- [x] Maven 3. (If you use an IDE like Eclipse or IntelliJ, Maven is **already included** :sunglasses:.)
	- see http://maven.apache.org/install.html
- [x] An IDE or code editor of your choice.

> Confirm your installation with `mvn -v` in a new shell. The result should be similar to:

```
$ mvn -v
Apache Maven 3.6.2 (40f52333136460af0dc0d7232c0dc0bcf0d9e117; 2019-08-27T17:06:16+02:00)
Maven home: C:\Users\winterer\.tools\apache-maven-3.6.2
Java version: 13, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-13
Default locale: en_GB, platform encoding: Cp1252
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
```

### Instructions

This maven project is already set up for JavaFx based GUI applications. It also contains a small example application - `App.java`.

1. Import this git repository into your favourite IDE.

1. Make sure, you can run the sample application without errors.
	- Either run it in your IDE
	- Via command line, run it with `mvn clean javafx:run`.

You can build your project with maven with

```
mvn clean package
```

The resulting archive (`.jar` file) is in the `target` directory.
