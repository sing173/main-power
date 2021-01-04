/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : sz_ai_hall

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2020-08-24 14:06:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ums_admin
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin`;
CREATE TABLE `ums_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `icon` varchar(500) DEFAULT NULL COMMENT '头像',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(200) DEFAULT NULL COMMENT '昵称',
  `note` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` int(1) DEFAULT '1' COMMENT '帐号启用状态：0->禁用；1->启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='后台用户表';

-- ----------------------------
-- Records of ums_admin
-- ----------------------------
INSERT INTO `ums_admin` VALUES ('3', 'admin', '$2a$10$.E1FokumK5GIXWgKlg.Hc.i/0/2.qdAwYFL1zc5QHdyzpXOr38RZO', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg', 'admin@163.com', '系统管理员', '系统管理员', '2018-10-08 13:32:47', '2019-04-20 12:45:16', '1');
INSERT INTO `ums_admin` VALUES ('10', 'ceshi', '$2a$10$RaaNo9CC0RSms8mc/gJpCuOWndDT4pHH0u5XgZdAAYFs1Uq4sOPRi', null, 'ceshi@qq.com', 'ceshi', null, '2020-03-13 16:23:30', null, '1');

-- ----------------------------
-- Table structure for ums_admin_login_log
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin_login_log`;
CREATE TABLE `ums_admin_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `ip` varchar(64) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `user_agent` varchar(100) DEFAULT NULL COMMENT '浏览器登录类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=287 DEFAULT CHARSET=utf8 COMMENT='后台用户登录日志表';

-- ----------------------------
-- Records of ums_admin_login_log
-- ----------------------------
INSERT INTO `ums_admin_login_log` VALUES ('285', '3', '2020-08-24 14:05:21', '0:0:0:0:0:0:0:1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('286', '10', '2020-08-24 14:05:39', '0:0:0:0:0:0:0:1', null, null);

-- ----------------------------
-- Table structure for ums_admin_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin_role_relation`;
CREATE TABLE `ums_admin_role_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='后台用户和角色关系表';

-- ----------------------------
-- Records of ums_admin_role_relation
-- ----------------------------
INSERT INTO `ums_admin_role_relation` VALUES ('26', '3', '5');
INSERT INTO `ums_admin_role_relation` VALUES ('39', '10', '8');

-- ----------------------------
-- Table structure for ums_menu
-- ----------------------------
DROP TABLE IF EXISTS `ums_menu`;
CREATE TABLE `ums_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `title` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `level` int(4) DEFAULT NULL COMMENT '菜单级数',
  `sort` int(4) DEFAULT NULL COMMENT '菜单排序',
  `name` varchar(100) DEFAULT NULL COMMENT '前端名称',
  `icon` varchar(200) DEFAULT NULL COMMENT '前端图标',
  `hidden` int(1) DEFAULT NULL COMMENT '前端隐藏',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='后台菜单表';

-- ----------------------------
-- Records of ums_menu
-- ----------------------------
INSERT INTO `ums_menu` VALUES ('21', '0', '2020-02-07 16:29:13', '权限管理', '0', '0', 'ums', 'ums', '0');
INSERT INTO `ums_menu` VALUES ('22', '21', '2020-02-07 16:29:51', '用户列表', '1', '0', 'admin', 'ums-admin', '0');
INSERT INTO `ums_menu` VALUES ('23', '21', '2020-02-07 16:30:13', '角色列表', '1', '0', 'role', 'ums-role', '0');
INSERT INTO `ums_menu` VALUES ('24', '21', '2020-02-07 16:30:53', '菜单列表', '1', '0', 'menu', 'ums-menu', '0');
INSERT INTO `ums_menu` VALUES ('25', '21', '2020-02-07 16:31:13', '资源列表', '1', '0', 'resource', 'ums-resource', '0');

