create sequence if not exists product_seq increment by 50;
create sequence if not exists category_seq increment by 50;

create table if not exists category(
    id integer primary key,
    name varchar(255) not null,
    description varchar(255)
);

create table if not exists product(
    id integer primary key,
    name varchar(255) not null,
    description varchar(255),
    available_quantity integer not null check(available_quantity > -1),
    price numeric(32, 2) not null check(price > 0),
    category_id integer constraint fk_category_id references category
);

-- Ensure sequence ownership
alter sequence product_seq owned by product.id;
alter sequence category_seq owned by category.id;
