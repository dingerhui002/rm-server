/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.7.27-log : Database - rm
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rm` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `rm`;

/*Table structure for table `t_backlog` */

DROP TABLE IF EXISTS `t_backlog`;

CREATE TABLE `t_backlog` (
  `backlog_id` varchar(32) NOT NULL COMMENT '待办事项表主键',
  `backlog_epic_id` varchar(32) DEFAULT NULL COMMENT '战略表主键',
  `backlog_module_id` varchar(32) DEFAULT NULL COMMENT '模块表主键',
  `backlog_type` varchar(1) DEFAULT NULL COMMENT '类型("0":story "1":bug)',
  `backlog_status_id` varchar(32) DEFAULT NULL COMMENT '状态表主键',
  `backlog_title` varchar(200) DEFAULT NULL COMMENT '标题',
  `backlog_deadline` varchar(20) DEFAULT NULL COMMENT '截止时间',
  `backlog_create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`backlog_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_epic` */

DROP TABLE IF EXISTS `t_epic`;

CREATE TABLE `t_epic` (
  `epic_id` varchar(32) NOT NULL COMMENT '公司战略表主键',
  `epic_title` varchar(100) DEFAULT NULL COMMENT '标题',
  `epic_create_user` varchar(32) DEFAULT NULL COMMENT '创建用户',
  `epic_create_time` varchar(20) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`epic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_module` */

DROP TABLE IF EXISTS `t_module`;

CREATE TABLE `t_module` (
  `module_id` varchar(32) NOT NULL COMMENT '模块表主键',
  `module_name` varchar(100) DEFAULT NULL COMMENT '模块名',
  `module_desc` varchar(200) DEFAULT NULL COMMENT '模块描述',
  `module_create_time` varchar(20) DEFAULT NULL COMMENT '模块创建时间',
  PRIMARY KEY (`module_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_sprint` */

DROP TABLE IF EXISTS `t_sprint`;

CREATE TABLE `t_sprint` (
  `sprint_id` varchar(32) NOT NULL COMMENT '迭代表主键',
  `sprint_name` varchar(100) DEFAULT NULL COMMENT '迭代名称',
  `sprint_desc` varchar(200) DEFAULT NULL COMMENT '迭代描述',
  `sprint_begin_date` varchar(20) DEFAULT NULL COMMENT '开始日期',
  `sprint_end_date` varchar(20) DEFAULT NULL COMMENT '结束日期',
  `sprint_create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `sprint_update_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`sprint_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_status` */

DROP TABLE IF EXISTS `t_status`;

CREATE TABLE `t_status` (
  `status_id` varchar(32) NOT NULL COMMENT '状态表主键',
  `status_epic_id` varchar(32) DEFAULT NULL COMMENT '战略表主键',
  `status_name` varchar(100) DEFAULT NULL COMMENT '状态名',
  `status_desc` varchar(200) DEFAULT NULL COMMENT '状态描述',
  `status_type` varchar(1) DEFAULT NULL COMMENT '"0":开始态 "1":进行态 "2":结束态',
  `status_can_delete` varchar(1) DEFAULT '0' COMMENT '"0":可删除 "1":不可删除',
  `status_order` int(5) DEFAULT NULL COMMENT '状态排序',
  `status_create_time` varchar(20) DEFAULT NULL COMMENT '状态创建时间',
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `user_id` varchar(32) NOT NULL COMMENT '用户表主键',
  `user_name` varchar(200) DEFAULT NULL COMMENT '用户名',
  `user_phone` varchar(50) DEFAULT NULL COMMENT '手机号',
  `user_mail` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `user_desc` varchar(200) DEFAULT NULL COMMENT '描述',
  `user_status` varchar(1) DEFAULT NULL COMMENT '用户状态 "0":启用 "1":停用',
  `user_create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `user_last_login_time` varchar(20) DEFAULT NULL COMMENT '用户最后一次登录时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
