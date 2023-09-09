create table groups(
    id varchar(255) not null primary key,
    number varchar(255) not null constraint number_unique unique
);

create table users (
    id bigint primary key,
    creation_date date,
    is_subscribed boolean,
    group_number varchar(255) constraint user_group references groups (number)
);

alter table groups owner to hitsbot;
alter table users owner to hitsbot;