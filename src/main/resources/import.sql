INSERT INTO shipping_method(name) VALUES ('GLS');
INSERT INTO shipping_method(name) VALUES ('POST');

INSERT INTO product_packaging(id) VALUES (1);
INSERT INTO product_packaging(id) VALUES (2);

INSERT INTO product(name, shipping_method, product_packaging_id) VALUES ('Basen', 1, 1);
INSERT INTO product(name, shipping_method, product_packaging_id) VALUES ('Plac zabaw', 2, 1);

INSERT INTO bag(bag_type, quantity, bag_product_packaging) VALUES ('small bag', 2, 1);
INSERT INTO bag(bag_type, quantity, bag_product_packaging) VALUES ('small bag', 2, 1);
INSERT INTO box(box_type, quantity, box_product_packaging) VALUES ('large box', 2, 1);
INSERT INTO box(box_type, quantity, box_product_packaging) VALUES ('medium box', 1, 1);
INSERT INTO box(box_type, quantity, box_product_packaging) VALUES ('small box', 1, 1);

INSERT INTO brand(quantity, brand_product_packaging) VALUES (7, 1);