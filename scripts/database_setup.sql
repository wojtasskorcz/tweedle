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

create table UserConnection (userId varchar(255) not null,
	providerId varchar(255) not null,
	providerUserId varchar(255),
	rank int not null,
	displayName varchar(255),
	profileUrl varchar(512),
	imageUrl varchar(512),
	accessToken varchar(255) not null,					
	secret varchar(255),
	refreshToken varchar(255),
	expireTime bigint,
	primary key (userId, providerId, providerUserId));
create unique index UserConnectionRank on UserConnection(userId, providerId, rank);