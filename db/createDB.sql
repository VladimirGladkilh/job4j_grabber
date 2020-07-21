create database grabber;
create table rabbit (id serial primary key,  created_date timestamp without time zone);
create table post (id serial primary key, subject varchar(512), link varchar(1024),
 description text, create_date timestamp without time zone,
 CONSTRAINT link UNIQUE (link));