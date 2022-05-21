--PASSENGER TRAINS

--Győr
INSERT INTO
    service (train_id, suburban, long_distance, first_class, second_class, reservation_compulsory,
             supplement_compulsory, wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (949,   FALSE,  TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   FALSE),
    (949,   FALSE,  TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   FALSE,  FALSE,  TRUE,   FALSE),
    (39510, FALSE,  FALSE,  FALSE,  TRUE,   FALSE,  FALSE,  FALSE,  TRUE,   TRUE,   FALSE),
    (39510, FALSE,  TRUE,   FALSE,  TRUE,   FALSE,  FALSE,  TRUE,   TRUE,   TRUE,   FALSE);
    
--Tatabánya
INSERT INTO
    service (train_id, suburban, long_distance, first_class, second_class, reservation_compulsory,
             supplement_compulsory, wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (912, FALSE,  TRUE,   TRUE,  TRUE,   TRUE,  TRUE,  TRUE,   TRUE,   TRUE,   FALSE);
    
--Szombathely
INSERT INTO
    service (train_id, suburban, long_distance, first_class, second_class, reservation_compulsory,
             supplement_compulsory, wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (925,   FALSE,  TRUE,   FALSE,  TRUE,   TRUE,   TRUE,   FALSE,  TRUE,   TRUE,   FALSE),
    (919,   FALSE,  TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   FALSE),
    (9019,  FALSE,  TRUE,   FALSE,  TRUE,   FALSE,  FALSE,  FALSE,  FALSE,  TRUE,   FALSE),
    (9826,  FALSE,  FALSE,  FALSE,  TRUE,   FALSE,  FALSE,  FALSE,  TRUE,   FALSE,  FALSE),
    (909,   FALSE,  TRUE,   FALSE,  TRUE,   TRUE,   FALSE,  TRUE,   TRUE,   TRUE,   FALSE),
    (8900,  FALSE,  TRUE,   FALSE,  TRUE,   FALSE,  FALSE,  TRUE,   TRUE,   TRUE,   FALSE);
    
--Zalaegerszeg
INSERT INTO
    service (train_id, suburban, long_distance, first_class, second_class, reservation_compulsory,
             supplement_compulsory, wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (9819,  FALSE,  FALSE,  FALSE,  TRUE,   FALSE,  FALSE,  FALSE,  TRUE,   FALSE,  FALSE),
    (959,   FALSE,  TRUE,   FALSE,  TRUE,   TRUE,   FALSE,  TRUE,   TRUE,   TRUE,   FALSE);
    
--Veszprém
INSERT INTO
    service (train_id, suburban, long_distance, first_class, second_class, reservation_compulsory,
             supplement_compulsory, wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (39519, FALSE,  FALSE,  FALSE,  TRUE,   FALSE,  FALSE,  FALSE,  FALSE,  TRUE,   FALSE),
    (900,   FALSE,  TRUE,   FALSE,  TRUE,   TRUE,   FALSE,  TRUE,   TRUE,   TRUE,   FALSE);