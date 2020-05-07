# paymybuddy
this application permit pay without bank transaction
This app uses Java to run and stores the data in Mysql DB.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

- Java 1.8
- Maven 3.6.2
- Mysql 8.0.17

### Installing

A step by step series of examples that tell you how to get a development env running:

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html

3.Install MySql:

https://dev.mysql.com/downloads/mysql/

After downloading the mysql 8 installer and installing it, you will be asked to configure the password for the default `root` account.
This code uses the default root account to connect and the password can be set as `rootroot`. If you add another user/credentials make sure to change the same in the code base.

4.Spring io with dependency

![Options SPRING intializr](/image/option spring initializr.png)

5. MODEL UML

![UML Class](/image/diagramme class paymybuddy.PNG)

6. BASE DE DONNEES MPD

![MPD DENORMALISE](/image/MPDdenormalise.PNG)

### Running App

Table are created automatically. And tables and data are imported par file import.sql.
Idem for H2 test


### Testing

The app has unit tests and integration tests written.
To run the tests from maven, go to the folder that contains the pom.xml file and execute the below command.

`mvn test`

### Reports

To obtains tests reports and Findbugs reports execute the below command.

'mvn site'

