#Zadanie rekturatcyjne Atmoterm

**Autor: Jan Wojnar**

Do startu używać JDK w wersji 11+ przy pomocy IntelliJ lub po użyciu `mvn pakcage` komendy

`java -jar -Dspring.profiles.active=exampleDatabase C:\path\to\jar\atmoterm-1.0.0.jar`

Aplikacja po uruchomieniu znajduje się pod adresem http://localhost:8080/atmotermApp
Proszę o uruchomienie aplikacji z profilem "**exampleDatabase**", dzięki któremu 9 (w tym 4 aktywnych) pracowników zostanie przydzilonych
do trzech zespołów.


Bezpośredni dostęp do bazy danych pod adresem: http://localhost:8080/atmotermApp/h2-console

Logowanie:

driver class: org.h2.Driver

jdbc url: jdbc:h2:mem:mydb

username: sa

password: password

**Testy**

Stworzyłem parę klas testowych, pokrycie nie wynosi 100% ponieważ byłem na urlopie, a aplikacja powstała w ciągu 2-3 dni po pracy. 
Najistotniejsza klasa testowa
 to RepositoryTest.java, w której inicjalizowana jest baza danych łącznie z zespołami do których zostali
  przydzieleni pracownicy, nastepnie po operacja CRUDowych sprawdzany jest stan bazy :)


Załączyłem plik **atmoterm.zadanie.postman_collection.json**, w którym znajduje się kilkanaście zapytań
 testujących działanie aplikacji i endpointów. Plik ten można bardzo łatwo importować w programie Postman.




