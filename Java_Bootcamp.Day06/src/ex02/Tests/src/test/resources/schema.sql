drop table if exists Store.Products;
drop schema if exists Store;

create schema if not exists Store;

create table if not exists Store.Products (
    id identity primary key,
    name varchar(50) not null unique,
    price numeric(50) not null
);