For start app: 
1. Upadte tmdb.apikey in src\main\resources\application.properties
2. Run mvn spring-boot:run
3. Open http://localhost:8080/swagger-ui.html 

4. http://localhost:8080/user
5. curl -X POST --data "email=test@test2.com&password=test" http://localhost:8080/user/register/ -v -u user:password