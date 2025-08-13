
A simple Spring Boot dashboard application that connects to AS/400 systems using JT400 (JTOpen) to retrieve system values like QDATE and QTIME.

To run this.

**create aplication.properties file like this **
in \src\main\resources\application.properties

# AS/400 Connection Configuration
as400.hostname=<<hostname>>
as400.library=QGPL

# Server Configuration
server.port=8080
spring.application.name=AS400 Dashboard

# Thymeleaf Configuration
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html

# Logging
logging.level.com.chanura=DEBUG
logging.level.com.ibm.as400=INFO

======================================================================
How to download and run this in your localhost:
Pull my repo.
Crate an application.properties in (\src\main\resources). Example given in the readme.md file inside the repo.
To run this, run these command in VS code terminal.
•	mvn clean install
•	java -jar  .\target\<<jar_name>>
open web browser.
Run this in browser.
Locahost:<<given_portname>>

