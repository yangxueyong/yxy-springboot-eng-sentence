
drop table if exists t_num_game_level;
CREATE TABLE t_num_game_level (
  id varchar(100) NOT NULL COMMENT '主键',
  sort varchar(100) null  COMMENT '排序',
  levelHideTime BIGINT NULL COMMENT '每一关的记忆时间',
  beginBlock BIGINT NULL COMMENT '第一关需要完成多少块',
  beginNum BIGINT NULL COMMENT '对于数字关卡，第一关以什么数字开始',
  levelSumNum BIGINT NULL COMMENT '总的关卡数量',
  typeKey varchar(100) NULL COMMENT '类型 general表示普通，custom表示自定义',
  itemCls varchar(100) DEFAULT 'v_item' NULL COMMENT '元素的样式',
  fontSize varchar(100) DEFAULT '30rpx' NULL COMMENT '字体的大小',
  gameTime BIGINT NULL COMMENT '游戏时长',
  colNum BIGINT NULL COMMENT '棋盘的列数',
  numSum BIGINT NULL COMMENT '棋盘的方块数量',
  title varchar(100) NULL COMMENT '标题',
  note varchar(1000) NULL COMMENT '备注',
  rightText varchar(100) NULL COMMENT '右侧文字',
  create_time datetime default now(),
  update_time datetime ,
  CONSTRAINT t_num_game_level_pk PRIMARY KEY (id)
)
    ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci
COMMENT='数字游戏关卡';


drop table if exists t_num_game_type;
CREATE TABLE t_num_game_type (
 id varchar(100) NOT NULL primary key COMMENT '主键',
 sort varchar(100) not null,
`title` varchar(100) COMMENT '标题',
 note varchar(1000) NULL COMMENT '描述',
 rightText varchar(1000) NULL COMMENT '右侧文字' ,
 create_time datetime default now(),
 update_time datetime
)
    ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci
COMMENT='数字游戏_类型';




drop table if exists t_num_game_color;
CREATE TABLE t_num_game_color (
id varchar(100) NOT NULL primary key COMMENT '主键',
`key` varchar(100) COMMENT '键',
value varchar(100) NULL COMMENT '值',
content varchar(1000) NULL COMMENT '文本' ,
status varchar(100) DEFAULT 'available'  COMMENT 'available可用，unavailable不可用',
create_time datetime default now(),
update_time datetime
)
    ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci
COMMENT='数字游戏_颜色卡';



