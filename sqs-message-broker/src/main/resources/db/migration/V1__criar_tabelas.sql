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