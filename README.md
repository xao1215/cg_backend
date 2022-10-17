Postgres and Redis Docker instances
```
docker run -d -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=db -p 5432:5432
docker run -d redis -p 6379:6379
```
Run/test the Spring app
```
./mvnw spring-boot:run
./mvnw test
```
Endpoints
- http://localhost:8080/api/eat (POST)
- http://localhost:8080/api/eat/{id} (GET, DELETE, UPDATE)
- http://localhost:8080/api/eat/timeperiod/{assetId} (GET)
- http://localhost:8080/api/eat/latest/{assetId} (GET)

POST object example
```
{
    "activePower":100,
    "voltage": 100,
    "assetId":"fbb12efa-1954-474c-9849-d719d09f156c",
    "timestamp": "2000-01-01 01:00:00.000"
}
```
/timeperiod body
```
{
    "from":"2000-01-01 01:00:00.000",
    "to":"2100-01-01 01:00:00.000"
}
```