--PASSENGER TRAINS

--Győr-Tatabánya
INSERT INTO
    station (train_id, distance, name, arrival_time, departure_time, platform)
VALUES
    (949, 0,    'Győr',                 NULL,       '06:21',    1),
    (949, 37,   'Komárom',              '06:37',    '06:38',    4),
    (949, 57,   'Tata',                 '06:50',    '06:51',    NULL),
    (949, 67,   'Tatabánya',            '06:57',    '06:58',    4),
    (949, 127,  'Budapest-Kelenföld',   '07:28',    '07:29',    14),
    (949, 140,  'Budapest-Keleti',      '07:44',    NULL,       9);
    
--Győr-Szombathely
INSERT INTO
    station (train_id, distance, name, arrival_time, departure_time, platform)
VALUES
    (922, 0,    'Győr',         NULL,       '09:45',    NULL),
    (922, 31,   'Csorna',       '10:25',    '10:34',    NULL),
    (922, 59,   'Répcelak',     '10:53',    '10:54',    NULL),
    (922, 103,  'Szombathely',  '11:24',    NULL,       NULL);
    
--Győr-Veszprém
INSERT INTO
    station (train_id, distance, name, arrival_time, departure_time, platform)
VALUES
    (39510, 0,  'Győr',                 NULL,       '05:13',    6),
    (39510, 2,  'Győr-Gyárváros',       '05:16',    '05:16',    NULL),
    (39510, 6,  'Győrszabadhegy',       '05:19',    '05:20',    NULL),
    (39510, 16, 'Nyúl',                 '05:31',    '05:31',    NULL),
    (39510, 21, 'Pannonhalma'           '05:37',    '05:38',    NULL),
    (39510, 27, 'Tarjánpuszta',         '05:45',    '05:46',    NULL),
    (39510, 28, 'Győrasszonyfa',        '05:48',    '05:48',    NULL),
    (39510, 34, 'Veszprémvarsány',      '05:55',    '05:56',    NULL),
    (39510, 38, 'Bakonygyirót',         '06:01',    '06:01',    NULL),
    (39510, 41, 'Bakonyszentlászló',    '06:05',    '06:09',    NULL),
    (39510, 45, 'Vinye',                '06:17',    '06:17',    NULL),
    (39510, 58, 'Zirc',                 '06:37',    '06:38',    NULL),
    (39510, 67, 'Eplény',               '06:50',    '06:51',    NULL),
    (39510, 79, 'Veszprém',             '07:10',    NULL,       6);
    
--Győr-Kaposvár
INSERT INTO
    station (train_id, distance, name, arrival_time, departure_time, platform)
VALUES
    (9605, 0,   'Győr',                 NULL,       '11:11',    4),
    (9605, 2,   'Győr-Gyárváros',       '11:14',    '11:14',    NULL),
    (9605, 6,   'Győrszabadhegy',       '11:17',    '11:18',    NULL),
    (9605, 25,  'Gyömöre-Tét',          '11:37',    '11:37',    NULL),
    (9605, 29,  'Szerecseny',           '11:42',    '11:42',    NULL),
    (9605, 39,  'Vaszar',               '11:50',    '11:50',    NULL),
    (9605, 47,  'Pápa',                 '11:58',    '12:02',    NULL),
    (9605, 72,  'Celldömölk',           '12:25',    '12:33',    2),
    (9605, 90,  'Jánosháza',            '12:46',    '12:46',    NULL),
    (9605, 108, 'Sümeg',                '13:00',    '13:01',    NULL),
    (9605, 128, 'Tapolca',              '13:18',    '13:19',    NULL),
    (9605, 138, 'Balatonederics',       '13:27',    '13:28',    NULL),
    (9605, 144, 'Balatongyörök',        '13:33',    '13:34',    NULL),
    (9605, 147, 'Vonyarcvashegy',       '13:37',    '13:38',    NULL),
    (9605, 149, 'Gyenesdiás',           '13:41',    '13:41',    NULL),
    (9605, 153, 'Keszthely',            '13:47',    '13:48',    NULL),
    (9605, 163, 'Balatonszentgyörgy',   '13:59',    '14:06',    1),
    (9605, 166, 'Balatonberény',        '14:09',    '14:09',    NULL),
    (9605, 170, 'Balatonmáriafürdő',    '14:13',    '14:14',    2),
    (9605, 175, 'Máriahullámtelep',     '14:18',    '14:18',    NULL),
    (9605, 177, 'Balatonfenyves alsó',  '14:21',    '14:21',    NULL),
    (9605, 179, 'Balatonfenyves',       '14:24',    '14:25',    NULL),
    (9605, 182, 'Alsóbélatelep',        '14:28',    '14:28',    NULL),
    (9605, 184, 'Bélatelep',            '14:31',    '14:31',    NULL),
    (9605, 186, 'Fonyód',               '14:36',    '14:46',    2),
    (9605, 198, 'Lengyeltóti',          '14:57',    '15:01',    NULL),
    (9605, 204, 'Öreglak',              '15:07',    '15:07',    NULL),
    (9605, 207, 'Somogyvár',            '15:10',    '15:11',    NULL),
    (9605, 210, 'Pamuk',                '15:14',    '15:14',    NULL),
    (9605, 215, 'Osztopán',             '15:19',    '15:20',    NULL),
    (9605, 220, 'Somogyjád',            '15:24',    '15:24',    NULL),
    (9605, 224, 'Várda',                '15:29',    '15:29',    NULL),
    (9605, 236, 'Kapostüskevár',        '15:39',    '15:39',    NULL),
    (9605, 239, 'Kaposvár',             '15:45',    NULL,       3);
    
