CREATE TABLE IF NOT EXISTS `user`
(
    `id`          varchar(128) NOT NULL,
    `username`    varchar(128) NOT NULL,
    `_name`       varchar(128) NOT NULL,
    `enabled`     tinyint(1)   DEFAULT '1',
    `email`       varchar(512) DEFAULT NULL,
    `phone`       varchar(50)  DEFAULT NULL,
    `password`    varchar(512) DEFAULT NULL,
    `source`      varchar(50)  NOT NULL COMMENT '用户来源:本地/第三方',
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `role`
(
    `id`             varchar(64)               NOT NULL COMMENT '角色ID',
    `_type`          enum ('origin','inherit') NOT NULL COMMENT '角色分类：系统内置角色、继承角色',
    `_name`          varchar(64)               NOT NULL,
    `_description`   varchar(255) DEFAULT NULL,
    `parent_role_id` varchar(64)  DEFAULT NULL COMMENT '父角色ID',
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

INSERT INTO role (id, _type, _name, _description, parent_role_id)
VALUES ('ADMIN', 'origin', '系统管理员', '系统管理员', 'ADMIN');
INSERT INTO role (id, _type, _name, _description, parent_role_id)
VALUES ('ORGADMIN', 'origin', '组织管理员', '组织管理员', 'ORGADMIN');
INSERT INTO role (id, _type, _name, _description, parent_role_id)
VALUES ('USER', 'origin', '普通用户', '工作空间用户', 'USER');

CREATE TABLE `user_role`
(
    `id`          varchar(64)  NOT NULL,
    `user_id`     varchar(128) NOT NULL,
    `role_id`     varchar(64)  NOT NULL,
    `_source`   varchar(64) DEFAULT NULL,
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
)

/**工作空间**/
CREATE TABLE IF NOT EXISTS `workspace`
(
    `id` varchar(64) NOT NULL,
    `_name` varchar(128) NOT NULL,
    `_description` varchar(255) DEFAULT NULL,
    `organization_id` varchar(64) NOT NULL,
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
)
/**工作空间**/