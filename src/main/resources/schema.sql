create table users (
  id IDENTITY,
  name VARCHAR NOT NULL
);
create table matches(
  id IDENTITY,
  user1 INT,
  user2 INT,
  user1Hand VARCHAR NOT NULL,
  user2Hand VARCHAR NOT NULL,
  isActive BOOLEAN NOT NULL
);
create table matchinfo(
  id IDENTITY,
  user1 INT,
  user2 INT,
  user1Hand VARCHAR NOT NULL,
  isActive BOOLEAN NOT NULL
)
