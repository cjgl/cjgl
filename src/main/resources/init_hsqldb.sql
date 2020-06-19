/*项目表*/
drop table t_sys_project if exists;
create table t_sys_project(
projectid int primary key IDENTITY,
projectname varchar(50),
remark varchar(200),
createtime varchar(23),
updatetime varchar(23),
delflag varchar(1)
);

/*子系统表*/
drop table t_sys_subsystem if exists;
create table t_sys_subsystem(
subsystemid int primary key IDENTITY,
projectid int,
subsystemname varchar(50),
type int,
remark varchar(200),
createtime varchar(23),
updatetime varchar(23),
delflag varchar(1)
);

/*角色表*/
drop table t_sys_role if exists;
create table t_sys_role(
roleid int primary key IDENTITY,
projectid int,
subsystemid int,
rolename varchar(50),
remark varchar(200),
createtime varchar(23),
updatetime varchar(23),
delflag varchar(1)
);

/*用户表*/
drop table t_sys_user if exists;
create table t_sys_user(
userid int primary key IDENTITY,
projectid int,
subsystemid int,
loginname varchar(50),
username varchar(50),
pwd varchar(50),
sso varchar(1),
createtime varchar(23),
updatetime varchar(23),
delflag varchar(1)
);

/*用户角色关联表*/
drop table t_sys_userrole if exists;
create table t_sys_userrole(
userroleid int primary key IDENTITY,
userid int,
roleid int
);

/*菜单表*/
drop table t_sys_menu if exists;
create table t_sys_menu(
menuid int primary key IDENTITY,
subsystemid int,
pmenuid int,
menuname varchar(50),
menuurl varchar(500),
iconcls varchar(500),
seqno int
);

/*角色菜单关联表*/
drop table t_sys_rolemenu if exists;
create table t_sys_rolemenu(
rolemenuid int primary key IDENTITY,
roleid int,
menuid int
);

drop table t_sys_permission if exists;
create table t_sys_permission(
permissionid int primary key IDENTITY,
menuid int,
permissionname varchar(50),
permissiontag varchar(50)
);

drop table t_sys_rolepermission if exists;
create table t_sys_rolepermission (
rolepermissionid int primary key IDENTITY,
permissionid int,
roleid int
);

insert into t_sys_project(projectid, projectname, remark, createtime, updatetime, delflag)
values(0, '后台管理项目', '后台管理项目', '2018-12-27 13:15:00', '2018-12-27 13:15:00', 0);

insert into t_sys_subsystem(subsystemid, projectid, subsystemname, type, remark, createtime, updatetime, delflag)
values(0, 0, '后台管理子系统', 0, '后台管理子系统', '2018-12-27 13:15:00', '2018-12-27 13:15:00', 0);

insert into t_sys_role(roleid, projectid, subsystemid, rolename, remark, createtime, updatetime, delflag)
values(0, 0, 0, '后台管理角色', '后台管理角色', '2018-12-27 13:15:00', '2018-12-27 13:15:00', 0);

insert into t_sys_user(userid, projectid, subsystemid, loginname, username, pwd, sso, createtime, updatetime, delflag)
values(0, 0, 0, 'admin', '超级管理员', '123456', '0', '2018-12-27 13:15:00', '2018-12-27 13:15:00', 0);

insert into t_sys_userrole(userid, roleid)
values(0, 0);

insert into t_sys_menu(menuid, subsystemid, pmenuid, menuname, menuurl, iconcls, seqno)
values(1, 0, 0, '系统信息管理', '', 'icon-gear', 1);

insert into t_sys_menu(menuid, subsystemid, pmenuid, menuname, menuurl, iconcls, seqno)
values(2, 0, 1, '项目管理', 'project/projectPage', 'icon-application', 1);

insert into t_sys_menu(menuid, subsystemid, pmenuid, menuname, menuurl, iconcls, seqno)
values(3, 0, 1, '子系统管理', 'subsystem/subSystemPage', 'icon-map', 2);

insert into t_sys_menu(menuid, subsystemid, pmenuid, menuname, menuurl, iconcls, seqno)
values(4, 0, 1, '角色管理', 'role/rolePage', 'icon-role', 3);

insert into t_sys_menu(menuid, subsystemid, pmenuid, menuname, menuurl, iconcls, seqno)
values(5, 0, 1, '用户管理', 'user/userPage', 'icon-user', 4);

insert into t_sys_menu(menuid, subsystemid, pmenuid, menuname, menuurl, iconcls, seqno)
values(6, 0, 1, '菜单管理', 'menu/menuPage', 'icon-layout', 5);

insert into t_sys_menu(menuid, subsystemid, pmenuid, menuname, menuurl, iconcls, seqno)
values(7, 0, 1, '功能权限管理', 'permission/permissionPage', 'icon-lock', 6);

insert into t_sys_menu(menuid, subsystemid, pmenuid, menuname, menuurl, iconcls, seqno)
values(8, 0, 0, '功能菜单', '', 'icon-applicationtree', 2);

insert into t_sys_rolemenu(roleid, menuid)
values(0, 1);

insert into t_sys_rolemenu(roleid, menuid)
values(0, 2);

insert into t_sys_rolemenu(roleid, menuid)
values(0, 3);

insert into t_sys_rolemenu(roleid, menuid)
values(0, 4);

insert into t_sys_rolemenu(roleid, menuid)
values(0, 5);

insert into t_sys_rolemenu(roleid, menuid)
values(0, 6);

insert into t_sys_rolemenu(roleid, menuid)
values(0, 7);

insert into t_sys_rolemenu(roleid, menuid)
values(0, 8);
