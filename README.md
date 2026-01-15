Просто запуск (По умолчанию в папку - converted)

>mvn spring-boot:run

В папку с названием NewFolder

>mvn spring-boot:run -Dspring-boot.run.arguments="NewFolder"

-------
Для jar:

Просто запуск (По умолчанию в папку - converted)

>java -jar json-converter-0.0.1-SNAPSHOT.jar

В папку с названием NewFolder

>java -jar json-converter-0.0.1-SNAPSHOT.jar NewFolder
---
Упаковать в exe

>jpackage --name JsonConverter --input target/ --main-jar json-converter-0.0.1-SNAPSHOT.jar --type app-image --dest release
