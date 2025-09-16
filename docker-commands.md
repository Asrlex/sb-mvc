# Docker

## Build image

```bash
docker build -t <image_name>:<tag> .
```

## Run container

```bash
docker run -d -p 8080:8080 --name <container_name>
```

## Pass environment variables

```bash
docker run -d -p 8080:8080 --name <container_name> \
    -e X_API_KEY=mysecret \
    -e SPRING_DATASOURCE_URL=jdbc:postgresql://<db_host>:<db_port>/<db_name> \
    -e SPRING_DATASOURCE_USERNAME=<db_username> \
    -e SPRING_DATASOURCE_PASSWORD=<db_password> \
    -e JWT_SECRET=<your_jwt_secret> \
    <image_name>:<tag>
```

## Docker Compose

```bash
# For development with SQLite
docker compose -f ./compose.dev.yaml up --build

# For production with Postgres
docker compose -f ./compose.yaml up --build

# To stop and remove containers
docker compose -f compose.dev.yaml down
docker compose -f compose.yaml down
```