-- ----------------------------
-- Table structure for ums_resource
-- ----------------------------
DROP TABLE IF EXISTS `ums_resource`;
CREATE TABLE `ums_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `name` varchar(200) DEFAULT NULL COMMENT '资源名称',
  `url` varchar(200) DEFAULT NULL COMMENT '资源URL',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `category_id` bigint(20) DEFAULT NULL COMMENT '资源分类ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='后台资源表';

-- ----------------------------
-- Records of ums_resource
-- ----------------------------
INSERT INTO `ums_resource` VALUES ('25', '2020-02-07 16:47:34', '后台用户管理', '/admin/**', '', '4');
INSERT INTO `ums_resource` VALUES ('26', '2020-02-07 16:48:24', '后台用户角色管理', '/role/**', '', '4');
INSERT INTO `ums_resource` VALUES ('27', '2020-02-07 16:48:48', '后台菜单管理', '/menu/**', '', '4');
INSERT INTO `ums_resource` VALUES ('28', '2020-02-07 16:49:18', '后台资源分类管理', '/resourceCategory/**', '', '4');
INSERT INTO `ums_resource` VALUES ('29', '2020-02-07 16:49:45', '后台资源管理', '/resource/**', '', '4');

-- ----------------------------
-- Table structure for ums_resource_category
-- ----------------------------
DROP TABLE IF EXISTS `ums_resource_category`;
CREATE TABLE `ums_resource_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `name` varchar(200) DEFAULT NULL COMMENT '分类名称',
  `sort` int(4) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='资源分类表';

-- ----------------------------
-- Records of ums_resource_category
-- ----------------------------
INSERT INTO `ums_resource_category` VALUES ('4', '2020-02-05 10:23:04', '权限模块', '0');
INSERT INTO `ums_resource_category` VALUES ('5', '2020-02-07 16:34:27', '内容模块', '0');

-- ----------------------------
-- Table structure for ums_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `admin_count` int(11) DEFAULT NULL COMMENT '后台用户数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(1) DEFAULT '1' COMMENT '启用状态：0->禁用；1->启用',
  `sort` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='后台用户角色表';

-- ----------------------------
-- Records of ums_role
-- ----------------------------
INSERT INTO `ums_role` VALUES ('5', '超级管理员', '拥有所有查看和操作功能', '0', '2020-02-02 15:11:05', '1', '0');
INSERT INTO `ums_role` VALUES ('8', '权限管理员', '用于权限模块所有操作功能', '0', '2020-08-24 10:57:35', '1', '0');

-- ----------------------------
-- Table structure for ums_role_menu_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_menu_relation`;
CREATE TABLE `ums_role_menu_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8 COMMENT='后台角色菜单关系表';

-- ----------------------------
-- Records of ums_role_menu_relation
-- ----------------------------
INSERT INTO `ums_role_menu_relation` VALUES ('91', '5', '21');
INSERT INTO `ums_role_menu_relation` VALUES ('92', '5', '22');
INSERT INTO `ums_role_menu_relation` VALUES ('93', '5', '23');
INSERT INTO `ums_role_menu_relation` VALUES ('94', '5', '24');
INSERT INTO `ums_role_menu_relation` VALUES ('95', '5', '25');
INSERT INTO `ums_role_menu_relation` VALUES ('106', '8', '21');
INSERT INTO `ums_role_menu_relation` VALUES ('107', '8', '22');
INSERT INTO `ums_role_menu_relation` VALUES ('108', '8', '23');
INSERT INTO `ums_role_menu_relation` VALUES ('109', '8', '24');
INSERT INTO `ums_role_menu_relation` VALUES ('110', '8', '25');

-- ----------------------------
-- Table structure for ums_role_resource_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_resource_relation`;
CREATE TABLE `ums_role_resource_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `resource_id` bigint(20) DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=216 DEFAULT CHARSET=utf8 COMMENT='后台角色资源关系表';

-- ----------------------------
-- Records of ums_role_resource_relation
-- ----------------------------
INSERT INTO `ums_role_resource_relation` VALUES ('165', '5', '25');
INSERT INTO `ums_role_resource_relation` VALUES ('166', '5', '26');
INSERT INTO `ums_role_resource_relation` VALUES ('167', '5', '27');
INSERT INTO `ums_role_resource_relation` VALUES ('168', '5', '28');
INSERT INTO `ums_role_resource_relation` VALUES ('169', '5', '29');
INSERT INTO `ums_role_resource_relation` VALUES ('211', '8', '25');
INSERT INTO `ums_role_resource_relation` VALUES ('212', '8', '26');
INSERT INTO `ums_role_resource_relation` VALUES ('213', '8', '27');
INSERT INTO `ums_role_resource_relation` VALUES ('214', '8', '28');
INSERT INTO `ums_role_resource_relation` VALUES ('215', '8', '29');


