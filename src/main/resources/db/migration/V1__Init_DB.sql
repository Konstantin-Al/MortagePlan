create table IF NOT EXISTS customer (
                        customer_id bigint AUTO_INCREMENT not null,
                        active bit not null,
                        name varchar(255),
                        password varchar(255),

                        primary key (customer_id)
);

create table IF NOT EXISTS customer_role (
                        customer_id bigint not null,
                        roles varchar(255)
);

create table IF NOT EXISTS loan_cal (
                        id integer not null,
                        CD datetime,
                        customer_id bigint,
                        total_loan  varchar(255),
                        interest varchar(255),
                        years smallint,


                        primary key (id)
);


alter table customer_role
    add constraint customer_role_customer_fk
        foreign key (customer_id) references customer (customer_id);

alter table loan_cal
    add constraint loan_cal_customer_fk
        foreign key (customer_id) references customer (customer_id);
