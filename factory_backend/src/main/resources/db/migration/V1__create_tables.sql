begin;
create table users (
    id bigint primary key,
    password varchar(255)
);
insert into users
values (0, '$2a$12$LrWeueExd5diyQjkJQ.rIu6d1UnNfmxO57C/Vtb9xt3Opgg88UDby');
commit;