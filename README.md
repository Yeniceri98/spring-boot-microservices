___ Swagger ___ <br /><br />
auth-microservice: http://localhost:8085/swagger-ui/index.html <br />
user-microservice: http://localhost:8086/swagger-ui/index.html 
<br /><br />

___ auth-microservice Docker Image ___ <br />

docker run -d --name authservice-postgres --env POSTGRES_DB=authdb --env POSTGRES_USER=user --env POSTGRES_PASSWORD=123 -p 5432:5432 postgres:latest 
<br /><br />

___ user-microservice Docker Image ___ <br />

docker run -d --name userservice-mongo -p 27017:27017 -e MONGO_INITDB_DATABASE=userdb mongo:latest
<br /><br />

___ MongoDB Compass ___ <br /><br />
Select DB: use userdb <br />
Read & Write Permision: ({user: "user",pwd: "123",roles: ["readWrite","dbAdmin"]})
<br /><br />

___ Redis Docker Image ___ <br />

Redis: docker run --name microservice-redis -p 6379:6379 -d redis
<br />

Redis GUI (RedisInsight): docker run -d --name redisinsight -p 8001:8001 redislabs/redisinsight
