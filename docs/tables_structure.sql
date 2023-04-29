create table tbl_collection
(
    id        int auto_increment
        primary key,
    user_id   int         not null,
    name      varchar(25) not null,
    parent_id int         null,
    constraint fk_tbl_collection_parent_id__id
        foreign key (parent_id) references tbl_collection (id),
    constraint fk_tbl_collection_user_id__id
        foreign key (user_id) references tbl_user (id)
);

create table tbl_highlight
(
    id      int auto_increment
        primary key,
    content varchar(255) not null,
    user_id int          not null,
    constraint fk_tbl_highlight_user_id__id
        foreign key (user_id) references tbl_user (id)
);

create table tbl_highlight_storage
(
    highlight_id  int not null,
    collection_id int not null,
    primary key (highlight_id, collection_id),
    constraint fk_tbl_highlight_storage_collection_id__id
        foreign key (collection_id) references tbl_collection (id),
    constraint fk_tbl_highlight_storage_highlight_id__id
        foreign key (highlight_id) references tbl_highlight (id)
);

create table tbl_user
(
    id int auto_increment
        primary key
);