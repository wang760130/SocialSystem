
-- Table "t_dynamics" DDL

CREATE TABLE `t_dynamics` (
  `id` bigint(20) NOT NULL,
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '动态类型 1:私有 2:公共',
  `text` varchar(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '文本',
  `coords` varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '坐标',
  `addtime` datetime NOT NULL COMMENT '添加时间',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态值,可自行使用',
  PRIMARY KEY (`id`),
  KEY `idx_dynamics_userid` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



-- Table "t_friends" DDL

CREATE TABLE `t_friends` (
  `id` bigint(20) NOT NULL,
  `userid1` bigint(20) NOT NULL COMMENT '用户1',
  `userid2` bigint(20) NOT NULL COMMENT '用户2',
  `addtime` datetime NOT NULL COMMENT '添加时间',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态值,可自行使用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



-- Table "t_thumbs" DDL

CREATE TABLE `t_thumbs` (
  `id` bigint(20) NOT NULL,
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '动态类型 1:喜欢 2:不喜欢',
  `dynid` bigint(20) NOT NULL COMMENT '动态id',
  `addtime` datetime NOT NULL COMMENT '添加时间',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态值,可自行使用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- Table "t_user" DDL

CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL,
  `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户名',
  `sex` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别 1:男 2:女',
  `addtime` datetime NOT NULL COMMENT '添加时间',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态值,可自行使用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
