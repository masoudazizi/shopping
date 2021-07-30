INSERT INTO authority (name) VALUES ('ADMIN');
INSERT INTO authority (name) VALUES ('USER');
insert into user (id,activated,email,first_name,last_name,login,password_hash) values(1,true,'azizimassoud77@gmail.com','masoud','azizi','masoud','$2a$10$Ega0FmxL1PG96goJ5DfHbur6vLU3b89QRf.cEriEVE.SlvFCNFn26');
insert into user_authority(user_id, authority_name) values (1,'ADMIN');
insert into user_authority(user_id, authority_name) values (1,'USER');

