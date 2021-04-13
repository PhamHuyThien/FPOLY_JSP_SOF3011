
create database assj4;
use assj4;


create table users(
    id int not null primary key auto_increment,
    user varchar(50) not null unique,
    pass varchar(50),
    name varchar(100),
    email varchar(100),
    phone varchar(15),
    role bit,
    status bit,
    time int
);


create table videos(
    id int not null primary key auto_increment,
    title varchar(500),
    poster varchar(100),
    views int,
    description varchar(500),
    status int,
    time int
);


create table favorites(
    id int not null primary key auto_increment,
    id_users int not null,
    id_videos int not null,
    time int
);

create table shares(
    id int not null primary key auto_increment,
    id_users int not null, 
    id_videos int not null, 
    email varchar(100),
    time int
);