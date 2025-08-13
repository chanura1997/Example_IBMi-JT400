# AS/400 Dashboard

A simple Spring Boot dashboard application that connects to AS/400 systems using JT400 (JTOpen) to retrieve system values like QDATE and QTIME.

## Features

- **Real-time AS/400 Connection**: Connects directly to AS/400 systems
- **System Value Retrieval**: Gets QDATE (current date) and QTIME (current time) from AS/400
- **User Authentication**: Login with AS/400 credentials
- **Modern Web Interface**: Built with HTMX and Thymeleaf
- **Responsive Design**: Clean, modern UI that works on all devices

## Technology Stack

- **Spring Boot 3.x**: Main application framework
- **JT400 (JTOpen)**: IBM Toolbox for Java - AS/400 connectivity
- **HTMX**: Dynamic web interface elements
- **Thymeleaf**: Server-side HTML templating
- **Maven**: Build automation and dependency management


Click the "Logout" button to return to the login page

**create aplication.properties file like this **
in \src\main\resources\application.properties

# AS/400 Connection Configuration
# Example: as400.hostname=pub400.com
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


