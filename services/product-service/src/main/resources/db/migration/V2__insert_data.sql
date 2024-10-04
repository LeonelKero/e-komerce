-- Using the sequence for generating category IDs
insert into category (id, name, description) values (1, 'Electronics', 'Devices and gadgets');
insert into category (id, name, description) values (2, 'Clothing', 'Men and Women apparel');
insert into category (id, name, description) values (3, 'Home Appliances', 'Household gadgets and appliances');
insert into category (id, name, description) values (4, 'Books', 'Educational and fictional books');
insert into category (id, name, description) values (5, 'Toys', 'Toys for children of all ages');
insert into category (id, name, description) values (6, 'Sports', 'Sporting goods and accessories');
insert into category (id, name, description) values (7, 'Furniture', 'Indoor and outdoor furniture');
insert into category (id, name, description) values (8, 'Beauty', 'Beauty and personal care products');
insert into category (id, name, description) values (9, 'Automotive', 'Vehicle parts and accessories');
insert into category (id, name, description) values (10, 'Garden', 'Gardening tools and supplies');

-- Using the sequence for generating product IDs and referring to category IDs from the category table
insert into product (id, name, description, available_quantity, price, category_id) values (nextval('product_seq'), 'Macbook Pro 16 M3 Max', 'Portable computer for ', 15, 3999.99, 1);
insert into product (id, name, description, available_quantity, price, category_id) values (nextval('product_seq'), 'PS5 Pro', 'Gaming console next gen', 25, 500.0, 1);
insert into product (id, name, description, available_quantity, price, category_id) values (nextval('product_seq'), 'T-shirt', 'Cotton t-shirt', 100, 19.99, 2);
insert into product (id, name, description, available_quantity, price, category_id) values (nextval('product_seq'), 'stand Smith', 'Simple and beautiful shoes', 80, 750.50, 2);
insert into product (id, name, description, available_quantity, price, category_id) values (nextval('product_seq'), 'Refrigerator', 'Double-door fridge', 10, 499.99, 3);
insert into product (id, name, description, available_quantity, price, category_id) values (nextval('product_seq'), 'Novel', 'Thriller fiction book', 50, 14.99, 4);
insert into product (id, name, description, available_quantity, price, category_id) values (nextval('product_seq'), 'Action Figure', 'Superhero toy', 75, 24.99, 5);
insert into product (id, name, description, available_quantity, price, category_id) values (nextval('product_seq'), 'Basketball', 'Official size basketball', 40, 29.99, 6);
insert into product (id, name, description, available_quantity, price, category_id) values (nextval('product_seq'), 'Sofa', 'Three-seater leather sofa', 5, 799.99, 7);
insert into product (id, name, description, available_quantity, price, category_id) values (nextval('product_seq'), 'Coach', 'Two-seater soft coach', 5, 799.99, 10);
insert into product (id, name, description, available_quantity, price, category_id) values (nextval('product_seq'), 'Shampoo', 'Hair care shampoo', 200, 9.99, 8);
insert into product (id, name, description, available_quantity, price, category_id) values (nextval('product_seq'), 'Car Tires', 'All-season tires', 20, 129.99, 9);
insert into product (id, name, description, available_quantity, price, category_id) values (nextval('product_seq'), 'Garden Hose', 'Flexible garden hose', 60, 24.99, 10);