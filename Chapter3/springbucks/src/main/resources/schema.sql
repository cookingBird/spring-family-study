drop table if exists t_order_coffee;
drop table if exists t_order;
drop table if exists t_coffee;

create table t_coffee (
                          id bigint auto_increment,
                          create_time timestamp,
                          update_time timestamp,
                          name varchar(255),
                          price bigint,
                          primary key (id)
);

create table t_order (
                         id bigint auto_increment,
                         create_time timestamp,
                         update_time timestamp,
                         customer varchar(255),
                         state varchar(255) not null,
                         primary key (id)
);

create table t_order_coffee (
                                coffee_order_id bigint not null,
                                items_id bigint not null,
                                foreign key (coffee_order_id) references t_order(id),
                                foreign key (items_id) references t_coffee(id),
                                primary key (coffee_order_id, items_id)
);

insert into t_coffee (name, price, create_time, update_time) values ('espresso', 2000, now(), now());
insert into t_coffee (name, price, create_time, update_time) values ('latte', 2500, now(), now());
insert into t_coffee (name, price, create_time, update_time) values ('cappuccino', 2500, now(), now());
insert into t_coffee (name, price, create_time, update_time) values ('mocha', 3000, now(), now());
insert into t_coffee (name, price, create_time, update_time) values ('macchiato', 3000, now(), now());