--Tatabánya-Győr
INSERT INTO
    station (train_id, distance, name, arrival_time, departure_time, platform)
VALUES
    (912, 0,    'Budapest-Keleti',      NULL,       '06:15',    11),
    (912, 13,   'Budapest-Kelenföld',   '06:28',    '06:29',    15),
    (912, 73,   'Tatabánya',            '06:59',    '07:00',    3),
    (912, 83,   'Tata',                 '07:06',    '07:07',    NULL),
    (912, 103,  'Komárom',              '07:19',    '07:20',    3),
    (912, 140,  'Győr',                 '07:37',    '07:45',    2),
    (912, 171,  'Csorna',               '08:25',    '08:34',    NULL),
    (912, 199,  'Répcelak',             '08:53',    '08:54',    NULL),
    (912, 243,  'Szombathely',          '09:24',    NULL,       NULL);
    
--Szombathely-Győr
INSERT INTO
    station (train_id, distance, name, arrival_time, departure_time, platform)
VALUES
    (925, 0,    'Szombathely',  NULL,       '10:38',    NULL),
    (925, 44,   'Répcelak',     '11:10',    '11:11',    NULL),
    (925, 72,   'Csorna',       '11:30',    '11:35',    NULL),
    (925, 103,  'Győr',         '12:15',    NULL,       NULL);
    
--Szombathely-Budapest-Keleti
INSERT INTO
    station (train_id, distance, name, arrival_time, departure_time, platform)
VALUES
    (919, 0,    'Szentgotthárd',        NULL,       '04:31',    NULL),
    (919, 2,    'Haris',                '04:33',    '04:33',    NULL),
    (919, 9,    'Alsórönök',            '04:38',    '04:39',    NULL),
    (919, 13,   'Rátót',                '04:42',    '04:42',    NULL),
    (919, 19,   'Csákánydoroszló',      '04:45',    '04:46',    NULL),
    (919, 23,   'Horvátnádalja',        '04:49',    '04:49',    NULL),
    (919, 28,   'Körmend',              '04:53',    '04:55',    NULL),
    (919, 36,   'Egyházasrádóc',        '05:01',    '05:01',    NULL),
    (919, 45,   'Ják-Balogunyom',       '05:07',    '05:08',    NULL),
    (919, 50,   'Szombathely-Szőlős',   '05:12',    '05:12',    NULL),
    (919, 54,   'Szombathely',          '05:17',    '05:24',    NULL),
    (919, 98,   'Répcelak',             '05:52',    '05:53',    NULL),
    (919, 126,  'Csorna',               '06:12',    '06:17',    NULL),
    (919, 157,  'Győr',                 '06:57',    '07:03',    3),
    (919, 284,  'Budapest-Kelenföld',   '08:07',    '08:08',    14),
    (919, 297,  'Budapest-Keleti',      '08:23',    NULL,       13);
    
