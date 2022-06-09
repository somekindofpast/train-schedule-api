alter table if exists cargo drop constraint if exists FK274ox1jmt7aa7bryxkn93v14b;
alter table if exists service drop constraint if exists FK6b6bpw3o2y8w606dhyhfrtl0g;
alter table if exists stop drop constraint if exists FKtmy8xl2r4b7m5on4kqbahxk26;

drop table if exists cargo cascade;
drop table if exists service cascade;
drop table if exists stop cascade;
drop table if exists train cascade;

create table cargo
(
    id       bigserial not null,
    car_type varchar(255),
    name     varchar(255),
    train_id int8,
    primary key (id)
);
create table service
(
    id                     bigserial not null,
    any_weather_condition  boolean,
    bicycle_reservation    boolean,
    budapest_pass          boolean,
    first_class            boolean,
    long_distance          boolean,
    reservation_compulsory boolean,
    second_class           boolean,
    supplement_compulsory  boolean,
    wheelchair_access      boolean,
    train_id               int8,
    primary key (id)
);
create table stop
(
    id             bigserial not null,
    arrival_time   time,
    departure_time time,
    distance       int4,
    name           varchar(255),
    platform       int4,
    train_id       int8,
    primary key (id)
);
create table train
(
    id   int8 not null,
    type varchar(255),
    primary key (id)
);

alter table if exists cargo add constraint FK274ox1jmt7aa7bryxkn93v14b foreign key (train_id) references train;
alter table if exists service add constraint FK6b6bpw3o2y8w606dhyhfrtl0g foreign key (train_id) references train;
alter table if exists stop add constraint FKtmy8xl2r4b7m5on4kqbahxk26 foreign key (train_id) references train;