For start app: 
1. Upadte tmdb.apikey in src\main\resources\application.properties
2. Run mvn spring-boot:run
3. Open http://localhost:8080/swagger-ui.html 

4. http://localhost:8080/user
5. curl -X POST --data "email=test@test2.com&password=test" http://localhost:8080/user/register/ -v -u user:password

6. Add favorite Actor
curl -X POST --data "email=test@test2.com&password=test&name=Jennifer&lastName=Connelly" http://localhost:8080/tv/addActor/ -u user:password
7. Remove favorite actor
curl -X POST --data "email=test@test2.com&password=test&name=Jennifer&lastName=Connelly" http://localhost:8080/tv/removeActor/ -u user:password
8. Add watched show
curl -X POST --data "email=test@test2.com&password=test&tvShowName=snowpiercer" http://localhost:8080/tv/addWatchedTv/ -u user:password
9. Remove watched Tv show
curl -X POST --data "email=test@test2.com&password=test&tvShowName=snowpiercer" http://localhost:8080/tv/unSelectWatchedTv/ -u user:password