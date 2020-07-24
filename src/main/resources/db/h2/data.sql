insert into member(email,name,password,phone,role) values('jeon@test.com',
'유저1','$2a$10$vc.n7gsWnwwWt38bEh7wnuSSUQjNNGEOIWiEKP4XUGq2p1jD00pey',
'010-111-111','USER');

insert into member(email,name,password,phone,role) values('admin@gmail.com',
'관리자1','$2a$10$vc.n7gsWnwwWt38bEh7wnuSSUQjNNGEOIWiEKP4XUGq2p1jD00pey',
'010-111-111','ADMIN');

insert into posts(CREATED_DATE,MODIFIED_DATE,type, title, content, member_id) values('2020-07-16 20:01:44.633','2020-07-16 20:01:44.633','SECURITY','필터란?','csrf토큰에 대해서','1');
insert into posts(CREATED_DATE,MODIFIED_DATE,type, title, content, member_id) values('2020-07-16 20:01:44.633','2020-07-16 20:01:44.633','JPA','QuerySql','Query 에 대해서 알려주세요','1');
insert into posts(CREATED_DATE,MODIFIED_DATE,type, title, content, member_id) values('2020-07-16 20:01:44.633','2020-07-16 20:01:44.633','SECURITY','XML?','오라클DB매핑하는법 알려주세요!','1');
insert into posts(CREATED_DATE,MODIFIED_DATE,type, title, content, member_id) values('2020-07-16 20:01:44.633','2020-07-16 20:01:44.633','JPA','cache','MYSQLDB매핑하는법 알려주세요!','1');
insert into posts(CREATED_DATE,MODIFIED_DATE,type, title, content, member_id) values('2020-07-16 20:01:44.633','2020-07-16 20:01:44.633','SPRINGBOOT','autowired','오라클DB매핑하는법 알려주세요!','1');
insert into posts(CREATED_DATE,MODIFIED_DATE,type, title, content, member_id) values('2020-07-16 20:01:44.633','2020-07-16 20:01:44.633','SPRING','filter 적용법','필터 적용하는법 알려주세요!','1');
insert into posts(CREATED_DATE,MODIFIED_DATE,type, title, content, member_id) values('2020-07-16 20:01:44.633','2020-07-16 20:01:44.633','SPRING','카카오api수정건','지도api 알려주세요!','1');