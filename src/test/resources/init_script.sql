CREATE TABLE application (
  id serial NOT NULL PRIMARY KEY,
  name text NOT NULL,
  details json NOT NULL
);

insert into application (id, name, details) values (1, 'Transcof', '{
  "image": "http://dummyimage.com/213x213.jpg/ff4444/ffffff",
  "version": "0.54"
}');
insert into application (id, name, details) values (2, 'Stringtough', '{
  "image": "http://dummyimage.com/155x246.png/ff4444/ffffff"
}');
