create table APP.Buch (id integer not null, isbn_number integer, titel varchar(100), Buch_id integer not null, primary key (id));
create table APP.Person (DTYPE varchar(31) not null, id integer not null, name varchar(255), primary key (id));
alter table APP.Buch add constraint FK1FC418110AB4D4 foreign key (Buch_id) references APP.Person;
