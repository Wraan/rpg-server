drop table if exists oauth_client_details;
drop table if exists role_user;
drop table if exists role;
drop table if exists users;

drop table if exists oauth_client_token;
drop table if exists oauth_access_token;
drop table if exists oauth_refresh_token;
drop table if exists oauth_code;
drop table if exists oauth_approvals;

create table if not exists oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)
);

create table if not exists role (
  id serial PRIMARY KEY,
  name varchar(255) default null,
  unique(name)
);

create table if not exists users (
  id serial PRIMARY KEY,
  username varchar(100) not null,
  password varchar(1024) not null,
  email varchar(1024) not null,
  enabled boolean not null,
  account_non_expired boolean not null,
  credentials_non_expired boolean not null,
  account_non_locked boolean not null,
  unique(username)
);

create table if not exists role_user (
  role_id INTEGER default null references role (id),
  user_id INTEGER default null references users (id)
);

-- token store
create table if not exists oauth_client_token (
  token_id VARCHAR(256),
  token bytea,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

create table if not exists oauth_access_token (
  token_id VARCHAR(256),
  token bytea,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication bytea,
  refresh_token VARCHAR(256)
);

create table if not exists oauth_refresh_token (
  token_id VARCHAR(256),
  token bytea,
  authentication bytea
);

create table if not exists oauth_code (
  code VARCHAR(256),
  authentication bytea
);

create table if not exists oauth_approvals (
	userId VARCHAR(256),
	clientId VARCHAR(256),
	scope VARCHAR(256),
	status VARCHAR(10),
	expiresAt TIMESTAMP,
	lastModifiedAt TIMESTAMP
);