--Szombathely-Veszprém
INSERT INTO
    station (train_id, distance, name, arrival_time, departure_time, platform)
VALUES
    (9019, 0,    'Szombathely',      NULL,       '04:50',    NULL),
    (9019, 8,    'Vép',              '04:56',    '04:56',    NULL),
    (9019, 17,   'Porpác',           '05:01',    '05:02',    NULL),
    (9019, 24,   'Sárvár',           '05:09',    '05:10',    NULL),
    (9019, 33,   'Ostffyasszonyfa',  '05:16',    '05:17',    NULL),
    (9019, 37,   'Nagysimonyi',      '05:20',    '05:21',    NULL),
    (9019, 41,   'Kemenesmihályfa',  '05:25',    '05:25',    NULL),
    (9019, 45,   'Celldömölk',       '05:31',    '05:32',    5),
    (9019, 53,   'Nemeskocs',        '05:39',    '05:39',    NULL),
    (9019, 55,   'Boba',             '05:42',    '05:43',    NULL),
    (9019, 60,   'Kerta',            '05:47',    '05:48',    NULL),
    (9019, 64,   'Karakószörcsök',   '05:51',    '05:51',    NULL),
    (9019, 68,   'Tüskevár',         '05:54',    '05:55',    NULL),
    (9019, 71,   'Somlóvásárhely',   '05:58',    '05:58',    NULL),
    (9019, 76,   'Devecser',         '06:02',    '06:03',    NULL),
    (9019, 86,   'Ajka-Gyártelep',   '06:11',    '06:11',    NULL),
    (9019, 88,   'Ajka',             '06:14',    '06:15',    NULL),
    (9019, 94,   'Városlőd-Kislőd',  '06:21',    '06:21',    NULL),
    (9019, 96,   'Városlőd',         '06:23',    '06:24',    NULL),
    (9019, 104,  'Szentgál',         '06:31',    '06:31',    NULL),
    (9019, 110,  'Herend',           '06:36',    '06:37',    NULL),
    (9019, 114,  'Márkó',            '06:41',    '06:41',    NULL),
    (9019, 124,  'Veszprém',         '06:50',    NULL,       2);
    
--Szombathely-Zalaegerszeg
INSERT INTO
    station (train_id, distance, name, arrival_time, departure_time, platform)
VALUES
    (9826, 0,   'Szombathely',              NULL,       '14:37',    NULL),
    (9826, 5,   'Gyöngyöshermán',           '14:42',    '14:42',    NULL),
    (9826, 14,  'Sorkifalud',               '14:48',    '14:49',    NULL),
    (9826, 15,  'Szentléránt',              '14:50',    '14:50',    NULL),
    (9826, 20,  'Püspökmolnári',            '14:54',    '14:55',    NULL),
    (9826, 24,  'Vasvár',                   '14:59',    '15:00',    NULL),
    (9826, 33,  'Pácsony',                  '15:08',    '15:08',    NULL),
    (9826, 36,  'Győrvár',                  '15:10',    '15:10',    NULL),
    (9826, 42,  'Egervár-Vasboldogasszony', '15:14',    '15:15',    NULL),
    (9826, 45,  'Zalaszentlőrinc',          '15:18',    '15:18',    NULL),
    (9826, 49,  'Zalaszentiván',            '15:22',    '15:25',    NULL),
    (9826, 58,  'Zalaegerszeg',             '15:34',    NULL,       5);
    
--Szombathely-Székesfehérvár
INSERT INTO
    station (train_id, distance, name, arrival_time, departure_time, platform)
