# API Design

Ziel ist es eine API aus der dem Lehrfach "Verteilte Anwendungen" zu verbessern und weitgehend sicher zu designen. 


## Aufgabenstellung

Bereits im Projekt vorhanden waren

- **Frontend:** Eine Single-Page-Application programmiert mit Vue.js
- **Library:** Eine Bibliothek programmiert mit Jakarta EE Spezifikationen
- **Backend:** Eine Quarkus Applikation, die die Bibliothek implementiert und das Frontend ausliefert


1. Nach erfolgreicher Implementierung der nachfolgenden Aufgaben, sollte die Backend Applikation zum Einen das Anlegen, Ändern, Löschen und Abrufen von Projekten unterstützen: 

- Build prozess ausführen
- Many-to-Many Relation mithilfe der junction table implementieren (liquibase)
- Mapping mithilfe von JPA
- - Rest Endpunkt anlegen

2. Darüber hinaus sollten die Security Anforderung für eine abgesicherte API implementiert werden. 

- Jakarta Transaction API (JPA)
- Jakarte Bean Validatioon (JBV) 



##  dev mode

You can run your application in dev mode that enables live coding using:
```shell script
$mvn clean install
$mvn compile quarkus:dev -pl backend
```

> **_NOTE:_**  Quarkus ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
$mvn package
```
It produces the `verteilte-anwendung-runner.jar` file in the `backend/target/` directory.

The application is now runnable using `$java -jar backend/target/verteilte-anwendung-runner.jar`.
