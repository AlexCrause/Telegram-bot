create table if not exists subscribers (
    user_id uuid primary key,
    user_id_telegram bigint not null unique,
    price bigint
);