INSERT INTO c_order_status VALUES('accepted', 'Order accepting');
INSERT INTO c_order_status VALUES('checking', 'Stock Checking');
INSERT INTO c_order_status VALUES('hoge', 'testing...');

INSERT INTO m_category VALUES('CTG0000001','Drink');
INSERT INTO m_category VALUES('CTG0000003','Hot Selling');
INSERT INTO m_category VALUES('test','a44e6b8a-379c-4965-8d34-977ed1d2729c');
INSERT INTO m_category VALUES('CTG0000002','hoge1376469006406');

INSERT INTO m_coupon VALUES('CPN0000001', 'Join coupon', 3000);
INSERT INTO m_coupon VALUES('CPN0000002', 'PC Coupon', 30000);

INSERT INTO m_item VALUES('ITM0000001', 'Water', 100);
INSERT INTO m_item VALUES('ITM0000002', 'Macbook Air', 980000);
INSERT INTO m_item VALUES('ITM0000003', 'Windows RT', 48900);
INSERT INTO m_item VALUES('hoge', 'hogename', 100);


INSERT INTO m_item_category VALUES('ITM0000001','CTG0000001');
INSERT INTO m_item_category VALUES('ITM0000002','CTG0000002');
INSERT INTO m_item_category VALUES('ITM0000002','CTG0000003');
INSERT INTO m_item_category VALUES('ITM0000003','CTG0000002');
INSERT INTO m_item_category VALUES('ITM0000003','CTG0000003');
