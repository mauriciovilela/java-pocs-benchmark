CREATE TABLE users(
   id serial PRIMARY KEY,
   first_name text,
   state VARCHAR(10)
);

CREATE TABLE product(
   id serial PRIMARY KEY,
   description text,
   price numeric (16,2) NOT NULL
);

CREATE TABLE purchase_order(
    id serial PRIMARY KEY,
    user_id integer references users (id),
    product_id integer references product (id)
);

create MATERIALIZED VIEW purchase_order_summary
AS
select
    u.state,
    sum(p.price) as total_sale
from
    users u,
    product p,
    purchase_order po
where
    u.id = po.user_id
    and p.id = po.product_id
group by u.state
order by u.state

WITH NO DATA;
CREATE UNIQUE INDEX state_category ON purchase_order_summary (state);
