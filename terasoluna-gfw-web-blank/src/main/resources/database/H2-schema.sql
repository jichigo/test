create table article_class (
    article_class_id varchar(128) not null,
    name varchar(64) not null,
    constraint pk_article_class primary key (article_class_id)
);

create table article (
--    article_id IDENTITY not null,
    article_id bigint not null,
    title varchar(256) not null,
    overview varchar(1024) not null,
    content clob not null,
    published_date date not null,
    published_by varchar(128) not null,
    article_class_id varchar(128)not null,
    version bigint not null,
    constraint pk_article primary key (article_id),
    constraint fk_article_1 foreign key (article_class_id) references article_class(article_class_id)
);

create table id_mng (
    id_class varchar(10) not null,
    id_value bigint not null,
    constraint pk_id_mng primary key (id_class)
);

create sequence seq_article
    start with 1000
    increment by 1;

create sequence seq_article_class
    start with 1000
    increment by 1;

CREATE ALIAS QUERY AS \$\$  
ResultSet query(Connection conn, String sql) throws SQLException {  
    return conn.createStatement().executeQuery(sql);  
} \$\$;