CREATE TABLE IF NOT EXISTS train (
                                        id INTEGER PRIMARY KEY NOT NULL,
                                        type VARCHAR(20) NOT NULL,
                                        highest_class SMALLINT
);

CREATE TABLE IF NOT EXISTS stop (
                                        id SERIAL PRIMARY KEY,
                                        train_id INTEGER NOT NULL,
                                        distance INTEGER NOT NULL,
                                        name VARCHAR(255) NOT NULL,
                                        arrival_time TIME,
                                        departure_time TIME,
                                        platform INTEGER
);

CREATE TABLE IF NOT EXISTS service (
                                        id SERIAL PRIMARY KEY,
                                        train_id INTEGER NOT NULL,
                                        long_distance BOOLEAN DEFAULT FALSE,
                                        first_class BOOLEAN DEFAULT FALSE,
                                        second_class BOOLEAN DEFAULT FALSE,
                                        reservation_compulsory BOOLEAN DEFAULT FALSE,
                                        supplement_compulsory BOOLEAN DEFAULT FALSE,
                                        wheelchair_access BOOLEAN DEFAULT FALSE,
                                        bicycle_reservation BOOLEAN DEFAULT FALSE,
                                        any_weather_condition BOOLEAN DEFAULT FALSE,
                                        budapest_pass BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS cargo (
                                        id SERIAL PRIMARY KEY,
                                        train_id INTEGER NOT NULL,
                                        name VARCHAR(30) NOT NULL,
                                        car_type VARCHAR(30) NOT NULL
);