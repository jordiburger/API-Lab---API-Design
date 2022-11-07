# Verteilte Anwendungen Übungsaufgabe 2

Mögliche Punktzahl: 20 Punkte

## Deadlines

- 1. Zug 1. Gruppe: 19.12.2022
- 1. Zug 2. Gruppe: 22.12.2022
- 2. Zug 1. Gruppe: 20.12.2022
- 2. Zug 2. Gruppe: 20.12.2022 

## Aufgabestellung
In dieser Aufgabe erhalten Sie ein vorkonfiguriertes Projekt, das aus drei Komponenten besteht:

- **Frontend:** Eine Single-Page-Application programmiert mit Vue.js
- **Library:** Eine Bibliothek programmiert mit Jakarta EE Spezifikationen
- **Backend:** Eine Quarkus Applikation, die die Bibliothek implementiert und das Frontend ausliefert

Die bereits vorhandene Funktion umfasst das Anlegen, Ändern, Löschen und Abrufen von Benutzern.
Das Ziel der Übungsaufgabe 2 ist die Erweiterung dieser Applikation. Nach erfolgreicher Implementierung der nachfolgenden Aufgaben, sollte die Backend Applikation zum Einen das Anlegen, Ändern, Löschen und Abrufen von Projekten unterstützen und zum Anderen die Möglichkeit zur Verknüpfung von Benutzern zu Projekten bereitstellen.

1.  **(4P)** Wie Sie sicherlich bereits festgestellt haben, lässt sich das Projekt nicht so einfach mittels ``$ mvn package`` bauen und mit ``$ java -jar ./backend/target/verteilte-anwendung-runner.jar`` starten. Dies liegt an der fehlenden Datenbank! Schreiben Sie daher ein Docker Compose file mit dem Sie das [offizielle Image der MySQL aus Docker Hub](https://hub.docker.com/_/mysql) starten. Beachten Sie bitte die bereits vorhandenen Konfigurationen in der 
[application.properties](backend/src/main/resources/application.properties). Wenn Sie die MySQL korrekt gestartet haben und sowie das Projekt inklusive der Integrationstests bauen und starten können, dann sollten sie auf die Applikation über [http://localhost:8080/](http://localhost:8080/) zugreifen können.
2.  **(4P)** Um auch Projekte in der MySQL speichern zu können, muss das bestehende Schema um zwei Tabellen erweitert werden. Erstellen Sie dafür ein neues Liquibase ChangeSet in [liquibase-changelog.xml](backend/src/main/resources/META-INF/liquibase-changelog.xml), das zum einen die Speicherung von Projekten ermöglicht zum Anderen eine Many-to-Many Relation zwischen User und Projekt
in eine Junction Table.
3.  **(4P)** Schreiben Sie ein Docker Compose file mit dem Sie das angepasste Image aus dem ersten Schritt starten. Der gestartete Container soll über Port 8181 erreichbar sein und die Konfiguration aus einer lokalen Datei des Host Systems (somit nicht im Image enthalten) lesen.
4.  **(8P)** Erweitern sie die Quarkus Applikation, um einen weiteren REST Endpunkt. Dieser soll unter dem Pfad `/aufgaben/1/` eine Ressource namens „zahl“ vom Media Type ``application/example`` bereitstellen. Der REST Endpunkt soll vier Methoden unterstützen: 1. Initiales Anlegen einer Zahl, 2. Abrufen der aktuellen Zahl, 3. Aktualisieren einer Zahl und 4. Löschen einer Zahl. 


# Quarkus Get Started

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
$mvn compile quarkus:dev
```

> **_NOTE:_**  Quarkus ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
$mvn package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `$java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
$mvn package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `$java -jar target/*-runner.jar`.
