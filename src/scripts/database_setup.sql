create table users (
    username varchar(50) not null primary key,
    password varchar(50) not null,
    enabled boolean not null
) engine = InnoDb;

create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    foreign key (username) references users (username),
    unique index authorities_idx_1 (username, authority)
) engine = InnoDb;

INSERT INTO `users` (`username`, `password`, `enabled`) VALUES ('admin', 'admin', true);
INSERT INTO `users` (`username`, `password`, `enabled`) VALUES ('user', 'user', true);

INSERT INTO `authorities` (`username`, `authority`) VALUES ('user', 'user');
INSERT INTO `authorities` (`username`, `authority`) VALUES ('admin', 'user');
INSERT INTO `authorities` (`username`, `authority`) VALUES ('admin', 'admin');