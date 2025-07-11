-- t_game_score definition
drop table if exists t_game_score;
CREATE TABLE `t_game_score` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` varchar(100) DEFAULT NULL COMMENT '用户类型',
    `game_level_id` varchar(50) DEFAULT NULL COMMENT '级别id',
    `game_type_id` varchar(50) DEFAULT NULL COMMENT '类型id',
    `consume_time` bigint(20) DEFAULT NULL COMMENT '消耗时间 单位毫秒',
    `status` varchar(100) DEFAULT 'available' COMMENT 'available可用，unavailable不可用',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unq_t_game_score_1` (`user_id`,`game_level_id`,`game_type_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='游戏_成绩';


-- t_num_game_animal definition
drop table if exists t_num_game_animal;
CREATE TABLE `t_num_game_animal` (
    `id` varchar(100) NOT NULL COMMENT '主键',
    `key` varchar(100) DEFAULT NULL COMMENT '键',
    `imgSrc` varchar(1000) DEFAULT NULL COMMENT '原图',
    `shadowSrc` varchar(1000) DEFAULT NULL COMMENT '影子图',
    `status` varchar(100) DEFAULT 'available' COMMENT 'available可用，unavailable不可用',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime DEFAULT NULL,
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数字游戏_动物';


-- t_num_game_color definition
drop table if exists t_num_game_color;
CREATE TABLE `t_num_game_color` (
    `id` varchar(100) NOT NULL COMMENT '主键',
    `key` varchar(100) DEFAULT NULL COMMENT '键',
    `value` varchar(100) DEFAULT NULL COMMENT '值',
    `content` varchar(1000) DEFAULT NULL COMMENT '文本',
    `status` varchar(100) DEFAULT 'available' COMMENT 'available可用，unavailable不可用',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime DEFAULT NULL,
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数字游戏_颜色卡';


-- t_num_game_level definition
drop table if exists t_num_game_level;
CREATE TABLE `t_num_game_level` (
    `id` varchar(100) NOT NULL COMMENT '主键',
    `sort` varchar(100) DEFAULT NULL COMMENT '排序',
    `type` varchar(100) DEFAULT NULL COMMENT '类型 simple:简单 memory:记忆',
    `levelHideTime` bigint(20) DEFAULT NULL COMMENT '每一关的记忆时间',
    `beginBlock` bigint(20) DEFAULT NULL COMMENT '第一关需要完成多少块',
    `beginNum` bigint(20) DEFAULT NULL COMMENT '对于数字关卡，第一关以什么数字开始',
    `levelSumNum` bigint(20) DEFAULT NULL COMMENT '总的关卡数量',
    `typeKey` varchar(100) DEFAULT NULL COMMENT '类型 general表示普通，custom表示自定义',
    `itemCls` varchar(100) DEFAULT 'v_item' COMMENT '元素的样式',
    `fontSize` varchar(100) DEFAULT '30rpx' COMMENT '字体的大小',
    `gameTime` bigint(20) DEFAULT NULL COMMENT '游戏时长',
    `colNum` bigint(20) DEFAULT NULL COMMENT '棋盘的列数',
    `numSum` bigint(20) DEFAULT NULL COMMENT '棋盘的方块数量',
    `title` varchar(100) DEFAULT NULL COMMENT '标题',
    `note` varchar(1000) DEFAULT NULL COMMENT '备注',
    `rightText` varchar(100) DEFAULT NULL COMMENT '右侧文字',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime DEFAULT NULL,
    `status` varchar(100) DEFAULT 'available' COMMENT 'available可用，unavailable不可用',
    `user_type` varchar(100) DEFAULT 'all' COMMENT 'all所有人都能玩，general,member只有会员或普通用户能玩',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数字游戏关卡';


-- t_num_game_type definition
drop table if exists t_num_game_type;
CREATE TABLE `t_num_game_type` (
    `id` varchar(100) NOT NULL COMMENT '主键',
    `sort` varchar(100) NOT NULL COMMENT '排序',
    `type` varchar(100) DEFAULT NULL COMMENT '类型 simple:简单 memory:记忆',
    `title` varchar(100) DEFAULT NULL COMMENT '标题',
    `note` varchar(1000) DEFAULT NULL COMMENT '描述',
    `rightText` varchar(1000) DEFAULT NULL COMMENT '右侧文字',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime DEFAULT NULL,
    `status` varchar(100) DEFAULT 'available' COMMENT 'available可用，unavailable不可用',
    `user_type` varchar(100) DEFAULT 'all' COMMENT 'all所有人都能玩，general,member只有会员或普通用户能玩',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数字游戏_类型';


INSERT INTO t_num_game_animal (id,`key`,imgSrc,shadowSrc,status,create_time,update_time) VALUES ('cbb771f2-5bd2-11f0-8c35-b8cef6abb4dc','a','longxia.png','longxia (1).png','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_animal (id,`key`,imgSrc,shadowSrc,status,create_time,update_time) VALUES ('cbb7751e-5bd2-11f0-8c35-b8cef6abb4dc','a','22-乌龟.png','22-乌龟 (1).png','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_animal (id,`key`,imgSrc,shadowSrc,status,create_time,update_time) VALUES ('cbb7759f-5bd2-11f0-8c35-b8cef6abb4dc','a','狗狗出售.png','狗狗出售 (1).png','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_animal (id,`key`,imgSrc,shadowSrc,status,create_time,update_time) VALUES ('cbb775f6-5bd2-11f0-8c35-b8cef6abb4dc','a','卡通大象.png','卡通大象 (1).png','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_animal (id,`key`,imgSrc,shadowSrc,status,create_time,update_time) VALUES ('cbb7763b-5bd2-11f0-8c35-b8cef6abb4dc','a','卡通海豚.png','卡通海豚 (1).png','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_animal (id,`key`,imgSrc,shadowSrc,status,create_time,update_time) VALUES ('cbb7767d-5bd2-11f0-8c35-b8cef6abb4dc','a','卡通猴子.png','卡通猴子 (1).png','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_animal (id,`key`,imgSrc,shadowSrc,status,create_time,update_time) VALUES ('cbb776b3-5bd2-11f0-8c35-b8cef6abb4dc','a','卡通鸡.png','卡通鸡 (1).png','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_animal (id,`key`,imgSrc,shadowSrc,status,create_time,update_time) VALUES ('cbb776ea-5bd2-11f0-8c35-b8cef6abb4dc','a','卡通鹿.png','卡通鹿 (1).png','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_animal (id,`key`,imgSrc,shadowSrc,status,create_time,update_time) VALUES ('cbb77733-5bd2-11f0-8c35-b8cef6abb4dc','a','卡通马.png','卡通马 (1).png','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_animal (id,`key`,imgSrc,shadowSrc,status,create_time,update_time) VALUES ('cbb7776c-5bd2-11f0-8c35-b8cef6abb4dc','a','卡通绵羊.png','卡通绵羊 (1).png','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_animal (id,`key`,imgSrc,shadowSrc,status,create_time,update_time) VALUES ('cbb777ab-5bd2-11f0-8c35-b8cef6abb4dc','a','卡通牛.png','卡通牛 (1).png','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_animal (id,`key`,imgSrc,shadowSrc,status,create_time,update_time) VALUES ('cbb77802-5bd2-11f0-8c35-b8cef6abb4dc','a','卡通企鹅.png','卡通企鹅 (1).png','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_animal (id,`key`,imgSrc,shadowSrc,status,create_time,update_time) VALUES ('cbb77847-5bd2-11f0-8c35-b8cef6abb4dc','a','卡通蛇.png','卡通蛇 (1).png','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_animal (id,`key`,imgSrc,shadowSrc,status,create_time,update_time) VALUES ('cbb77885-5bd2-11f0-8c35-b8cef6abb4dc','a','卡通鱼.png','卡通鱼 (1).png','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_animal (id,`key`,imgSrc,shadowSrc,status,create_time,update_time) VALUES ('cbb778b9-5bd2-11f0-8c35-b8cef6abb4dc','a','可爱的卡通鸟.png','可爱的卡通鸟 (1).png','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_animal (id,`key`,imgSrc,shadowSrc,status,create_time,update_time) VALUES ('cbb778f6-5bd2-11f0-8c35-b8cef6abb4dc','a','老虎.png','老虎 (1).png','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_animal (id,`key`,imgSrc,shadowSrc,status,create_time,update_time) VALUES ('cbb77930-5bd2-11f0-8c35-b8cef6abb4dc','a','螃蟹.png','螃蟹 (1).png','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_animal (id,`key`,imgSrc,shadowSrc,status,create_time,update_time) VALUES ('cbb77964-5bd2-11f0-8c35-b8cef6abb4dc','a','狮子.png','狮子 (1).png','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_animal (id,`key`,imgSrc,shadowSrc,status,create_time,update_time) VALUES ('cbb77996-5bd2-11f0-8c35-b8cef6abb4dc','a','鼠.png','鼠 (1).png','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_color (id,`key`,value,content,status,create_time,update_time) VALUES ('cba0ec4f-5bd2-11f0-8c35-b8cef6abb4dc','yellow','yellow','黄色 yellow','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_color (id,`key`,value,content,status,create_time,update_time) VALUES ('cba0ef33-5bd2-11f0-8c35-b8cef6abb4dc','red','red','红色 red','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_color (id,`key`,value,content,status,create_time,update_time) VALUES ('cba0efb2-5bd2-11f0-8c35-b8cef6abb4dc','brown','brown','棕色 brown','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_color (id,`key`,value,content,status,create_time,update_time) VALUES ('cba0f018-5bd2-11f0-8c35-b8cef6abb4dc','purple','purple','紫色 purple','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_color (id,`key`,value,content,status,create_time,update_time) VALUES ('cba0f05f-5bd2-11f0-8c35-b8cef6abb4dc','pink','pink','粉色 pink','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_color (id,`key`,value,content,status,create_time,update_time) VALUES ('cba0f0a1-5bd2-11f0-8c35-b8cef6abb4dc','green','green','绿色 green','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_color (id,`key`,value,content,status,create_time,update_time) VALUES ('cba0f0e6-5bd2-11f0-8c35-b8cef6abb4dc','blue','blue','蓝色 blue','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_color (id,`key`,value,content,status,create_time,update_time) VALUES ('cba0f137-5bd2-11f0-8c35-b8cef6abb4dc','white','white','白色 white','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_color (id,`key`,value,content,status,create_time,update_time) VALUES ('cba0f18a-5bd2-11f0-8c35-b8cef6abb4dc','black','black','黑色 black','available','2025-07-08 16:08:56',NULL);
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('1','01','memory',500,2,2,5,'general','v_item','100rpx',30,3,9,'3 ✖️ 3','1-9的数字','','2025-07-08 16:08:56',NULL,'available','all');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('2','02','memory',500,3,1,7,'general','v_item','80rpx',60,4,16,'4 ✖️ 4','1-16的数字','','2025-07-08 16:08:56',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('3','03','memory',500,1,1,10,'general','v_item','70rpx',100,5,25,'5 ✖️ 5','1-25的数字','','2025-07-08 16:08:56',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('4','04','memory',500,1,1,15,'general','v_item','60rpx',200,6,36,'6 ✖️ 6','1-36的数字','','2025-07-08 16:08:56',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('5','05','memory',500,1,1,25,'general','v_item','50rpx',300,7,49,'7 ✖️ 7','1-49的数字','','2025-07-08 16:08:56',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('6','06','memory',500,1,1,30,'general','v_item','30rpx',400,8,64,'8 ✖️ 8','1-64的数字','','2025-07-08 16:08:56',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('7','07','memory',500,1,1,40,'general','v_item','30rpx',500,9,81,'9 ✖️ 9','1-81的数字','','2025-07-08 16:08:56',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('8','08','memory',500,1,1,50,'general','v_item','30rpx',600,10,100,'10 ✖️ 10','1-100的数字','','2025-07-08 16:08:56',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('9','09','memory',500,1,1,1,'custom','v_item','30rpx',600,10,100,'自定义','自定义时间和数字区间','','2025-07-08 16:08:56',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('cc1','01','simple',NULL,NULL,1,NULL,'general','v_item','100rpx',600,3,9,'3 ✖️ 3','1-9的数字','','2025-07-08 14:32:34',NULL,'available','all');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('cc2','02','simple',NULL,NULL,1,NULL,'general','v_item','80rpx',600,4,16,'4 ✖️ 4','1-16的数字','','2025-07-08 14:32:34',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('cc3','03','simple',NULL,NULL,1,NULL,'general','v_item','70rpx',3600,5,25,'5 ✖️ 5','1-25的数字','','2025-07-08 14:32:34',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('cc4','04','simple',NULL,NULL,1,NULL,'general','v_item','60rpx',3600,6,36,'6 ✖️ 6','1-36的数字','','2025-07-08 14:32:34',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('cc5','05','simple',NULL,NULL,1,NULL,'general','v_item','50rpx',3600,7,49,'7 ✖️ 7','1-49的数字','','2025-07-08 14:32:34',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('cc6','06','simple',NULL,NULL,1,NULL,'general','v_item','30rpx',3600,8,64,'8 ✖️ 8','1-64的数字','','2025-07-08 14:32:34',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('cc7','07','simple',NULL,NULL,1,NULL,'general','v_item','30rpx',3600,9,81,'9 ✖️ 9','1-81的数字','','2025-07-08 14:32:34',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('cc8','08','simple',NULL,NULL,1,NULL,'general','v_item','30rpx',3600,10,100,'10 ✖️ 10','1-100的数字','','2025-07-08 14:32:34',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('cc9','09','simple',NULL,NULL,1,NULL,'custom','v_item','30rpx',600,10,100,'自定义','自定义时间和数字区间','','2025-07-08 14:32:34',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('gg1','01','remove',NULL,NULL,1,NULL,'general','v_item','24',600,3,9,'3 ✖️ 3','1-9的数字','','2025-07-08 14:32:34',NULL,'available','all');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('gg2','02','remove',NULL,NULL,2,NULL,'general','v_item','20',600,4,16,'4 ✖️ 4','1-16的数字','','2025-07-08 14:32:34',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('gg3','03','remove',NULL,NULL,1,NULL,'general','v_item','18',3600,5,25,'5 ✖️ 5','1-25的数字','','2025-07-08 14:32:34',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('gg4','04','remove',NULL,NULL,1,NULL,'general','v_item','16',3600,6,36,'6 ✖️ 6','1-36的数字','','2025-07-08 14:32:34',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('gg5','05','remove',NULL,NULL,1,NULL,'general','v_item','14',3600,7,49,'7 ✖️ 7','1-49的数字','','2025-07-08 14:32:34',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('gg6','06','remove',NULL,NULL,1,NULL,'general','v_item','14',3600,7,100,'10 ✖️ 10','1-100的数字','','2025-07-08 14:32:34',NULL,'available',',general,member');
INSERT INTO t_num_game_level (id,sort,`type`,levelHideTime,beginBlock,beginNum,levelSumNum,typeKey,itemCls,fontSize,gameTime,colNum,numSum,title,note,rightText,create_time,update_time,status,user_type) VALUES ('gg7','06','remove',NULL,NULL,1,NULL,'custom','v_item','14',3600,7,100,'自定义','点击这个进行自定义设置','','2025-07-08 14:32:34',NULL,'available',',general,member');
INSERT INTO t_num_game_type (id,sort,`type`,title,note,rightText,create_time,update_time,status,user_type) VALUES ('a1','06','memory','记忆颜色','点击颜色出现的地方','','2025-07-08 16:08:56',NULL,'available',',general,member');
INSERT INTO t_num_game_type (id,sort,`type`,title,note,rightText,create_time,update_time,status,user_type) VALUES ('a2','05','memory','记忆数字','点击数字出现的地方','','2025-07-08 16:08:56',NULL,'available',',general,member');
INSERT INTO t_num_game_type (id,sort,`type`,title,note,rightText,create_time,update_time,status,user_type) VALUES ('a3','02','memory','找出颜色','找出提示的颜色','','2025-07-08 16:08:56',NULL,'available',',general,member');
INSERT INTO t_num_game_type (id,sort,`type`,title,note,rightText,create_time,update_time,status,user_type) VALUES ('a4','01','memory','找出数字','找出提示的数字','','2025-07-08 16:08:56',NULL,'available','all');
INSERT INTO t_num_game_type (id,sort,`type`,title,note,rightText,create_time,update_time,status,user_type) VALUES ('a5','03','memory','找出动物','找出提示的动物','','2025-07-08 16:08:56',NULL,'available',',general,member');
INSERT INTO t_num_game_type (id,sort,`type`,title,note,rightText,create_time,update_time,status,user_type) VALUES ('a6','07','memory','记忆动物','点击动物出现的地方','','2025-07-08 16:08:56',NULL,'available',',general,member');
INSERT INTO t_num_game_type (id,sort,`type`,title,note,rightText,create_time,update_time,status,user_type) VALUES ('a7','08','memory','记忆影子','点击影子对应动物出现的地方','','2025-07-08 16:08:56',NULL,'available',',general,member');
INSERT INTO t_num_game_type (id,sort,`type`,title,note,rightText,create_time,update_time,status,user_type) VALUES ('a8','04','memory','找出动物的影子','找出提示的动物影子','','2025-07-08 16:08:56',NULL,'available',',general,member');
INSERT INTO t_num_game_type (id,sort,`type`,title,note,rightText,create_time,update_time,status,user_type) VALUES ('b1','02','simple','拖拽排序','拖拽小方块，按照从小到大的顺序排列','','2025-07-08 16:08:56',NULL,'available',',general,member');
INSERT INTO t_num_game_type (id,sort,`type`,title,note,rightText,create_time,update_time,status,user_type) VALUES ('b2','01','simple','顺序点击','按照从小到大的顺序点击小方块','','2025-07-08 16:08:56',NULL,'available','all');
INSERT INTO t_num_game_type (id,sort,`type`,title,note,rightText,create_time,update_time,status,user_type) VALUES ('kk1','01','remove','数字消消','点击相同的数字进行消除','','2025-07-08 16:08:56',NULL,'available','all');
INSERT INTO t_num_game_type (id,sort,`type`,title,note,rightText,create_time,update_time,status,user_type) VALUES ('kk2','02','remove','颜色消消','点击相同的颜色进行消除','','2025-07-08 16:08:56',NULL,'available',',general,member');
INSERT INTO t_num_game_type (id,sort,`type`,title,note,rightText,create_time,update_time,status,user_type) VALUES ('kk3','02','remove','动物消消','点击相同的动物进行消除','','2025-07-08 16:08:56',NULL,'available',',general,member');
INSERT INTO t_num_game_type (id,sort,`type`,title,note,rightText,create_time,update_time,status,user_type) VALUES ('kk4','02','remove','动物和影子消消','点击动物对应的影子进行消除','','2025-07-08 16:08:56',NULL,'available',',general,member');
