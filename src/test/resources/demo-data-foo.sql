delete from foo;
delete from foo_ext;

insert into foo (id, name) values (1, 'Foo');
insert into foo (id, name) values (2, 'Bar');

insert into foo_ext (id, ext_value, foo_id) values (1, 'ExtFoo', 1);
insert into foo_ext (id, ext_value, foo_id) values (2, 'ExtBar', 2);
