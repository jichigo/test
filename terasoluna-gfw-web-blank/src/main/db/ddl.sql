DROP TABLE t_order_pay_card;
DROP TABLE t_order_pay;
DROP TABLE t_order_item;
DROP TABLE t_order_coupon;
DROP TABLE t_order;
DROP TABLE m_item_category;
DROP TABLE m_category;
DROP TABLE m_item;
DROP TABLE m_coupon;
DROP TABLE c_order_status;
DROP SEQUENCE s_order_id;

CREATE SEQUENCE s_order_id;

CREATE TABLE c_order_status (
    code varchar(10) PRIMARY KEY,
    name varchar(256) NOT NULL
);

CREATE TABLE m_category (
    code varchar(10) PRIMARY KEY,
    name varchar(256) NOT NULL
);

CREATE TABLE m_item (
    code varchar(10) PRIMARY KEY,
    name varchar(256) NOT NULL,
    price INT4 NOT NULL
);

CREATE TABLE m_stock (
    item_code varchar(10) PRIMARY KEY,
    quantity INT4 NOT NULL,
    version INT8 NOT NULL
);

CREATE TABLE m_item_category (
    item_code varchar(10),
    category_code varchar(10),
    CONSTRAINT m_item_category_pk PRIMARY KEY (item_code,category_code),
    CONSTRAINT m_item_category_fk1 FOREIGN KEY (item_code) REFERENCES m_item (code),
    CONSTRAINT m_item_category_fk2 FOREIGN KEY (category_code) REFERENCES m_category (code)
);

CREATE TABLE m_coupon (
    code varchar(10) PRIMARY KEY,
    name varchar(256) NOT NULL,
    price INT4 NOT NULL
);

CREATE TABLE t_order(
    id INT4 PRIMARY KEY,
    status_code varchar(10) NOT NULL,
    created_by varchar(50) NOT NULL,
    created_date timestamp NOT NULL,
    last_modified_by varchar(50) NOT NULL,
    last_modified_date timestamp NOT NULL,
    CONSTRAINT t_order_fk1 FOREIGN KEY (status_code) REFERENCES c_order_status (code)
);


CREATE TABLE t_order_item (
    order_id INT4,
    item_code varchar(10),
    quantity INT4 NOT NULL,
    CONSTRAINT t_order_item_pk PRIMARY KEY (order_id,item_code),
    CONSTRAINT t_order_item_fk1 FOREIGN KEY (order_id) REFERENCES t_order (id),
    CONSTRAINT t_order_item_fk2 FOREIGN KEY (item_code) REFERENCES m_item (code)
);

CREATE TABLE t_order_coupon (
    order_id INT4,
    coupon_code varchar(10),
    quantity INT4 NOT NULL,
    CONSTRAINT t_order_coupon_pk PRIMARY KEY (order_id,coupon_code),
    CONSTRAINT t_order_coupon_fk1 FOREIGN KEY (order_id) REFERENCES t_order (id),
    CONSTRAINT t_order_coupon_fk2 FOREIGN KEY (coupon_code) REFERENCES m_coupon (code)
);


CREATE TABLE t_order_pay (
    order_id INT4 PRIMARY KEY,
    way_to_pay varchar(32) NOT NULL,
    CONSTRAINT t_order_pay_fk1 FOREIGN KEY (order_id) REFERENCES t_order (id)
);

CREATE TABLE t_order_pay_card (
    order_id INT4 PRIMARY KEY,
    card_number varchar(32) NOT NULL,
    security_code varchar(10),
    CONSTRAINT t_order_pay_card_fk1 FOREIGN KEY (order_id) REFERENCES t_order_pay (order_id)
);


