/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50550
Source Host           : localhost:3306
Source Database       : security

Target Server Type    : MYSQL
Target Server Version : 50550
File Encoding         : 65001

Date: 2018-06-28 17:37:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` varchar(64) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `permission_order` int(11) DEFAULT NULL,
  `parent_id` varchar(64) DEFAULT NULL,
  `permission_method` varchar(255) DEFAULT NULL,
  `permission_name` varchar(255) NOT NULL,
  `permission_sign` varchar(255) DEFAULT NULL,
  `permission_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_persistent_login
-- ----------------------------
DROP TABLE IF EXISTS `sys_persistent_login`;
CREATE TABLE `sys_persistent_login` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(64) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role_permission_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission_rel`;
CREATE TABLE `sys_role_permission_rel` (
  `r_id` varchar(64) NOT NULL,
  `p_id` varchar(64) NOT NULL,
  PRIMARY KEY (`r_id`,`p_id`),
  KEY `p_id` (`p_id`),
  CONSTRAINT `sys_role_permission_rel_ibfk_1` FOREIGN KEY (`r_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_role_permission_rel_ibfk_2` FOREIGN KEY (`p_id`) REFERENCES `sys_permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(64) NOT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `state` int(11) DEFAULT NULL,
  `user_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user_role_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_rel`;
CREATE TABLE `sys_user_role_rel` (
  `u_id` varchar(64) NOT NULL,
  `r_id` varchar(64) NOT NULL,
  PRIMARY KEY (`u_id`,`r_id`),
  KEY `r_id` (`r_id`) USING BTREE,
  CONSTRAINT `sys_user_role_rel_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_user_role_rel_ibfk_2` FOREIGN KEY (`r_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
