ALTER TABLE IF EXISTS purchase
  DROP CONSTRAINT IF EXISTS fk_product_id;
ALTER TABLE IF EXISTS purchase
  DROP CONSTRAINT IF EXISTS fk_customer_id;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS purchase;

create table customer (
	id INT PRIMARY KEY,
	first_name VARCHAR(64),
	last_name VARCHAR(64)
);
create table product (
	id INT PRIMARY KEY,
	name VARCHAR(64),
	price INT
);

create table purchase (
	id INT PRIMARY KEY,
	customer_id INT,
	product_id INT,
	date DATE
);

ALTER TABLE purchase ADD CONSTRAINT
    fk_product_id FOREIGN KEY (product_id) REFERENCES product(id);
ALTER TABLE purchase ADD CONSTRAINT
    fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer(id);

insert into customer (id, first_name, last_name) values (1, 'Sony', 'Mitchel');
insert into customer (id, first_name, last_name) values (2, 'Julia', 'Spike');
insert into customer (id, first_name, last_name) values (3, 'Tony', 'Montana');
insert into customer (id, first_name, last_name) values (4, 'John', 'Wick');
insert into customer (id, first_name, last_name) values (5, 'Jack', 'Old');
insert into customer (id, first_name, last_name) values (6, 'Alice', 'Mitchel');
insert into customer (id, first_name, last_name) values (7, 'Curt', 'Russel');
insert into customer (id, first_name, last_name) values (8, 'Anna', 'Argo');
insert into customer (id, first_name, last_name) values (9, 'Bob', 'Mitchel');
insert into customer (id, first_name, last_name) values (10, 'Polly', 'Wick');
insert into customer (id, first_name, last_name) values (11, 'Mickey', 'Cooper');


insert into product (id, name, price) values (1, 'product 1', 90);
insert into product (id, name, price) values (2, 'product 2', 400);
insert into product (id, name, price) values (3, 'product 3', 800);
insert into product (id, name, price) values (4, 'product 4', 950);
insert into product (id, name, price) values (5, 'product 5', 190);
insert into product (id, name, price) values (6, 'product 6', 140);
insert into product (id, name, price) values (7, 'product 7', 460);
insert into product (id, name, price) values (8, 'product 8', 240);
insert into product (id, name, price) values (9, 'product 9', 300);
insert into product (id, name, price) values (10, 'product 10', 560);

