-- 创建库
create database if not exists api_db;

-- 切换库
use api_db;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userName     varchar(256)                           null comment '用户昵称',
    userAccount  varchar(256)                           not null comment '账号',
    userAvatar   varchar(1024)                          null comment '用户头像',
    gender       tinyint                                null comment '性别',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user / admin',
    userPassword varchar(512)                           not null comment '密码',
    accessKey    varchar(512)                           null comment 'accessKey',
    secretKey    varchar(512)                           null comment 'secretKey',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    constraint uni_userAccount
        unique (userAccount)
) comment '用户';


-- 接口信息
create table if not exists `interface_info`
(
`id` bigint not null auto_increment comment '主键' primary key,
`name` varchar(256) not null comment '名称',
`description` varchar(256) null comment '接口描述',
`url` varchar(512) not null comment '接口地址',
`requestParams` text not null comment '接口地址',
`requestHeader` text null comment '请求头',
`responseHeader` text null comment '响应头',
`status` int default 0 not null comment '接口状态 (0-关闭，1-开启)',
`method` varchar(256) not null comment '请求类型',
`userId` bigint not null comment '创建人',
`createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
`updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
`isDeleted` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '接口信息';

insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('叶昊天', '莫熠彤', 'www.everette-hegmann.biz', '韦鹏煊', '魏展鹏', 0, '郑智宸', 5479944);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('余绍齐', '李嘉熙', 'www.cordelia-ziemann.org', '程思', '唐建辉', 0, '谭智宸', 1523600);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('孔懿轩', '魏梓晨', 'www.eldridge-torp.io', '蒋志强', '吴雨泽', 0, '万越彬', 64692);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('王彬', '钟晓啸', 'www.dessie-baumbach.net', '陆煜城', '钟远航', 0, '宋伟泽', 42355);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('叶文轩', '郝健雄', 'www.annelle-bernier.info', '程鑫磊', '李潇然', 0, '韦志泽', 2945567701);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('李雪松', '汪鹭洋', 'www.rikki-greenfelder.info', '方昊强', '傅昊强', 0, '阎黎昕', 226);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('邱煜祺', '傅鸿煊', 'www.lawerence-gusikowski.com', '唐语堂', '侯烨伟', 0, '莫天翊', 7833362380);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('武熠彤', '姚琪', 'www.annice-fahey.net', '钱哲瀚', '廖昊焱', 0, '莫浩宇', 959377);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('孔熠彤', '洪涛', 'www.angelica-zulauf.org', '何航', '吕瑾瑜', 0, '丁晋鹏', 11);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('姜明轩', '赖嘉熙', 'www.stephen-becker.biz', '孟黎昕', '侯晓啸', 0, '李智渊', 75850);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('杜文', '赖明哲', 'www.tynisha-abbott.co', '杨文', '卢果', 0, '董风华', 308133);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('魏懿轩', '周昊天', 'www.cary-nienow.org', '朱嘉熙', '赵弘文', 0, '秦烨华', 4323930);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('陆旭尧', '邱思源', 'www.jake-haley.org', '魏耀杰', '曹思淼', 0, '钱志强', 36271072);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('冯懿轩', '魏鹏煊', 'www.demetrius-larkin.name', '叶烨华', '贾烨霖', 0, '雷旭尧', 197194);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('熊越彬', '叶炎彬', 'www.casimira-thompson.info', '何旭尧', '熊志强', 0, '阎浩', 626);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('吕浩', '钟智宸', 'www.brian-champlin.com', '白语堂', '李擎宇', 0, '叶梓晨', 3);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('邱航', '赖明哲', 'www.enedina-stiedemann.biz', '陶泽洋', '汪鑫磊', 0, '秦晟睿', 47);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('薛炎彬', '严鹭洋', 'www.stevie-schaden.info', '郑哲瀚', '曹晟睿', 0, '程天翊', 8074761942);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('王风华', '史鹏煊', 'www.dante-corwin.info', '张伟祺', '谭修洁', 0, '李浩', 246847289);
insert into `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('熊博超', '邵浩轩', 'www.omar-turner.biz', '唐绍辉', '余雨泽', 0, '丁炎彬', 853395);

-- 用户调用接口关系表
create table if not exists `user_interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
  	`userId` bigint not null comment '调用用户 id',
    `interfaceInfoId` bigint not null comment '接口 id',
   	`totalNum` int default 0 not null comment '总调用次数',
  	`leftNum` int default 0 not null comment '剩余调用次数',
  	`status` int default 0 not null comment '0-正常，1-禁用',
  	`createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '用户调用接口关系';