DROP TABLE IF EXISTS km_document;
CREATE TABLE km_document(
  id VARCHAR(32) NOT NULL   COMMENT '主键' ,
  title VARCHAR(128) NOT NULL   COMMENT '标题' ,
  sub_title VARCHAR(128)    COMMENT '副标题' ,
  content VARCHAR(3072) NOT NULL   COMMENT '内容' ,
  summary VARCHAR(512)    COMMENT '摘要' ,
  category_id VARCHAR(32) NOT NULL   COMMENT '类目' ,
  tag VARCHAR(32)    COMMENT '标签' ,
  keyword VARCHAR(32)    COMMENT '核心词' ,
  channel VARCHAR(32)    COMMENT '渠道' ,
  status CHAR(1)    COMMENT '状态' ,
  weight INT(10)    COMMENT '权重' ,
  recommend INT(10)    COMMENT '推荐值' ,
  views INT(10)    COMMENT '浏览次数' ,
  diggs INT(10)    COMMENT '赞次数' ,
  burys INT(10)    COMMENT '踩次数' ,
  created_by VARCHAR(32)    COMMENT '创建人' ,
  created_time DATETIME    COMMENT '创建时间' ,
  updated_by VARCHAR(32)    COMMENT '更新人' ,
  updated_time DATETIME    COMMENT '更新时间' ,
  publish_time DATETIME    COMMENT '发布时间' ,
  short_url VARCHAR(32)    COMMENT '短域名 url' ,
  km_lib_id VARCHAR(32)    COMMENT '所属知识库' ,
  PRIMARY KEY (id)
) COMMENT = '知识文档 ';
ALTER TABLE km_document COMMENT '知识文档';

DROP TABLE IF EXISTS  km_category;
CREATE TABLE km_category(
  id VARCHAR(32) NOT NULL   COMMENT '主键' ,
  untitled VARCHAR(32) NOT NULL   COMMENT '名称' ,
  untitled1 INT(10)    COMMENT '排序' ,
  untitled7 VARCHAR(1)    COMMENT '是否置顶' ,
  untitled4 INT(10)    COMMENT '推荐值' ,
  untitled3 VARCHAR(128)    COMMENT '图标路径' ,
  parent_id VARCHAR(32)    COMMENT '父级' ,
  created_by VARCHAR(32)    COMMENT '创建人' ,
  created_time DATETIME    COMMENT '创建时间' ,
  updated_by VARCHAR(32)    COMMENT '更新人' ,
  updated_time DATETIME    COMMENT '更新时间' ,
  PRIMARY KEY (id)
) COMMENT = '知识类目 ';
ALTER TABLE km_category COMMENT '知识类目';

DROP TABLE IF EXISTS  cms_banner;
CREATE TABLE cms_banner(
  id BIGINT NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
  title VARCHAR(128) NOT NULL   COMMENT '标题' ,
  banner_path VARCHAR(128) NOT NULL   COMMENT 'banner路径' ,
  banner_size INT NOT NULL   COMMENT 'banner大小' ,
  banner_format VARCHAR(32)    COMMENT 'banner格式' ,
  status CHAR(1)    COMMENT '状态' ,
  type VARCHAR(32)    COMMENT '类型 图片、视频' ,
  recommend INT    COMMENT '推荐值' ,
  sort INT    COMMENT '排序' ,
  summary VARCHAR(512)    COMMENT '摘要' ,
  loop_interval INT    COMMENT '轮播间隔' ,
  created_by BIGINT    COMMENT '创建人' ,
  created_time DATETIME    COMMENT '创建时间' ,
  updated_by BIGINT    COMMENT '更新人' ,
  updated_time DATETIME    COMMENT '更新时间' ,
  PRIMARY KEY (id)
) COMMENT = '广告 ';
ALTER TABLE cms_banner COMMENT '广告';

