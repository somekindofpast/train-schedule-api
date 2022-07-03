# train-schedule-api

Ennek a REST API-nak a célja vonatok és vonat menetrendek kilistázása a felhasználó számára.

A program hátterében egy PostgreSQL adatbázis fut, mely négy táblával rendelkezik:  
- Train (a fő tábla, mely kapcsolódik a többi táblához és tartalmazza a vonat típusát)  
- Stop (N:1 kapcsolat a Train-nel, állomás név, érkezés/távozás ideje, indulás óta megtett táv, platform szám)  
- Service (1:1 kapcsolat a Train-nel, személyvonatokhoz tartozó adatok, például első/másodosztály, helyjegyköteles, stb)  
- Cargo (N:1 kapcsolat a Train-nel, tehervonatokhoz tartozó szerelvények típusai és a rakományok nevei)  

![](src/train_schedule_test.png)
## DatabaseBuilder:  
Az API indításkor az adatbázis alaphelyzetbe áll. Ehhez lefut a DatabaseBuilder alkalmazás, mely a FlyWay migrációs  
eszköz segítségével tisztít, majd létrehozza az adattáblákat, és feltölti őket alapértékekkel. Ezután az alkalmazás  
a meglévő adatok alapján generál még párat, hogy nagyobb legyen az adatbázis.

## TrainScheduleApiApplication:
Az adatbázis legenerálását követően feláll a Spring Boot környezet, és kapcsolódik az adatbázishoz. Ezekután böngésző  
vagy API kliens segítségével (pl Postman) lekérdezéseket küldhetünk az API-nak. A lekérdezések eredménye logolásra  
kerül a terminálon, illetve hiba esetén a hiba típusa és hibaüzenet. Az endpointok megtekinthetők a Swagger UI  
elindításával. (http://localhost:8080/swagger-ui.html)

## Fő endpointok:  
### GET: /schedule/passenger/{departure_location}/{arrival_location}  
Az induló és érkező településnevet megadva visszakapunk egy menetrend listát azokról a személyvonatokról, melyek  
áthaladnak ezeken a településeken, indulási idejük szerint sorrenbe rakva. Kikalkulálódik emellett a megteendő út 
hossza és az utazás ideje.  
### GET: /schedule/freight/{departure_location}/{arrival_location}  
Az előző lekérdezés tehervonatos változata.
### GET: /train/freight/{cargo_name}  
A rakomány nevét megadva kilistázásra kerül az összes tehervonat mely szállít ilyen rakományt.
## Hogyan futtassuk a programot:
### Fejlesztő környezettel:
Az egyik lehetőség a fejlesztő környezet (például IntelliJ) használata. Ehhez szükség lesz egy PostgreSQL adatbázis  
létrehozására, majd a fejlesztő környezetben is be kell állítani az adatbázis kapcsolathoz szükséges környezeti  
változókat (adatbázis név, felhasználó név, felhasználó jelszó, adatbázis url).
### Docker konténerizációval:
A projektmappában megtalálhatóak azok a .bat fájlok melyekkel a projekt és az adatbázis futtatható lesz Docker konténerben.  
Először indítsuk el a Docker alkalmazást (Docker Desktop), majd futtassuk le parancssorban ezeket a bat fájlokat:
- docker_build: a Dockerfile alapján létrehozza az API Docker image-ét. Ezt a fájlt elég csak első alkalommal futtatni.
- train-db_run: letölti a PostgresSQL Docker image-ét, létrehozza a Docker konténert, és elindítja az adatbázist.
- train-api_run: létrehozza az API Docker konténerét, és elindítja az API-t. (**figyelem:** az adatbázis legenerálása több percig is eltarthat Dockerben)