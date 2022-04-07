create database bank
use bank
create table savingaccount
(accountid varchar(30) primary key ,accountname varchar(40) not null,
amount int,
createdate date
)
drop table savingaccount
insert into savingaccount values (1,'ramesh',0,'2022-.3-16')
insert into savingaccount(accountid,accountname) values(3,'sush')
select * from savingaccount order by accountid desc
select * from savingaccount order by accountid desc
select * from savingaccount where accountid='SAIDBC10001'
delete from savingaccount where accountid=2
desc savingaccount
update savingaccount set createdate='2021-1-12'where accountid='SAIDBC10002'
drop table savingaccount
---------------------------
create table transactions(
transactionid int primary key,
transactiondate date not null,
transtype varchar(30),
fromaccount varchar(30),
toaccount varchar(30),
amount int not null)
insert into transactions values(54467,'2022-03-17','withdrawal','gfdgdf',null,50)
select * from transactions
----------------------------------
create table payaccount
(accountid varchar(30) primary key ,accountname varchar(40) not null,
amount int,
createdate 
)
select 2 from hi
SELECT EXISTS(SELECT 2 FROM hi)

select * from savingaccount where accountname like 'fg'
