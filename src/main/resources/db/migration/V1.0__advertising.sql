create table advertising_data_source
(
    id bigint auto_increment not null primary key,
    name varchar not null unique,
    created_at timestamp default now()
);

create table advertising_campaign
(
    id bigint auto_increment not null primary key,
    name varchar not null unique,
    created_at timestamp default now()
);

create table advertising_sample
(
    id bigint auto_increment not null primary key,
    data_source_id bigint not null references advertising_data_source(id),
    campaign_id bigint not null references advertising_campaign(id),
    clicks int not null default 0,
    impressions int not null default 0,
    created_at timestamp default now()
);


