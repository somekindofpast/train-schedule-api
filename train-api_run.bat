docker run -it --rm --name train-api --network=train-network -e DATABASE_NAME=train_schedule_test -e DATABASE_USERNAME=flywayuser -e DATABASE_PASSWORD=flypass -e DATABASE_URL=jdbc:postgresql://train-db:5432/train_schedule_test -p 8080:8080 trainapp