drop table if exists t_num_game_animal;
CREATE TABLE t_num_game_animal (
id varchar(100) NOT NULL primary key COMMENT '主键',
`key` varchar(100) COMMENT '键',
imgSrc varchar(1000) NULL COMMENT '原图',
shadowSrc varchar(1000) NULL COMMENT '影子图' ,
status varchar(100) DEFAULT 'available'  COMMENT 'available可用，unavailable不可用',
create_time datetime default now(),
update_time datetime
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci
COMMENT='数字游戏_动物';

drop table if exists t_game_score;
CREATE TABLE t_game_score (
id bigint NOT NULL AUTO_INCREMENT primary key COMMENT '主键',
user_id varchar(100) COMMENT '用户类型',
game_level_id varchar(50) NULL COMMENT '级别id',
game_type_id varchar(50) NULL COMMENT '类型id' ,
consume_time bigint null COMMENT '消耗时间 单位毫秒',
status varchar(100) DEFAULT 'available'  COMMENT 'available可用，unavailable不可用',
create_time datetime default now(),
update_time datetime
)
    ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci
COMMENT='游戏_成绩';
create unique index unq_t_game_score_1 on t_game_score (user_id,game_level_id,game_type_id);


INSERT INTO t_num_game_level
(id,sort, levelHideTime, beginBlock, beginNum, levelSumNum, typeKey, itemCls, fontSize, gameTime, colNum, numSum, title, note, rightText, create_time, update_time)
VALUES
('1', '01', 500, 2, 2, 5, 'general', 'v_item', '100rpx', 30, 3, 9, '3 ✖️ 3', '1-9的数字', '', NOW(), NULL),
('2', '02', 500, 3, 1, 7, 'general', 'v_item', '80rpx', 60, 4, 16, '4 ✖️ 4', '1-16的数字', '', NOW(), NULL),
('3', '03', 500, 1, 1, 10, 'general', 'v_item', '70rpx', 100, 5, 25, '5 ✖️ 5', '1-25的数字', '', NOW(), NULL),
('4', '04', 500, 1, 1, 15, 'general', 'v_item', '60rpx', 200, 6, 36, '6 ✖️ 6', '1-36的数字', '', NOW(), NULL),
('5', '05', 500, 1, 1, 25, 'general', 'v_item', '50rpx', 300, 7, 49, '7 ✖️ 7', '1-49的数字', '', NOW(), NULL),
('6', '06', 500, 1, 1, 30, 'general', 'v_item', '30rpx', 400, 8, 64, '8 ✖️ 8', '1-64的数字', '', NOW(), NULL),
('7', '07', 500, 1, 1, 40, 'general', 'v_item', '30rpx', 500, 9, 81, '9 ✖️ 9', '1-81的数字', '', NOW(), NULL),
('8', '08', 500, 1, 1, 50, 'general', 'v_item', '30rpx', 600, 10, 100, '10 ✖️ 10', '1-100的数字', '', NOW(), NULL),
('9', '09', 500, 1, 1, 1, 'custom', 'v_item', '30rpx', 600, 10, 100, '自定义', '自定义时间和数字区间', '', NOW(), NULL);

INSERT INTO t_num_game_type
(id, sort,title, note, rightText, create_time, update_time)
VALUES
('a1', '01', '记忆颜色', '点击颜色出现的地方', '', NOW(), NULL),
('a2', '02', '记忆数字', '点击数字出现的地方', '', NOW(), NULL),
('a6', '03', '记忆动物', '点击动物出现的地方', '', NOW(), NULL),
('a7', '04', '记忆影子', '点击影子对应动物出现的地方', '', NOW(), NULL),
('a3', '05', '找出颜色', '找出提示的颜色', '', NOW(), NULL),
('a4', '06', '找出数字', '找出提示的数字', '', NOW(), NULL),
('a5', '07', '找出动物', '找出提示的动物', '', NOW(), NULL),
('a8', '08', '找出动物的影子', '找出提示的动物影子', '', NOW(), NULL);


INSERT INTO t_num_game_color
(id, `key`, value, content, create_time, update_time)
VALUES
(UUID(), 'yellow', 'yellow', '黄色 yellow', NOW(), NULL),
(UUID(), 'red', 'red', '红色 red', NOW(), NULL),
(UUID(), 'brown', 'brown', '棕色 brown', NOW(), NULL),
(UUID(), 'purple', 'purple', '紫色 purple', NOW(), NULL),
(UUID(), 'pink', 'pink', '粉色 pink', NOW(), NULL),
(UUID(), 'green', 'green', '绿色 green', NOW(), NULL),
(UUID(), 'blue', 'blue', '蓝色 blue', NOW(), NULL),
(UUID(), 'white', 'white', '白色 white', NOW(), NULL),
(UUID(), 'black', 'black', '黑色 black', NOW(), NULL);

INSERT INTO t_num_game_animal
(id, `key`, imgSrc, shadowSrc, create_time, update_time)
VALUES
    (UUID(), 'a', 'longxia.png', 'longxia (1).png', NOW(), NULL),
(UUID(), 'a', '22-乌龟.png', '22-乌龟 (1).png', NOW(), NULL),
(UUID(), 'a', '狗狗出售.png', '狗狗出售 (1).png', NOW(), NULL),
(UUID(), 'a', '卡通大象.png', '卡通大象 (1).png', NOW(), NULL),
(UUID(), 'a', '卡通海豚.png', '卡通海豚 (1).png', NOW(), NULL),
(UUID(), 'a', '卡通猴子.png', '卡通猴子 (1).png', NOW(), NULL),
(UUID(), 'a', '卡通鸡.png', '卡通鸡 (1).png', NOW(), NULL),
(UUID(), 'a', '卡通鹿.png', '卡通鹿 (1).png', NOW(), NULL),
(UUID(), 'a', '卡通马.png', '卡通马 (1).png', NOW(), NULL),
(UUID(), 'a', '卡通绵羊.png', '卡通绵羊 (1).png', NOW(), NULL),
(UUID(), 'a', '卡通牛.png', '卡通牛 (1).png', NOW(), NULL),
(UUID(), 'a', '卡通企鹅.png', '卡通企鹅 (1).png', NOW(), NULL),
(UUID(), 'a', '卡通蛇.png', '卡通蛇 (1).png', NOW(), NULL),
(UUID(), 'a', '卡通鱼.png', '卡通鱼 (1).png', NOW(), NULL),
(UUID(), 'a', '可爱的卡通鸟.png', '可爱的卡通鸟 (1).png', NOW(), NULL),
(UUID(), 'a', '老虎.png', '老虎 (1).png', NOW(), NULL),
(UUID(), 'a', '螃蟹.png', '螃蟹 (1).png', NOW(), NULL),
(UUID(), 'a', '狮子.png', '狮子 (1).png', NOW(), NULL),
(UUID(), 'a', '鼠.png', '鼠 (1).png', NOW(), NULL);


-- 新增状态字段
ALTER TABLE t_num_game_level ADD status varchar(100) DEFAULT 'available'  COMMENT 'available可用，unavailable不可用';
ALTER TABLE t_num_game_type ADD status varchar(100) DEFAULT 'available'  COMMENT 'available可用，unavailable不可用';