DROP TABLE IF EXISTS  cms_recommend;
CREATE TABLE cms_recommend(
  id BIGINT NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
  title VARCHAR(128) NOT NULL   COMMENT '标题' ,
  cover_path VARCHAR(128) NOT NULL   COMMENT '封面图路径' ,
  cover_size INT NOT NULL   COMMENT '封面图大小' ,
  cover_format VARCHAR(32) NOT NULL   COMMENT '封面图格式' ,
  content_path VARCHAR(128) NOT NULL   COMMENT '内容图路径' ,
  content_size INT NOT NULL   COMMENT '内容图大小' ,
  content_format VARCHAR(32) NOT NULL   COMMENT '内容图格式' ,
  status CHAR(1)    COMMENT '状态' ,
  type VARCHAR(32)    COMMENT '类型 产品、活动' ,
  recommend INT    COMMENT '推荐值' ,
  weight INT    COMMENT '权重' ,
  sort INT    COMMENT '排序' ,
  summary VARCHAR(512)    COMMENT '摘要' ,
  tag VARCHAR(32)    COMMENT '标签' ,
  views INT    COMMENT '浏览次数' ,
  diggs INT    COMMENT '赞次数' ,
  burys INT    COMMENT '踩次数' ,
  created_by BIGINT    COMMENT '创建人' ,
  created_time DATETIME    COMMENT '创建时间' ,
  updated_by BIGINT    COMMENT '更新人' ,
  updated_time DATETIME    COMMENT '更新时间' ,
  link_url VARCHAR(1024)    COMMENT '内容链接' ,
  PRIMARY KEY (id)
) COMMENT = '推荐 ';
ALTER TABLE cms_recommend COMMENT '推荐';

DROP TABLE IF EXISTS cms_recommend_config;
CREATE TABLE cms_recommend_config(
  id BIGINT NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
  rule_content VARCHAR(1024) NOT NULL   COMMENT '规则' ,
  recommend_tag VARCHAR(32) NOT NULL   COMMENT '推荐标签' ,
  hits INT    COMMENT '命中次数' ,
  is_valid CHAR(1)    COMMENT '是否有效' ,
  created_by BIGINT    COMMENT '创建人' ,
  created_time DATETIME    COMMENT '创建时间' ,
  updated_by BIGINT    COMMENT '更新人' ,
  updated_time DATETIME    COMMENT '更新时间' ,
  PRIMARY KEY (id)
) COMMENT = '推荐配置 ';
ALTER TABLE cms_recommend_config COMMENT '推荐配置';

DROP TABLE IF EXISTS cms_device;
CREATE TABLE cms_device(
  id BIGINT NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
  code VARCHAR(64)    COMMENT '唯一编号' ,
  name VARCHAR(32)    COMMENT '名称' ,
  status CHAR(1)    COMMENT '状态 在线、已下线' ,
  cms_status VARCHAR(32)    COMMENT '内容状态 已发布、失效' ,
  maker VARCHAR(128)    COMMENT '生产厂商' ,
  model_type VARCHAR(128)    COMMENT '使用地址' ,
  sub_branch VARCHAR(128)    COMMENT '所属支行' ,
  last_login_time DATETIME    COMMENT '最近登录时间' ,
  on_line_time DATETIME    COMMENT '上线时间' ,
  updated_by BIGINT    COMMENT '更新人' ,
  off_line_time DATETIME    COMMENT '下线时间' ,
  reg_time DATETIME    COMMENT '注册时间' ,
  updated_time DATETIME    COMMENT '更新时间' ,
  PRIMARY KEY (id)
) COMMENT = '智慧屏设备 ';
ALTER TABLE cms_device COMMENT '智慧屏设备';

DROP TABLE IF EXISTS cms_device_banner;
CREATE TABLE cms_device_banner(
  id BIGINT NOT NULL   COMMENT '主键' ,
  device_id BIGINT NOT NULL   COMMENT '设备id' ,
  device_code VARCHAR(64) NOT NULL   COMMENT '设备编号' ,
  banner_id BIGINT NOT NULL   COMMENT '广告id' ,
  banner_title VARCHAR(128)    COMMENT '广告标题' ,
  status CHAR(1)    COMMENT '状态' ,
  sort INT    COMMENT '序号' ,
  PRIMARY KEY (id)
) COMMENT = '设备关联广告 设备关联广告的中间表';
ALTER TABLE cms_device_banner COMMENT '设备关联广告';

DROP TABLE IF EXISTS  cms_device_recommend;
CREATE TABLE cms_device_recommend(
  id BIGINT NOT NULL   COMMENT '主键' ,
  device_id BIGINT NOT NULL   COMMENT '设备id' ,
  device_code VARCHAR(64) NOT NULL   COMMENT '设备编号' ,
  banner_id BIGINT NOT NULL   COMMENT '推荐id' ,
  banner_title VARCHAR(128)    COMMENT '推荐标题' ,
  status CHAR(1)    COMMENT '状态' ,
  sort INT    COMMENT '序号' ,
  views INT    COMMENT '浏览次数' ,
  PRIMARY KEY (id)
) COMMENT = '设备关联推荐 设备关联推荐内容的中间表';
ALTER TABLE cms_device_recommend COMMENT '设备关联推荐';
