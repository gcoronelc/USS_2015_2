create database cjava;

use cjava;

create table producto(
codigo int not null auto_increment,
nombre varchar(100) not null,
precio numeric(10,2) not null,
stock numeric(8,0) not null,
constraint pk_producto primary key(codigo)
);

insert into producto(nombre,precio,stock) values
('producto 1',4056.34,65),
('producto 2',5678.43,90),
('producto 3',234.65,98);




