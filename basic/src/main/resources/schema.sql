create table company (
    id serial not null,
    name text
);

ALTER TABLE company ADD CONSTRAINT pk_company PRIMARY KEY (id);

create table employee (
    id serial not null,
    name text,
    salary numeric(16,2),
    company_id int
);

ALTER TABLE employee ADD CONSTRAINT pk_employee PRIMARY KEY (id);
ALTER TABLE employee ADD CONSTRAINT fk_employee_company FOREIGN KEY (company_id) REFERENCES company(id);
