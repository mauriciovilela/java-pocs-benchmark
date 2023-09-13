CREATE TABLE IF NOT EXISTS employee (
  id serial NOT NULL,
  name text,
  status text,
  PRIMARY KEY (id)
);

create table log (
    id serial not null,
    text text,
    creation_date timestamp
);