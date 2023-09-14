create table if not exists optimistic_lock (
	id serial NOT NULL,
	name text not null,
	"version" bigint null,
	update_date timestamp
);

create table if not exists pessimist_lock (
	id serial NOT NULL,
	name text not null,
	update_date timestamp
);

create table if not exists orders (
	id serial NOT NULL,
    price NUMERIC(16,2) not null,
	quantity int not null,
	update_date timestamp
);

create table message (
    id serial not null,
    text text,
    creation_date timestamp
);