VALUES
    (909, 0,    'Szombathely',          NULL,       '06:24',    NULL),
    (909, 8,    'Vép',                  '06:30',    '06:31',    NULL),
    (909, 17,   'Porpác',               '06:37',    '06:37',    NULL),
    (909, 24,   'Sárvár',               '06:42',    '06:43',    NULL),
    (909, 33,   'Ostffyasszonyfa',      '06:49',    '06:52',    NULL),
    (909, 37,   'Nagysimonyi',          '06:55',    '06:55',    NULL),
    (909, 41,   'Kemenesmihályfa',      '06:59',    '06:59',    NULL),
    (909, 45,   'Celldömölk',           '07:03',    '07:04',    5),
    (909, 55,   'Boba',                 '07:12',    '07:13',    NULL),
    (909, 64,   'Karakószörcsök',       '07:29',    '07:29',    NULL),
    (909, 71,   'Somlóvásárhely',       '07:35',    '07:35',    NULL),
    (909, 76,   'Devecser',             '07:39',    '07:40',    NULL),
    (909, 88,   'Ajka',                 '07:50',    '07:51',    NULL),
    (909, 124,  'Veszprém',             '08:26',    '08:32',    6),
    (909, 132,  'Hajmáskér',            '08:38',    '08:39',    NULL),
    (909, 138,  'Öskü',                 '08:44',    '08:44',    NULL),
    (909, 142,  'Pétfürdő',             '08:48',    '08:49',    NULL),
    (909, 146,  'Várpalota',            '08:53',    '08:54',    NULL),
    (909, 169,  'Székesfehérvár',       '09:11',    '09:12',    6),
    (909, 232,  'Budapest-Kelenföld',   '09:51',    '09:52',    6),
    (909, 236,  'Budapest-Déli',        '09:59',    NULL,       7);
    
--Szombathely-Pécs
INSERT INTO
    station (train_id, distance, name, arrival_time, departure_time, platform)
VALUES
    (8900, 0,   'Szombathely',              NULL,       '05:09',    NULL),
    (8900, 20,  'Püspökmolnári',            '05:24',    '05:26',    NULL),
    (8900, 24,  'Vasvár',                   '05:30',    '05:33',    NULL),
    (8900, 49,  'Zalaszentiván',            '05:54',    '05:58',    NULL),
    (8900, 61,  'Búcsúszentlászló',         '06:08',    '06:08',    NULL),
    (8900, 69,  'Zalaszentmihály-Pacsa',    '06:14',    '06:14',    NULL),
    (8900, 74,  'Pötréte',                  '06:18',    '06:18',    NULL),
    (8900, 84,  'Gelse',                    '06:28',    '06:28',    NULL),
    (8900, 102, 'Nagykanizsa',              '06:47',    '06:49',    1),
    (8900, 116, 'Murakeresztúr',            '06:58',    '06:59',    NULL),
    (8900, 123, 'Őrtilos',                  '07:06',    '07:06',    NULL),
    (8900, 131, 'Gyékényes',                '07:15',    '07:25',    NULL),
    (8900, 148, 'Berzence',                 '07:39',    '07:40',    NULL),
    (8900, 153, 'Somogyudvarhely',          '07:45',    '07:45',    NULL),
    (8900, 158, 'Bélavár',                  '07:50',    '07:50',    NULL),
    (8900, 162, 'Vízvár',                   '07:53',    '08:01',    NULL),
    (8900, 173, 'Babócsa',                  '08:13',    '08:14',    NULL),
    (8900, 186, 'Barcs',                    '08:26',    '08:27',    NULL),
    (8900, 199, 'Darány',                   '08:40',    '08:40',    NULL),
    (8900, 216, 'Szigetvár',                '08:54',    '08:58',    NULL),
    (8900, 231, 'Szentlőrinc',              '09:15',    '09:22',    NULL),
    (8900, 250, 'Pécs',                     '09:37',    NULL,       4);
    
--Zalaegerszeg-Szombathely
INSERT INTO
    station (train_id, distance, name, arrival_time, departure_time, platform)
VALUES
    (9819, 0,   'Zalaegerszeg',             NULL,       '03:49',    4),
    (9819, 9,   'Zalaszentiván',            '03:57',    '04:00',    NULL),
    (9819, 13,  'Zalaszentlőrinc',          '04:04',    '04:04',    NULL),
    (9819, 16,  'Egervár-Vasboldogasszony', '04:07',    '04:08',    NULL),
    (9819, 22,  'Győrvár',                  '04:12',    '04:12',    NULL),
    (9819, 25,  'Pácsony',                  '04:14',    '04:14',    NULL),
    (9819, 34,  'Vasvár',                   '04:22',    '04:23',    NULL),
    (9819, 38,  'Püspökmolnári',            '04:27',    '04:28',    NULL),
    (9819, 43,  'Szentléránt',              '04:32',    '04:32',    NULL),
    (9819, 44,  'Sorkifalud',               '04:33',    '04:34',    NULL),
    (9819, 53,  'Gyöngyöshermán',           '04:40',    '04:40',    NULL),
    (9819, 58,  'Szombathely',              '04:45',    NULL,       NULL);
    
