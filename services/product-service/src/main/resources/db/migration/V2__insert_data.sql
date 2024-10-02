-- Using the sequence for generating category IDs
INSERT INTO category (id, name, description) VALUES (nextval('category_seq'), 'Electronics', 'Devices and gadgets');
INSERT INTO category (id, name, description) VALUES (nextval('category_seq'), 'Clothing', 'Men and Women apparel');
INSERT INTO category (id, name, description) VALUES (nextval('category_seq'), 'Home Appliances', 'Household gadgets and appliances');
INSERT INTO category (id, name, description) VALUES (nextval('category_seq'), 'Books', 'Educational and fictional books');
INSERT INTO category (id, name, description) VALUES (nextval('category_seq'), 'Toys', 'Toys for children of all ages');
INSERT INTO category (id, name, description) VALUES (nextval('category_seq'), 'Sports', 'Sporting goods and accessories');
INSERT INTO category (id, name, description) VALUES (nextval('category_seq'), 'Furniture', 'Indoor and outdoor furniture');
INSERT INTO category (id, name, description) VALUES (nextval('category_seq'), 'Beauty', 'Beauty and personal care products');
INSERT INTO category (id, name, description) VALUES (nextval('category_seq'), 'Automotive', 'Vehicle parts and accessories');
INSERT INTO category (id, name, description) VALUES (nextval('category_seq'), 'Garden', 'Gardening tools and supplies');

-- Using the sequence for generating product IDs and referring to category IDs from the category table
INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Laptop', 'Portable computer', 25, 999.99, 1);
INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'T-shirt', 'Cotton t-shirt', 100, 19.99, 2);
INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Refrigerator', 'Double-door fridge', 10, 499.99, 3);
INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Novel', 'Thriller fiction book', 50, 14.99, 4);
INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Action Figure', 'Superhero toy', 75, 24.99, 5);
INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Basketball', 'Official size basketball', 40, 29.99, 6);
INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Sofa', 'Three-seater leather sofa', 5, 799.99, 7);
INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Shampoo', 'Hair care shampoo', 200, 9.99, 8);
INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Car Tires', 'All-season tires', 20, 129.99, 9);
INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Garden Hose', 'Flexible garden hose', 60, 24.99, 10);