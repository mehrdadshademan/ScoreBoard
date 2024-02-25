**Purpose**
=============
This is a simple Live Football World Cup Scoreboard library that displays ongoing matches and their scores.

**How Is work?**
=============
This system developed by Java base on spring boot framework

When a match starts, the system calls the "startMatch" method, updating the scoreboard with a score of 0 for both teams, for example, "Italy 0 - 0 Spain". As teams score, the scoreboard is dynamically updated
When a match is finished, the method "finishMatch" is called then the match will be removed from scoreboard and the scoreboard will be updated , then  the user call method scoreBoardSummery() it can see the updated and sorted result of matches.
When a match score is change, the "updateMatch" method called and the score of teams will be updated
When the user want to see the result and scoreboard, it can be call "scoreBoardSummery" method and this method show the sorted base on match is according the total scores and if the scores ara same, then order by most recently started match.
After each call method like start, update or finish match( it use Set collection to store scoreboard) the scoreboard will update.
There are some validations for main methods like start/update/finish. 

We can use H2 database and cache is implemented for storing data (more complex systems might involve using a pub/sub mechanism for distributed system updates), this system remains simple and straightforward.
For handling exception to project, There is a "MatchBadRequestInputException" method which is extended and used in web rest exception ( this part is not necessary because it is web handler exception and uses ControllerAdvice, but it is for future extension by other colleagues easier), we do not need to try scope or try with resources because this project is so simple.
In this project, we use SOLID principles and a simple way to store data in a collection.
This project is not dockerized or a web application, it is like a simple project as a library. Then you can add this jar file to your project, in below you can see how to add the lib to your project in IntelliJ


Right-click on the project.
Select "Open Module Settings."
Go to the "Dependencies" tab.
Click the '+' button and select "JARs or directories."
Choose the JAR file.


**Libraries**
==============
For this project, we do not need so many libraries in maven but there are JPA, h2, and spring web libraries for the future when we want to store in the database and show exceptions in the controller.

**TEST**
==============
This project follows a Test-Driven Development (TDD) approach, where test cases are developed first before creating the actual methods.
Test cases include both Unit Tests and Integration Tests (For larger systems, integration tests are typically performed on dedicated servers, and stress tests may be conducted). However, given the simplicity of this system, extensive load and stress testing are deemed unnecessary.
In test classes, some methods use ParameterizedTest for multi-input for testing the class

**Additional Notes**
==============
According to the requirements, the project is not Dockerized, and there are no dependency profiles. The default port for the project is 8070, but you can modify it in the application properties.
