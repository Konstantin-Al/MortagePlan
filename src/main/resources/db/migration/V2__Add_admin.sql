insert into customer (customer_id, name, password, active)
    values   (1, 'admin', '$2a$10$8X.TpTeiFou6oTg8KD1Jueig8y4gKYZ.te/ONsqe8U1l722lCw9nO', true)
            ,(2, 'user1', '$2a$10$8X.TpTeiFou6oTg8KD1Jueig8y4gKYZ.te/ONsqe8U1l722lCw9nO', true)
            ;


insert into customer_role (customer_id, roles)
values   (1, 'USER')
        ,(1, 'ADMIN')
        ,(2, 'USER')
        ;
