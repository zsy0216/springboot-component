/*
 Navicat Premium Data Transfer

 Source Server         : CITIC
 Source Server Type    : MySQL
 Source Server Version : 50670
 Source Host           : rm-2jdm0u2oui38q95pf.mysql.rds.aliyuncs.com:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50670
 File Encoding         : 65001

 Date: 24/06/2021 13:50:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_menu`;
CREATE TABLE `admin_menu`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id，自动递增',
  `parent_id` int(11) NOT NULL COMMENT '父节点id',
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '路径',
  `redirect` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '重定向地址',
  `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '命名',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `sort` int(11) NOT NULL COMMENT '排序',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `hidden` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否隐藏，默认不隐藏',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10002 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of admin_menu
-- ----------------------------
INSERT INTO `admin_menu` VALUES (1, 0, '/', '/dashboard', 'Layout', '', '首页', 0, '', 0);
INSERT INTO `admin_menu` VALUES (2, 1, 'dashboard', NULL, 'dashboard/index', 'Dashboard', '首页', 1, 'dashboard', 0);
INSERT INTO `admin_menu` VALUES (3, 0, '/menu', '/menu/table', 'Layout', 'Menu', '菜单管理', 0, 'el-icon-s-help', 0);
INSERT INTO `admin_menu` VALUES (4, 3, 'table', NULL, '() => import(\'@/views/table/index\')', 'Table', '菜单列表', 1, 'table', 0);
INSERT INTO `admin_menu` VALUES (5, 3, 'tree', NULL, '() => import(\'@/views/tree/index\')', 'Tree', '树形结构', 2, 'tree', 0);
INSERT INTO `admin_menu` VALUES (6, 0, '/form', NULL, 'Layout', NULL, NULL, 0, NULL, 0);
INSERT INTO `admin_menu` VALUES (7, 6, 'index', NULL, '() => import(\'@/views/form/index\')', 'Form', '填写表单', 1, 'form', 0);
INSERT INTO `admin_menu` VALUES (8, 0, '/nested', '/nested/menu1', 'Layout', 'Nested', '级联菜单', 0, 'nested', 0);
INSERT INTO `admin_menu` VALUES (9, 8, 'menu1', NULL, '() => import(\'@/views/nested/menu1/index\')', 'Menu1', '一级菜单', 1, NULL, 0);
INSERT INTO `admin_menu` VALUES (10, 9, 'menu1-1', NULL, '() => import(\'@/views/nested/menu1/menu1-1\')', 'Menu1-1', '一级菜单-1', 2, NULL, 0);
INSERT INTO `admin_menu` VALUES (11, 9, 'menu1-2', NULL, '() => import(\'@/views/nested/menu1/menu1-2\')', 'Menu1-2', '一级菜单-2', 3, NULL, 0);
INSERT INTO `admin_menu` VALUES (12, 11, 'menu1-2-1', NULL, '() => import(\'@/views/nested/menu1/menu1-2/menu1-2-1\')', 'Menu1-2-1', '二级菜单-1', 5, NULL, 0);
INSERT INTO `admin_menu` VALUES (13, 11, 'menu1-2-2', NULL, '() => import(\'@/views/nested/menu1/menu1-2/menu1-2-2\')', 'Menu1-2-2', '二级菜单-2', 6, NULL, 0);
INSERT INTO `admin_menu` VALUES (14, 9, 'menu1-3', NULL, '() => import(\'@/views/nested/menu1/menu1-3\')', 'Menu1-3', '一级菜单-3', 4, NULL, 0);
INSERT INTO `admin_menu` VALUES (15, 8, 'menu2', NULL, '() => import(\'@/views/nested/menu2/index\')', 'Menu2', '一级菜单2', 1, NULL, 0);
INSERT INTO `admin_menu` VALUES (16, 0, 'external-link', NULL, 'Layout', NULL, NULL, 0, NULL, 0);
INSERT INTO `admin_menu` VALUES (17, 16, 'https://www.gitee.com/zsy0216', NULL, '', NULL, 'Gitee', 0, 'link', 0);
INSERT INTO `admin_menu` VALUES (9998, 0, '/404', NULL, '() => import(\'@/views/404\')', '', '', 9998, NULL, 1);
INSERT INTO `admin_menu` VALUES (9999, 0, '*', '/404', '', '', '', 9999, NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;
