--PASSENGER TRAINS

--Győr
INSERT INTO
    service (train_id, long_distance, first_class, second_class, reservation_compulsory, supplement_compulsory,
             wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (949,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   FALSE),
    (922,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   FALSE,  FALSE,  TRUE,   FALSE),
    (39510, FALSE,  FALSE,  TRUE,   FALSE,  FALSE,  FALSE,  TRUE,   TRUE,   FALSE),
    (9605,  TRUE,   FALSE,  TRUE,   FALSE,  FALSE,  TRUE,   TRUE,   TRUE,   FALSE);
    
--Tatabánya
INSERT INTO
    service (train_id, long_distance, first_class, second_class, reservation_compulsory, supplement_compulsory,
             wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (912, TRUE,   TRUE,  TRUE,   TRUE,  TRUE,  TRUE,   TRUE,   TRUE,   FALSE);
    
--Szombathely
INSERT INTO
    service (train_id, long_distance, first_class, second_class, reservation_compulsory, supplement_compulsory,
             wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (925,   TRUE,   FALSE,  TRUE,   TRUE,   TRUE,   FALSE,  TRUE,   TRUE,   FALSE),
    (919,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   FALSE),
    (9019,  TRUE,   FALSE,  TRUE,   FALSE,  FALSE,  FALSE,  FALSE,  TRUE,   FALSE),
    (9826,  FALSE,  FALSE,  TRUE,   FALSE,  FALSE,  FALSE,  TRUE,   FALSE,  FALSE),
    (909,   TRUE,   FALSE,  TRUE,   TRUE,   FALSE,  TRUE,   TRUE,   TRUE,   FALSE),
    (8900,  TRUE,   FALSE,  TRUE,   FALSE,  FALSE,  TRUE,   TRUE,   TRUE,   FALSE);
    
--Zalaegerszeg
INSERT INTO
    service (train_id, long_distance, first_class, second_class, reservation_compulsory, supplement_compulsory,
             wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (9819,  FALSE,  FALSE,  TRUE,   FALSE,  FALSE,  FALSE,  TRUE,   FALSE,  FALSE),
    (959,   TRUE,   FALSE,  TRUE,   TRUE,   FALSE,  TRUE,   TRUE,   TRUE,   FALSE);
    
--Veszprém
INSERT INTO
    service (train_id, long_distance, first_class, second_class, reservation_compulsory, supplement_compulsory,
             wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (39519, FALSE,  FALSE,  TRUE,   FALSE,  FALSE,  FALSE,  FALSE,  TRUE,   FALSE),
    (900,   TRUE,   FALSE,  TRUE,   TRUE,   FALSE,  TRUE,   TRUE,   TRUE,   FALSE),
    (952,   TRUE,   FALSE,  TRUE,   TRUE,   FALSE,  TRUE,   TRUE,   TRUE,   FALSE);
    
--Székesfehérvár
INSERT INTO
    service (train_id, long_distance, first_class, second_class, reservation_compulsory, supplement_compulsory,
             wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (8392,   TRUE,   FALSE,  TRUE,   FALSE,   FALSE,  TRUE,   TRUE,   TRUE,   FALSE);
    
--Kaposvár
INSERT INTO
    service (train_id, long_distance, first_class, second_class, reservation_compulsory, supplement_compulsory,
             wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (9690,  TRUE,   FALSE,  TRUE,   FALSE,   FALSE,  TRUE,   TRUE,   TRUE,   FALSE),
    (829,   TRUE,   FALSE,  TRUE,   TRUE,   FALSE,  FALSE,   TRUE,   TRUE,   FALSE);
    
--Szekszárd
INSERT INTO
    service (train_id, long_distance, first_class, second_class, reservation_compulsory, supplement_compulsory,
             wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (8309,   TRUE,   FALSE,  TRUE,   FALSE,   FALSE,  TRUE,   TRUE,   TRUE,   FALSE);
    
--Budapest
INSERT INTO
    service (train_id, long_distance, first_class, second_class, reservation_compulsory, supplement_compulsory,
             wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (822,   TRUE,   FALSE,  TRUE,   TRUE,   FALSE,  FALSE,  TRUE,   TRUE,   FALSE),
    (6110,  TRUE,   FALSE,  TRUE,   FALSE,  FALSE,  TRUE,   TRUE,   FALSE,  TRUE),
    (182,   TRUE,   TRUE,   TRUE,   TRUE,   FALSE,  TRUE,   TRUE,   TRUE,   FALSE),
    (540,   TRUE,   FALSE,  TRUE,   FALSE,  FALSE,  TRUE,   TRUE,   TRUE,   TRUE),
    (700,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   FALSE,  TRUE,   TRUE,   TRUE),
    (7400,  TRUE,   FALSE,  TRUE,   TRUE,   FALSE,  FALSE,  TRUE,   TRUE,   FALSE);
    
--Eger
INSERT INTO
    service (train_id, long_distance, first_class, second_class, reservation_compulsory, supplement_compulsory,
             wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (5509,   TRUE,   FALSE,  TRUE,   FALSE,   FALSE,  TRUE,   TRUE,   TRUE,   TRUE);
    
--Miskolc
INSERT INTO
    service (train_id, long_distance, first_class, second_class, reservation_compulsory, supplement_compulsory,
             wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (5140,  FALSE,  FALSE,  TRUE,   FALSE,  FALSE,  FALSE,  FALSE,  TRUE,   FALSE),
    (582,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE);
    
--Nyíregyháza
INSERT INTO
    service (train_id, long_distance, first_class, second_class, reservation_compulsory, supplement_compulsory,
             wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (5119,   FALSE,  FALSE,  TRUE,   FALSE,  FALSE,  FALSE,  FALSE,  TRUE,   FALSE);
    
--Debrecen
INSERT INTO
    service (train_id, long_distance, first_class, second_class, reservation_compulsory, supplement_compulsory,
             wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (682,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   TRUE,   FALSE);
    
--Szolnok
INSERT INTO
    service (train_id, long_distance, first_class, second_class, reservation_compulsory, supplement_compulsory,
             wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (37117,   FALSE,  FALSE,  TRUE,   FALSE,  FALSE,  FALSE,  FALSE,  TRUE,   FALSE);
    
--Kecskemét
INSERT INTO
    service (train_id, long_distance, first_class, second_class, reservation_compulsory, supplement_compulsory,
             wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (37120,   FALSE,  FALSE,  TRUE,   FALSE,  FALSE,  FALSE,  FALSE,  TRUE,   FALSE);
    
--Szeged
INSERT INTO
    service (train_id, long_distance, first_class, second_class, reservation_compulsory, supplement_compulsory,
             wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (7009,   TRUE,  FALSE,  TRUE,   FALSE,  FALSE,  FALSE,  TRUE,   TRUE,   TRUE),
    (7740,   FALSE, FALSE,  TRUE,   FALSE,  FALSE,  FALSE,  FALSE,  TRUE,   FALSE);
    
--Békéscsaba
INSERT INTO
    service (train_id, long_distance, first_class, second_class, reservation_compulsory, supplement_compulsory,
             wheelchair_access, bicycle_reservation, any_weather_condition, budapest_pass)
VALUES
    (7407,   TRUE,  FALSE,  TRUE,   TRUE,   FALSE,  FALSE,  TRUE,   TRUE,   FALSE),
    (7749,   FALSE, FALSE,  TRUE,   FALSE,  FALSE,  FALSE,  FALSE,  TRUE,   FALSE);