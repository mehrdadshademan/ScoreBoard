**Purpose**
=============
This is a simple Live Football World Cup Scoreboard library that displays ongoing matches and their scores.

When a match starts, the system calls the "start" method, updating the scoreboard with a score of 0 for both teams, for example, "Italy 0 - 0 Spain". As teams score, the scoreboard is dynamically updated ( it use Set collection to store scoreboard). we can use H2 database and cache is implemented for storing data (more complex systems might involve using a pub/sub mechanism for real-time updates), this system remains simple and straightforward.

**TEST**
==============
This project follows a Test-Driven Development (TDD) approach, where test cases are developed first before creating the actual methods. Test cases include both Unit Tests and Integration Tests. For larger systems, integration tests are typically performed on dedicated servers, and stress tests may be conducted. However, given the simplicity of this system, extensive load and stress testing are deemed unnecessary.

**Additional Notes**
==============
According to the requirements, the project is not Dockerized, and there are no dependency profiles. The default port for the project is 8070, but you can modify it in the application properties.
