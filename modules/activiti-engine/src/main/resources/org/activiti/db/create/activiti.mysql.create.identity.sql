create table ACT_ID_GROUP (
    ID_ varchar(64) COMMENT '组ID', -- 添加数据时需要自己维护
    ROLEID_ varchar(255) COMMENT '角色ID',
    REV_ integer,
    NAME_ varchar(255),
    TYPE_ varchar(255),
    SYSTEMID_ varchar(255) COMMENT '系统标识', -- 系统标识
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_ID_MEMBERSHIP (
    USER_ID_ varchar(64) COMMENT '用户ID',-- 用户ID
    GROUP_ID_ varchar(64) COMMENT '组ID', -- 组ID
    SYSTEMID_ varchar(255) COMMENT '系统标识', -- 系统标识
    primary key (USER_ID_, GROUP_ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_ID_USER (
    ID_ varchar(64) COMMENT '主键ID', -- 主键ID
    REV_ integer,
    USERID_ varchar(255) COMMENT '用户ID', -- 用户ID
    FIRST_ varchar(255) COMMENT '用户名', -- 用户名
    LAST_ varchar(255),
    EMAIL_ varchar(255),
    PWD_ varchar(255),
    PICTURE_ID_ varchar(64),
    SYSTEMID_ varchar(255) COMMENT '系统标识', -- 系统标识
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_ID_INFO (
    ID_ varchar(64),
    REV_ integer,
    USER_ID_ varchar(64),
    TYPE_ varchar(64),
    KEY_ varchar(255),
    VALUE_ varchar(255),
    PASSWORD_ LONGBLOB,
    PARENT_ID_ varchar(255),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

alter table ACT_ID_MEMBERSHIP 
    add constraint ACT_FK_MEMB_GROUP 
    foreign key (GROUP_ID_) -- 组ID
    references ACT_ID_GROUP (ID_);

alter table ACT_ID_MEMBERSHIP 
    add constraint ACT_FK_MEMB_USER 
    foreign key (USER_ID_) -- 用户ID
    references ACT_ID_USER (ID_);
