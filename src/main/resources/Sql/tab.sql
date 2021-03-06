CREATE TABLE `tab_user_info` (
	`id` VARCHAR(50) NOT NULL COMMENT '用户主键,uuid',
	`user_id` BIGINT(20) NOT NULL COMMENT '用户id',
	`nick_name` VARCHAR(64) NULL DEFAULT NULL COMMENT '昵称',
	`head_img_url` VARCHAR(255) NULL DEFAULT NULL COMMENT '头像',
	`real_name` VARCHAR(64) NULL DEFAULT NULL COMMENT '真实姓名',
	`gender` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '用户性别: 0 未知；1:男；2：女',
	`card_type` TINYINT(4) NULL DEFAULT NULL COMMENT '证件类型',
	`card_no` VARCHAR(100) NULL DEFAULT NULL COMMENT '证件号码',
	`email` VARCHAR(64) NULL DEFAULT NULL COMMENT '用户邮箱',
	`mobile` VARCHAR(16) NULL DEFAULT NULL COMMENT '用户手机号码',
	`language` VARCHAR(50) NULL DEFAULT NULL COMMENT '用户的语言，简体中文为zh_CN',
	`city` VARCHAR(60) NULL DEFAULT NULL COMMENT '用户所在城市',
	`province` VARCHAR(60) NULL DEFAULT NULL COMMENT '用户所在省份',
	`country` VARCHAR(60) NULL DEFAULT NULL COMMENT '用户所在国家',
	`operator` VARCHAR(50) NULL DEFAULT NULL COMMENT '操作者',
	`create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
	`modify_time` DATETIME NULL DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY (`id`)
)
COMMENT='用户信息表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;
