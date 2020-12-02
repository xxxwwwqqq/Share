/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50701
Source Host           : localhost:3306
Source Database       : yanxiaopei

Target Server Type    : MYSQL
Target Server Version : 50701
File Encoding         : 65001

Date: 2020-12-02 16:34:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `songinfo`
-- ----------------------------
DROP TABLE IF EXISTS `songinfo`;
CREATE TABLE `songinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `songName` varchar(50) NOT NULL,
  `songArtist` varchar(30) NOT NULL,
  `collect` int(11) NOT NULL,
  `songCover` varchar(15) NOT NULL,
  `songFile` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of songinfo
-- ----------------------------
INSERT INTO `songinfo` VALUES ('1', 'Kiss The Rain', '李闰珉', '0', 's_kisstherain', 'sf_kisstherain');
INSERT INTO `songinfo` VALUES ('2', '大鱼', '周深', '1', 's_dayu', 'sf_dayu');
INSERT INTO `songinfo` VALUES ('3', 'Stay With Me', 'Punch/灿烈', '1', 's_staywithme', 'sf_staywithme');
INSERT INTO `songinfo` VALUES ('4', '彩虹', '周杰伦', '0', 's_caihong', 'sf_caihong');
INSERT INTO `songinfo` VALUES ('5', '眉间雪', '晴愔', '0', 's_meijianxue', 'sf_meijianxue');
INSERT INTO `songinfo` VALUES ('6', '美人鱼', '林俊杰', '1', 's_meirenyu', 'sf_meirenyu');
INSERT INTO `songinfo` VALUES ('7', '你曾是少年', 'S.H.E', '1', 's_ncssn', 'sf_ncssn');
INSERT INTO `songinfo` VALUES ('8', '陪你度过漫长岁月', '陈奕迅', '0', 's_pndgmcsy', 'sf_pndgmcsy');
INSERT INTO `songinfo` VALUES ('9', '平凡的一天', '毛不易', '0', 's_pfdyt', 'sf_pfdyt');
INSERT INTO `songinfo` VALUES ('10', '手掌心', '丁当', '0', 's_szx', 'sf_shsf_szx');
INSERT INTO `songinfo` VALUES ('11', '天黑黑', '孙燕姿', '0', 's_thh', 'sf_thh');
INSERT INTO `songinfo` VALUES ('12', '牵丝戏', '银临', '1', 's_qsx', 'sf_qsx');
INSERT INTO `songinfo` VALUES ('13', '全世界谁倾听你', '林宥嘉', '0', 's_qsj', 'sf_qsj');
INSERT INTO `songinfo` VALUES ('14', '小半', '陈粒', '0', 's_xb', 'sf_xb');
INSERT INTO `songinfo` VALUES ('15', '修炼爱情', '林俊杰', '1', 's_xlaq', 'sf_xlaq');
INSERT INTO `songinfo` VALUES ('16', '有何不可', '许嵩', '0', 's_yhbk', 'sf_yhbk');
INSERT INTO `songinfo` VALUES ('17', '小小', '云 泣', '0', 's_xx', 'sf_xx');
INSERT INTO `songinfo` VALUES ('18', '父亲写的散文诗', '许飞', '0', 's_sws', 'sf_sws');
INSERT INTO `songinfo` VALUES ('19', '青花瓷', '周杰伦', '0', 's_qhc', 'sf_qhc');
INSERT INTO `songinfo` VALUES ('20', 'IF YOU', 'BIG BANG', '0', 's_ifyou', 'sf_ifyou');
