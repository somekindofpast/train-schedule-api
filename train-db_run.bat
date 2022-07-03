docker network rm train-network
docker network create train-network
docker stop train-db
docker run -it --rm --name train-db --network=train-network -e POSTGRES_DB=train_schedule_test -e POSTGRES_USER=flywayuser -e POSTGRES_PASSWORD=flypass -p 5433:5432 postgres:14.3-alpine