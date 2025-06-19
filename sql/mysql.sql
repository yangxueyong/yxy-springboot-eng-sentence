-- english_sentence.t_eng_sentence_detail definition

CREATE TABLE `t_eng_sentence_detail` (
    `id` varchar(100) NOT NULL COMMENT '主键',
    `main_id` varchar(100) DEFAULT NULL COMMENT '主表id',
    `zn_name` varchar(100) DEFAULT NULL COMMENT '中文描述',
    `en_name` varchar(100) DEFAULT NULL COMMENT '英文描述',
    `sort` varchar(100) DEFAULT NULL COMMENT '排序',
    `local_audio_path` varchar(100) DEFAULT NULL COMMENT '本地英文音频路径',
    `status` varchar(100) DEFAULT 'available' COMMENT 'available可用 unavailable不可用',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='句型详情表';


-- english_sentence.t_eng_sentence_detail_word_rel definition

CREATE TABLE `t_eng_sentence_detail_word_rel` (
    `id` varchar(100) NOT NULL COMMENT '主键',
    `main_id` varchar(100) DEFAULT NULL COMMENT '主表id',
    `detail_id` varchar(100) DEFAULT NULL COMMENT '详情id',
    `word_id` varchar(100) DEFAULT NULL COMMENT '单词id',
    `sort` varchar(100) DEFAULT NULL COMMENT '排序',
    `status` varchar(100) DEFAULT 'available' COMMENT 'available可用 unavailable不可用',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='句型中的单词关系信息';


-- english_sentence.t_eng_sentence_main definition

CREATE TABLE `t_eng_sentence_main` (
    `id` varchar(100) NOT NULL COMMENT '主键',
    `zn_name` varchar(100) DEFAULT NULL COMMENT '中文描述',
    `en_name` varchar(100) DEFAULT NULL COMMENT '英文描述',
    `sort` varchar(100) DEFAULT NULL COMMENT '排序',
    `status` varchar(100) DEFAULT 'available' COMMENT 'available可用 unavailable不可用',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='句型主表';


-- english_sentence.t_eng_sentence_user_practice definition

CREATE TABLE `t_eng_sentence_user_practice` (
    `id` varchar(100) NOT NULL COMMENT '主键',
    `main_id` varchar(100) DEFAULT NULL COMMENT '主表id',
    `detail_id` varchar(100) DEFAULT NULL COMMENT '详情表id',
    `word_id` varchar(100) DEFAULT NULL COMMENT '单词id',
    `practice_type` varchar(100) DEFAULT NULL COMMENT '练习类型 sentence:句子 word:单词',
    `practice_val` varchar(100) DEFAULT NULL COMMENT '练习结果 success:成功 fail:失败',
    `last_time` datetime DEFAULT NULL COMMENT '最近练习的时间',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='练习表';


-- english_sentence.t_eng_sentence_word definition

CREATE TABLE `t_eng_sentence_word` (
    `id` varchar(100) NOT NULL COMMENT '主键',
    `zn_name` varchar(100) DEFAULT NULL COMMENT '中文描述',
    `en_name` varchar(100) DEFAULT NULL COMMENT '英文描述',
    `sort` varchar(100) DEFAULT NULL COMMENT '排序',
    `local_audio_path` varchar(100) DEFAULT NULL COMMENT '本地英文音频路径',
    `status` varchar(100) DEFAULT 'available' COMMENT 'available可用 unavailable不可用',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='句型中的单词信息';


-- english_sentence.t_eng_user_oper definition

CREATE TABLE `t_eng_user_oper` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `oper_type` varchar(100) DEFAULT NULL COMMENT '操作类型 hide:隐藏，flag:标记',
    `data_type` varchar(100) DEFAULT NULL COMMENT 'main:大类，detail:句子，word：单词',
    `data_id` varchar(100) DEFAULT NULL COMMENT '数据值',
    `user_id` bigint DEFAULT NULL COMMENT '用户id',
    `status` varchar(100) DEFAULT 'available' COMMENT 'available可用 unavailable不可用',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
     PRIMARY KEY (`id`),
     UNIQUE KEY `unq_t_eng_user_oper` (`user_id`,`data_id`,`data_type`,`oper_type`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户操作信息';


-- english_sentence.t_user definition

CREATE TABLE `t_user` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `third_type` varchar(100) DEFAULT NULL COMMENT '三方类型 wx:微信 al:阿里',
    `app_id` varchar(100) DEFAULT NULL COMMENT 'app_id',
    `open_id` varchar(100) DEFAULT NULL COMMENT 'open_id',
    `session_key` varchar(100) DEFAULT NULL COMMENT 'session_key',
    `phone_no` varchar(100) DEFAULT NULL COMMENT '电话',
    `en_name` varchar(100) DEFAULT NULL COMMENT '名称',
    `third_head_img_path` varchar(1000) DEFAULT NULL COMMENT '三方头像地址',
    `last_study_main_id` varchar(100) DEFAULT NULL COMMENT '最近学习的mainId',
    `last_practice_main_id` varchar(100) DEFAULT NULL COMMENT '最近练习的mainId',
    `last_study_detail_id` varchar(100) DEFAULT NULL COMMENT '最近学习的detailId',
    `last_study_word_id` varchar(100) DEFAULT NULL COMMENT '最近学习的wordId',
    `status` varchar(100) DEFAULT 'available' COMMENT 'available可用 unavailable不可用',
    `user_type` varchar(100) DEFAULT 'general' COMMENT 'general普通 member会员',
    `member_due_date` datetime DEFAULT NULL COMMENT '到期日',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
     PRIMARY KEY (`id`),
     UNIQUE KEY `unq_t_user` (`third_type`,`app_id`,`open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';