--Zalaegerszeg-Budapest-Déli
INSERT INTO
    station (train_id, distance, name, arrival_time, departure_time, platform)
VALUES
    (959, 0,    'Zalaegerszeg',         NULL,       '05:49',    3),
    (959, 9,    'Zalaszentiván',        '05:57',    '06:00',    NULL),
    (959, 25,   'Zalabér-Batyk',        '06:10',    '06:11',    NULL),
    (959, 40,   'Ukk',                  '06:22',    '06:23',    NULL),
    (959, 50,   'Jánosháza',            '06:29',    '06:29',    NULL),
    (959, 91,   'Ajka',                 '06:58',    '07:00',    NULL),
    (959, 127,  'Veszprém',             '07:31',    '07:32',    6),
    (959, 145,  'Pétfürdő',             '07:48',    '07:49',    NULL),
    (959, 149,  'Várpalota',            '07:53',    '07:54',    NULL),
    (959, 172,  'Székesfehérvár',       '08:11',    '08:12',    6),
    (959, 235,  'Budapest-Kelenföld',   '08:51',    '08:52',    6),
    (959, 239,  'Budapest-Déli',        '08:59',    NULL,       7);
    
--Veszprém-Győr
INSERT INTO
    station (train_id, distance, name, arrival_time, departure_time, platform)
VALUES
    (39519, 0,  'Veszprém',             NULL,       '05:05',    4),
    (39519, 21, 'Zirc',                 '05:34',    '05:35',    NULL),
    (39519, 34, 'Vinye',                '05:54',    '05:54',    NULL),
    (39519, 38, 'Bakonyszentlászló',    '06:03',    '06:14',    NULL),
    (39519, 41, 'Bakonygyirót',         '06:18',    '06:18',    NULL),
    (39519, 45, 'Veszprémvarsány',      '06:23',    '06:24',    NULL),
    (39519, 51, 'Győrasszonyfa',        '06:31',    '06:31',    NULL),
    (39519, 52, 'Tarjánpuszta',         '06:33',    '06:34',    NULL),
    (39519, 58, 'Pannonhalma',          '06:41',    '06:45',    NULL),
    (39519, 63, 'Nyúl',                 '06:51',    '06:51',    NULL),
    (39519, 73, 'Győrszabadhegy',       '07:03',    '07:05',    NULL),
    (39519, 77, 'Győr-Gyárváros',       '07:09',    '07:09',    NULL),
    (39519, 79, 'Győr',                 '07:13',    NULL,       4);
    
--Veszprém-Szombathely
INSERT INTO
    station (train_id, distance, name, arrival_time, departure_time, platform)
VALUES
    (900, 0,    'Budapest-Déli',        NULL,       '06:00',    6),
    (900, 4,    'Budapest-Kelenföld',   '06:06',    '06:07',    4),
    (900, 67,   'Székesfehérvár',       '06:45',    '06:46',    5),
    (900, 90,   'Várpalota',            '07:05',    '07:06',    NULL),
    (900, 94,   'Pétfürdő',             '07:10',    '07:11',    NULL),
    (900, 98,   'Öskü',                 '07:15',    '07:15',    NULL),
    (900, 104,  'Hajmáskér',            '07:20',    '07:21',    NULL),
    (900, 112,  'Veszprém',             '07:28',    '07:34',    3),
    (900, 148,  'Ajka',                 '08:05',    '08:06',    NULL),
    (900, 160,  'Devecser',             '08:15',    '08:16',    NULL),
    (900, 181,  'Boba',                 '08:30',    '08:31',    NULL),
    (900, 191,  'Celldömölk',           '08:40',    '08:41',    5),
    (900, 212,  'Sárvár',               '08:58',    '09:00',    NULL),
    (900, 236,  'Szombathely',          '09:17',    NULL,       NULL );