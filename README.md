___ Swagger ___ <br /><br />
auth-microservice: http://localhost:8085/swagger-ui/index.html <br />
user-microservice: http://localhost:8086/swagger-ui/index.html 
<br /><br />

___ auth-microservice PostgreSQL Docker Image ___ <br />

docker run -d --name authservice-postgres --env POSTGRES_DB=authdb --env POSTGRES_USER=user --env POSTGRES_PASSWORD=123 -p 5432:5432 postgres:latest 
<br /><br />

___ user-microservice MongoDB Docker Image ___ <br />

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
<br /><br />

___ Creating Docker Images for Projects ___ <br />

1) Maven Plugin Approach (auth-microservice & user-microservice) <br />
- application.properties file better to be UTF-8
- <artifactId>spring-boot-maven-plugin</artifactId> should be in pom.xml <br />
- <name>dockerhubusername/anyname-${project.artifactId}:${project.version}</name> <br />
- Maven > Plugins > spring-boot > spring-boot:build-image command should be executed <br /><br />

2) Dockerfile Approach (git-config-server) <br />
- At first, Maven > package command should be executed <br />
- After the execution, there should be .jar file inside the target folder <br />
- Dockerfile should be created in project's main directory <br />
- After the configuration is done, one of the commands below should be run: <br /><br />
For Local Image:       docker build -t myimage . <br />
For Docker Hub Image:  docker build -t dockerhubusername/myimage . <br /><br />

Docker Hub Images: <br />
https://hub.docker.com/repository/docker/ahmetyeniceri/mmv2-auth-microservice/general <br />
https://hub.docker.com/repository/docker/ahmetyeniceri/mmv2-user-microservice/general <br />
https://hub.docker.com/repository/docker/ahmetyeniceri/git-config-server-0.0.1/general <br /><br />

NOTES: 
- Images can be push to Dockerhub in Docker Desktop. <br />
- In both approaches, Docker Compose can be used for easier management. <br /><br />

___ Kubernetes - Google Cloud ___ <br />

gcloud container clusters get-credentials cluster-name --region us-central1 --project project-name
<br />

kubectl apply -f deployment-service.yaml
<br />

kubectl get deployment	--->  Deployment check <br />
kubectl get service 	--->  Service check <br />
kubectl get pods		--->  Pod check <br />
kubectl get rs			--->  ReplicaSet check <br />