insert into purchase (id, customer_id, product_id, date) values (1, 5, 2, '2022-05-24');
insert into purchase (id, customer_id, product_id, date) values (2, 8, 9, '2021-10-11');
insert into purchase (id, customer_id, product_id, date) values (3, 9, 1, '2021-12-01');
insert into purchase (id, customer_id, product_id, date) values (4, 3, 3, '2022-02-26');
insert into purchase (id, customer_id, product_id, date) values (5, 10, 10, '2022-01-04');
insert into purchase (id, customer_id, product_id, date) values (6, 6, 8, '2022-04-03');
insert into purchase (id, customer_id, product_id, date) values (7, 10, 8, '2022-03-17');
insert into purchase (id, customer_id, product_id, date) values (8, 5, 6, '2021-07-22');
insert into purchase (id, customer_id, product_id, date) values (9, 7, 10, '2022-03-13');
insert into purchase (id, customer_id, product_id, date) values (10, 9, 8, '2021-09-28');
insert into purchase (id, customer_id, product_id, date) values (11, 4, 9, '2022-07-10');
insert into purchase (id, customer_id, product_id, date) values (12, 5, 4, '2021-08-19');
insert into purchase (id, customer_id, product_id, date) values (13, 7, 9, '2022-06-11');
insert into purchase (id, customer_id, product_id, date) values (14, 10, 10, '2022-08-07');
insert into purchase (id, customer_id, product_id, date) values (15, 3, 8, '2021-09-14');
insert into purchase (id, customer_id, product_id, date) values (16, 9, 6, '2021-10-02');
insert into purchase (id, customer_id, product_id, date) values (17, 3, 4, '2022-03-24');
insert into purchase (id, customer_id, product_id, date) values (18, 2, 9, '2021-10-02');
insert into purchase (id, customer_id, product_id, date) values (19, 7, 9, '2022-05-10');
insert into purchase (id, customer_id, product_id, date) values (20, 6, 8, '2022-05-03');
insert into purchase (id, customer_id, product_id, date) values (21, 2, 8, '2021-10-09');
insert into purchase (id, customer_id, product_id, date) values (22, 1, 8, '2021-11-04');
insert into purchase (id, customer_id, product_id, date) values (23, 1, 4, '2022-07-19');
insert into purchase (id, customer_id, product_id, date) values (24, 3, 2, '2022-04-02');
insert into purchase (id, customer_id, product_id, date) values (25, 5, 4, '2022-08-10');
insert into purchase (id, customer_id, product_id, date) values (26, 3, 3, '2022-04-13');
insert into purchase (id, customer_id, product_id, date) values (27, 8, 10, '2022-07-02');
insert into purchase (id, customer_id, product_id, date) values (28, 1, 1, '2021-12-13');
insert into purchase (id, customer_id, product_id, date) values (29, 3, 1, '2021-11-27');
insert into purchase (id, customer_id, product_id, date) values (30, 8, 6, '2022-01-26');
insert into purchase (id, customer_id, product_id, date) values (31, 2, 9, '2021-10-02');
insert into purchase (id, customer_id, product_id, date) values (32, 3, 8, '2022-02-08');
insert into purchase (id, customer_id, product_id, date) values (33, 7, 8, '2022-07-02');
insert into purchase (id, customer_id, product_id, date) values (34, 8, 1, '2021-08-25');
insert into purchase (id, customer_id, product_id, date) values (35, 3, 10, '2022-04-21');
insert into purchase (id, customer_id, product_id, date) values (36, 6, 6, '2022-03-11');
insert into purchase (id, customer_id, product_id, date) values (37, 1, 2, '2021-07-24');
insert into purchase (id, customer_id, product_id, date) values (38, 1, 4, '2022-01-27');
insert into purchase (id, customer_id, product_id, date) values (39, 8, 3, '2021-11-12');
insert into purchase (id, customer_id, product_id, date) values (40, 7, 7, '2021-12-15');
insert into purchase (id, customer_id, product_id, date) values (41, 9, 1, '2022-05-23');
insert into purchase (id, customer_id, product_id, date) values (42, 7, 5, '2021-11-20');
insert into purchase (id, customer_id, product_id, date) values (43, 1, 9, '2021-10-16');
insert into purchase (id, customer_id, product_id, date) values (44, 8, 6, '2021-12-22');
insert into purchase (id, customer_id, product_id, date) values (45, 6, 8, '2022-07-25');
insert into purchase (id, customer_id, product_id, date) values (46, 6, 10, '2021-08-17');
insert into purchase (id, customer_id, product_id, date) values (47, 2, 10, '2021-10-16');
insert into purchase (id, customer_id, product_id, date) values (48, 1, 8, '2022-06-05');
insert into purchase (id, customer_id, product_id, date) values (49, 6, 9, '2022-07-09');
insert into purchase (id, customer_id, product_id, date) values (50, 10, 8, '2022-03-16');
insert into purchase (id, customer_id, product_id, date) values (51, 3, 1, '2021-12-27');
insert into purchase (id, customer_id, product_id, date) values (52, 8, 7, '2022-03-01');
insert into purchase (id, customer_id, product_id, date) values (53, 3, 2, '2022-03-24');
insert into purchase (id, customer_id, product_id, date) values (54, 3, 10, '2021-11-17');
insert into purchase (id, customer_id, product_id, date) values (55, 8, 10, '2021-09-23');
insert into purchase (id, customer_id, product_id, date) values (56, 3, 1, '2022-03-01');
insert into purchase (id, customer_id, product_id, date) values (57, 1, 7, '2021-09-27');
insert into purchase (id, customer_id, product_id, date) values (58, 6, 4, '2022-02-15');
insert into purchase (id, customer_id, product_id, date) values (59, 9, 1, '2021-12-25');
insert into purchase (id, customer_id, product_id, date) values (60, 4, 4, '2022-01-17');
insert into purchase (id, customer_id, product_id, date) values (61, 5, 4, '2021-10-12');
insert into purchase (id, customer_id, product_id, date) values (62, 3, 8, '2022-01-18');
insert into purchase (id, customer_id, product_id, date) values (63, 4, 6, '2022-05-23');
insert into purchase (id, customer_id, product_id, date) values (64, 3, 1, '2021-10-03');
insert into purchase (id, customer_id, product_id, date) values (65, 4, 4, '2021-10-19');
insert into purchase (id, customer_id, product_id, date) values (66, 6, 10, '2022-03-08');
insert into purchase (id, customer_id, product_id, date) values (67, 6, 10, '2021-10-25');
insert into purchase (id, customer_id, product_id, date) values (68, 1, 4, '2022-05-01');
insert into purchase (id, customer_id, product_id, date) values (69, 1, 4, '2022-07-27');
insert into purchase (id, customer_id, product_id, date) values (70, 6, 4, '2021-12-11');
insert into purchase (id, customer_id, product_id, date) values (71, 8, 5, '2022-05-21');
insert into purchase (id, customer_id, product_id, date) values (72, 9, 5, '2022-07-29');
insert into purchase (id, customer_id, product_id, date) values (73, 9, 5, '2022-02-10');
insert into purchase (id, customer_id, product_id, date) values (74, 1, 3, '2021-11-01');
insert into purchase (id, customer_id, product_id, date) values (75, 8, 4, '2021-08-27');
insert into purchase (id, customer_id, product_id, date) values (76, 10, 3, '2022-04-19');
insert into purchase (id, customer_id, product_id, date) values (77, 1, 9, '2022-04-04');
insert into purchase (id, customer_id, product_id, date) values (78, 3, 8, '2021-08-31');
insert into purchase (id, customer_id, product_id, date) values (79, 2, 3, '2021-09-28');
insert into purchase (id, customer_id, product_id, date) values (80, 2, 10, '2022-03-22');
insert into purchase (id, customer_id, product_id, date) values (81, 7, 7, '2021-10-18');
insert into purchase (id, customer_id, product_id, date) values (82, 2, 4, '2022-02-16');
insert into purchase (id, customer_id, product_id, date) values (83, 9, 2, '2021-12-14');
insert into purchase (id, customer_id, product_id, date) values (84, 3, 3, '2021-12-03');
insert into purchase (id, customer_id, product_id, date) values (85, 2, 5, '2021-12-02');
insert into purchase (id, customer_id, product_id, date) values (86, 5, 9, '2021-11-04');
insert into purchase (id, customer_id, product_id, date) values (87, 6, 7, '2021-12-12');
insert into purchase (id, customer_id, product_id, date) values (88, 10, 2, '2022-04-12');
insert into purchase (id, customer_id, product_id, date) values (89, 6, 5, '2021-11-15');
insert into purchase (id, customer_id, product_id, date) values (90, 6, 5, '2022-06-09');
insert into purchase (id, customer_id, product_id, date) values (91, 3, 5, '2022-07-18');
insert into purchase (id, customer_id, product_id, date) values (92, 10, 7, '2022-04-13');
insert into purchase (id, customer_id, product_id, date) values (93, 9, 8, '2022-01-01');
insert into purchase (id, customer_id, product_id, date) values (94, 8, 2, '2022-07-10');
insert into purchase (id, customer_id, product_id, date) values (95, 7, 10, '2021-11-12');
insert into purchase (id, customer_id, product_id, date) values (96, 10, 8, '2021-10-06');
insert into purchase (id, customer_id, product_id, date) values (97, 1, 1, '2022-05-24');
insert into purchase (id, customer_id, product_id, date) values (98, 6, 5, '2021-10-22');
insert into purchase (id, customer_id, product_id, date) values (99, 5, 5, '2021-07-17');
insert into purchase (id, customer_id, product_id, date) values (100, 1, 10, '2022-05-16');
insert into purchase (id, customer_id, product_id, date) values (101, 9, 5, '2022-04-24');
insert into purchase (id, customer_id, product_id, date) values (102, 7, 4, '2022-06-03');
insert into purchase (id, customer_id, product_id, date) values (103, 2, 3, '2022-04-12');
insert into purchase (id, customer_id, product_id, date) values (104, 10, 7, '2022-05-02');
insert into purchase (id, customer_id, product_id, date) values (105, 4, 1, '2022-02-24');
insert into purchase (id, customer_id, product_id, date) values (106, 8, 7, '2022-08-04');
insert into purchase (id, customer_id, product_id, date) values (107, 4, 2, '2022-06-23');
insert into purchase (id, customer_id, product_id, date) values (108, 2, 1, '2022-05-24');
insert into purchase (id, customer_id, product_id, date) values (109, 3, 3, '2022-07-06');
insert into purchase (id, customer_id, product_id, date) values (110, 4, 6, '2022-04-04');
insert into purchase (id, customer_id, product_id, date) values (111, 8, 2, '2022-07-08');
insert into purchase (id, customer_id, product_id, date) values (112, 9, 4, '2021-12-20');
insert into purchase (id, customer_id, product_id, date) values (113, 1, 2, '2021-09-20');
insert into purchase (id, customer_id, product_id, date) values (114, 5, 5, '2022-08-01');
insert into purchase (id, customer_id, product_id, date) values (115, 6, 4, '2021-12-05');
insert into purchase (id, customer_id, product_id, date) values (116, 3, 4, '2022-07-06');
insert into purchase (id, customer_id, product_id, date) values (117, 2, 6, '2022-01-14');
insert into purchase (id, customer_id, product_id, date) values (118, 4, 7, '2022-02-19');
insert into purchase (id, customer_id, product_id, date) values (119, 8, 2, '2022-02-27');
insert into purchase (id, customer_id, product_id, date) values (120, 10, 6, '2021-08-08');
insert into purchase (id, customer_id, product_id, date) values (121, 8, 9, '2022-01-26');
insert into purchase (id, customer_id, product_id, date) values (122, 3, 4, '2022-04-30');
insert into purchase (id, customer_id, product_id, date) values (123, 8, 9, '2022-04-24');
insert into purchase (id, customer_id, product_id, date) values (124, 7, 4, '2021-09-07');
insert into purchase (id, customer_id, product_id, date) values (125, 8, 7, '2022-06-09');
insert into purchase (id, customer_id, product_id, date) values (126, 4, 8, '2022-02-17');
insert into purchase (id, customer_id, product_id, date) values (127, 9, 9, '2021-12-18');
insert into purchase (id, customer_id, product_id, date) values (128, 4, 1, '2022-01-25');
insert into purchase (id, customer_id, product_id, date) values (129, 4, 2, '2022-03-29');
insert into purchase (id, customer_id, product_id, date) values (130, 6, 3, '2022-07-28');
insert into purchase (id, customer_id, product_id, date) values (131, 7, 4, '2022-03-19');
insert into purchase (id, customer_id, product_id, date) values (132, 1, 2, '2021-10-25');
insert into purchase (id, customer_id, product_id, date) values (133, 6, 1, '2022-03-28');
insert into purchase (id, customer_id, product_id, date) values (134, 8, 6, '2021-10-09');
insert into purchase (id, customer_id, product_id, date) values (135, 3, 1, '2021-12-09');
insert into purchase (id, customer_id, product_id, date) values (136, 1, 9, '2022-03-08');
insert into purchase (id, customer_id, product_id, date) values (137, 4, 3, '2022-03-30');
insert into purchase (id, customer_id, product_id, date) values (138, 6, 9, '2022-02-24');
insert into purchase (id, customer_id, product_id, date) values (139, 7, 7, '2021-10-17');
insert into purchase (id, customer_id, product_id, date) values (140, 7, 3, '2021-10-15');
insert into purchase (id, customer_id, product_id, date) values (141, 8, 9, '2022-07-13');
insert into purchase (id, customer_id, product_id, date) values (142, 4, 8, '2022-03-29');
insert into purchase (id, customer_id, product_id, date) values (143, 9, 5, '2022-01-11');
insert into purchase (id, customer_id, product_id, date) values (144, 5, 9, '2022-05-29');
insert into purchase (id, customer_id, product_id, date) values (145, 1, 5, '2022-06-08');
insert into purchase (id, customer_id, product_id, date) values (146, 9, 2, '2022-07-31');
insert into purchase (id, customer_id, product_id, date) values (147, 4, 5, '2021-08-25');
insert into purchase (id, customer_id, product_id, date) values (148, 2, 6, '2022-04-07');
insert into purchase (id, customer_id, product_id, date) values (149, 8, 9, '2021-10-23');
insert into purchase (id, customer_id, product_id, date) values (150, 7, 1, '2022-06-22');
insert into purchase (id, customer_id, product_id, date) values (151, 10, 1, '2021-12-21');
insert into purchase (id, customer_id, product_id, date) values (152, 1, 8, '2022-04-03');
insert into purchase (id, customer_id, product_id, date) values (153, 7, 9, '2021-07-13');
insert into purchase (id, customer_id, product_id, date) values (154, 7, 7, '2022-07-29');
insert into purchase (id, customer_id, product_id, date) values (155, 2, 5, '2021-12-09');
insert into purchase (id, customer_id, product_id, date) values (156, 3, 6, '2021-10-11');
insert into purchase (id, customer_id, product_id, date) values (157, 8, 8, '2021-10-09');
insert into purchase (id, customer_id, product_id, date) values (158, 5, 4, '2022-04-19');
insert into purchase (id, customer_id, product_id, date) values (159, 9, 3, '2022-05-01');
insert into purchase (id, customer_id, product_id, date) values (160, 8, 2, '2021-07-18');
insert into purchase (id, customer_id, product_id, date) values (161, 8, 5, '2022-02-18');
insert into purchase (id, customer_id, product_id, date) values (162, 1, 4, '2022-01-27');
insert into purchase (id, customer_id, product_id, date) values (163, 9, 1, '2022-02-26');
insert into purchase (id, customer_id, product_id, date) values (164, 4, 2, '2022-01-02');
insert into purchase (id, customer_id, product_id, date) values (165, 4, 6, '2022-06-28');
insert into purchase (id, customer_id, product_id, date) values (166, 8, 6, '2021-12-01');
insert into purchase (id, customer_id, product_id, date) values (167, 4, 4, '2022-01-23');
insert into purchase (id, customer_id, product_id, date) values (168, 3, 9, '2021-09-11');
insert into purchase (id, customer_id, product_id, date) values (169, 6, 3, '2021-09-04');
insert into purchase (id, customer_id, product_id, date) values (170, 9, 4, '2022-06-01');
insert into purchase (id, customer_id, product_id, date) values (171, 9, 10, '2021-09-18');
insert into purchase (id, customer_id, product_id, date) values (172, 4, 2, '2022-03-23');
insert into purchase (id, customer_id, product_id, date) values (173, 6, 10, '2022-03-01');
insert into purchase (id, customer_id, product_id, date) values (174, 7, 9, '2021-08-13');
insert into purchase (id, customer_id, product_id, date) values (175, 6, 1, '2021-12-06');
insert into purchase (id, customer_id, product_id, date) values (176, 2, 9, '2022-05-15');
insert into purchase (id, customer_id, product_id, date) values (177, 8, 2, '2021-09-16');
insert into purchase (id, customer_id, product_id, date) values (178, 4, 3, '2021-11-18');
insert into purchase (id, customer_id, product_id, date) values (179, 6, 1, '2022-07-27');
insert into purchase (id, customer_id, product_id, date) values (180, 5, 5, '2022-01-16');
insert into purchase (id, customer_id, product_id, date) values (181, 1, 5, '2022-02-25');
insert into purchase (id, customer_id, product_id, date) values (182, 3, 10, '2021-10-12');
insert into purchase (id, customer_id, product_id, date) values (183, 4, 5, '2022-07-09');
insert into purchase (id, customer_id, product_id, date) values (184, 5, 2, '2021-09-12');
insert into purchase (id, customer_id, product_id, date) values (185, 5, 5, '2021-12-18');
insert into purchase (id, customer_id, product_id, date) values (186, 3, 5, '2022-04-23');
insert into purchase (id, customer_id, product_id, date) values (187, 10, 1, '2021-10-24');
insert into purchase (id, customer_id, product_id, date) values (188, 2, 9, '2022-06-22');
insert into purchase (id, customer_id, product_id, date) values (189, 6, 8, '2022-08-07');
insert into purchase (id, customer_id, product_id, date) values (190, 8, 9, '2022-02-05');
insert into purchase (id, customer_id, product_id, date) values (191, 7, 5, '2022-03-05');
insert into purchase (id, customer_id, product_id, date) values (192, 9, 4, '2022-02-10');
insert into purchase (id, customer_id, product_id, date) values (193, 2, 3, '2021-08-29');
insert into purchase (id, customer_id, product_id, date) values (194, 4, 2, '2021-07-27');
insert into purchase (id, customer_id, product_id, date) values (195, 7, 3, '2021-11-29');
insert into purchase (id, customer_id, product_id, date) values (196, 10, 5, '2021-10-31');
insert into purchase (id, customer_id, product_id, date) values (197, 5, 6, '2022-05-26');
insert into purchase (id, customer_id, product_id, date) values (198, 2, 5, '2021-09-18');
insert into purchase (id, customer_id, product_id, date) values (199, 7, 9, '2021-12-28');
insert into purchase (id, customer_id, product_id, date) values (200, 4, 5, '2021-08-13');
insert into purchase (id, customer_id, product_id, date) values (201, 7, 10, '2022-06-01');
insert into purchase (id, customer_id, product_id, date) values (202, 5, 1, '2021-12-28');
insert into purchase (id, customer_id, product_id, date) values (203, 7, 8, '2022-06-18');
insert into purchase (id, customer_id, product_id, date) values (204, 7, 7, '2022-01-09');
insert into purchase (id, customer_id, product_id, date) values (205, 2, 6, '2022-03-05');
insert into purchase (id, customer_id, product_id, date) values (206, 7, 5, '2021-08-22');
insert into purchase (id, customer_id, product_id, date) values (207, 5, 8, '2021-12-23');
insert into purchase (id, customer_id, product_id, date) values (208, 4, 4, '2022-04-04');
insert into purchase (id, customer_id, product_id, date) values (209, 4, 5, '2022-04-02');
insert into purchase (id, customer_id, product_id, date) values (210, 5, 9, '2022-01-05');
insert into purchase (id, customer_id, product_id, date) values (211, 3, 1, '2021-09-11');
insert into purchase (id, customer_id, product_id, date) values (212, 8, 1, '2022-06-25');
insert into purchase (id, customer_id, product_id, date) values (213, 2, 4, '2022-03-29');
insert into purchase (id, customer_id, product_id, date) values (214, 3, 4, '2022-03-15');
insert into purchase (id, customer_id, product_id, date) values (215, 4, 3, '2022-07-05');
insert into purchase (id, customer_id, product_id, date) values (216, 3, 2, '2022-01-14');
insert into purchase (id, customer_id, product_id, date) values (217, 5, 7, '2022-07-30');
insert into purchase (id, customer_id, product_id, date) values (218, 5, 1, '2022-06-07');
insert into purchase (id, customer_id, product_id, date) values (219, 2, 2, '2022-07-24');
insert into purchase (id, customer_id, product_id, date) values (220, 1, 1, '2021-09-04');
insert into purchase (id, customer_id, product_id, date) values (221, 5, 2, '2022-04-15');
insert into purchase (id, customer_id, product_id, date) values (222, 3, 3, '2022-06-16');
insert into purchase (id, customer_id, product_id, date) values (223, 9, 2, '2022-07-04');
insert into purchase (id, customer_id, product_id, date) values (224, 3, 2, '2022-06-18');
insert into purchase (id, customer_id, product_id, date) values (225, 3, 6, '2022-05-11');
insert into purchase (id, customer_id, product_id, date) values (226, 4, 8, '2022-06-02');
insert into purchase (id, customer_id, product_id, date) values (227, 9, 3, '2021-12-19');
insert into purchase (id, customer_id, product_id, date) values (228, 6, 2, '2022-04-19');
insert into purchase (id, customer_id, product_id, date) values (229, 4, 3, '2021-12-05');
insert into purchase (id, customer_id, product_id, date) values (230, 2, 10, '2021-09-12');
insert into purchase (id, customer_id, product_id, date) values (231, 4, 5, '2022-04-11');
insert into purchase (id, customer_id, product_id, date) values (232, 7, 6, '2021-10-08');
insert into purchase (id, customer_id, product_id, date) values (233, 10, 6, '2022-05-22');
insert into purchase (id, customer_id, product_id, date) values (234, 3, 6, '2021-12-15');
insert into purchase (id, customer_id, product_id, date) values (235, 9, 8, '2021-08-12');
insert into purchase (id, customer_id, product_id, date) values (236, 4, 9, '2021-11-05');
insert into purchase (id, customer_id, product_id, date) values (237, 8, 5, '2022-06-29');
insert into purchase (id, customer_id, product_id, date) values (238, 2, 7, '2022-02-12');
insert into purchase (id, customer_id, product_id, date) values (239, 4, 8, '2022-06-13');
insert into purchase (id, customer_id, product_id, date) values (240, 2, 10, '2021-07-20');
insert into purchase (id, customer_id, product_id, date) values (241, 5, 10, '2022-01-30');
insert into purchase (id, customer_id, product_id, date) values (242, 7, 6, '2022-07-04');
insert into purchase (id, customer_id, product_id, date) values (243, 9, 3, '2021-10-05');
insert into purchase (id, customer_id, product_id, date) values (244, 1, 3, '2022-02-25');
insert into purchase (id, customer_id, product_id, date) values (245, 2, 2, '2022-06-29');
insert into purchase (id, customer_id, product_id, date) values (246, 2, 9, '2021-12-29');
insert into purchase (id, customer_id, product_id, date) values (247, 2, 2, '2022-05-24');