/*
SQLyog Ultimate v12.5.0 (64 bit)
MySQL - 5.0.87-community-nt : Database - supbank
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`supbank` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `supbank`;

/*Table structure for table `td_block` */

DROP TABLE IF EXISTS `td_block`;

CREATE TABLE `td_block` (
  `hash` varchar(255) NOT NULL COMMENT '，主键，唯一标识本区块的哈希值',
  `height` int(11) default NULL COMMENT '区块高度',
  `prehash` varchar(255) default NULL COMMENT '上一区块的哈希值',
  `merkleroothash` varchar(255) default NULL COMMENT '本区块的MerkleRoot',
  `transactionnumber` int(11) default NULL COMMENT '区块储存的交易数量',
  `nonce` int(11) default NULL COMMENT '随机数',
  `blockreward` double default NULL COMMENT '区块奖励',
  `age` int(11) default NULL COMMENT '从出块到现在的存在时间',
  `miner` varchar(255) default NULL COMMENT '来自矿工的名字',
  `size` double default NULL COMMENT '区块大小',
  `timestamp` timestamp NULL default NULL,
  `islegal` int(11) default NULL COMMENT '区块是否合法',
  `flag` int(11) default '1' COMMENT '状态 0：不可用 1：可用',
  PRIMARY KEY  (`hash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `td_block` */

insert  into `td_block`(`hash`,`height`,`prehash`,`merkleroothash`,`transactionnumber`,`nonce`,`blockreward`,`age`,`miner`,`size`,`timestamp`,`islegal`,`flag`) values 
('255',0,NULL,NULL,5,NULL,NULL,9,'KONG',220,NULL,1,1),
('569gjk',1,'255',NULL,8,NULL,NULL,7,'Wei',123,NULL,1,1),
('852dcv',2,'569gjk',NULL,2,NULL,NULL,3,'jia',452,NULL,1,1);

/*Table structure for table `td_transaction` */

DROP TABLE IF EXISTS `td_transaction`;

CREATE TABLE `td_transaction` (
  `transactionid` varchar(255) NOT NULL COMMENT '主键',
  `input` varchar(255) default NULL COMMENT '花钱方',
  `output` varchar(255) default NULL COMMENT '收钱方',
  `sum` double default NULL COMMENT '交易金额',
  `signature` varchar(255) default NULL COMMENT '签名',
  `blockid` int(11) default NULL COMMENT '所在区块id',
  `timestamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `status` int(11) default NULL COMMENT '1:未验证  2:已验证',
  `flag` int(11) default '1' COMMENT '状态 0：不可用 1：可用',
  PRIMARY KEY  (`transactionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `td_transaction` */

insert  into `td_transaction`(`transactionid`,`input`,`output`,`sum`,`signature`,`blockid`,`timestamp`,`status`,`flag`) values 
('000','asdf562dsaf','55612asdf2acx',200,NULL,NULL,'2019-01-23 19:42:38',1,1),
('111','dfsfsf','asdf562dsaf',100,NULL,NULL,'2019-01-21 19:45:21',1,1),
('1904172041440001','inputaddress,,,,','outputaddress,,,',20,NULL,NULL,'0000-00-00 00:00:00',1,NULL),
('1904172042280001','inputaddress,,,,','outputaddress,,,',20,NULL,NULL,'0000-00-00 00:00:00',1,1),
('1904172049210002','inputaddress,,,,','outputaddress,,,',20,NULL,NULL,'0000-00-00 00:00:00',1,1),
('1904172050080003','inputaddress,,,,','outputaddress,,,',20,NULL,NULL,'0000-00-00 00:00:00',1,1),
('1904172054150001','inputaddress,,,,','outputaddress,,,',20,NULL,NULL,'2019-04-17 20:54:15',1,1),
('1904172057450001','inputaddress,,,,','outputaddress,,,',20,NULL,NULL,'2019-04-17 20:57:45',1,1),
('1904232106460001','2187e984a6c38cc35830e139e9ac2ba29ab32476','56aa8b2fd3c2109f598ae37ccc3dd19173d60497',50,'L4EpLC5QqkEAdubIe86ERpaPoEiRlWRI6hSnj1tW4a6NqBOLvi6UkJv4vf72HaYB9NwOt4V0Xp0v\nBGp4ZEJBvu8Fixcm4mnT0p//azM3xGFHFa+aKQnFQi5ndcWN+0VlFjfaFj0OPYxuPOt/MRqDC/8R\nik+e2r/dHgvhIhHrnvU=',NULL,'2019-04-23 21:06:46',1,1),
('1904232107560001','2187e984a6c38cc35830e139e9ac2ba29ab32476','56aa8b2fd3c2109f598ae37ccc3dd19173d60497',50,'X4xu+wcg2gGW9Lqmd6Kw8wkPhM+eJ/grwEfDjmh27383RJtQRmiGDXFh2KKe9meMdlFfvMAUiAn8\nU74PCbCJtUX41dYuwWaVjr5JfJROIbh8z/46OTLRe52hGifi46yW848vWsZKbOVjW6GCBicm216Y\njbaKM6N8+DJD/q5kRek=',NULL,'2019-04-23 21:07:56',1,1);

/*Table structure for table `td_user` */

DROP TABLE IF EXISTS `td_user`;

CREATE TABLE `td_user` (
  `userid` varchar(255) NOT NULL COMMENT '主键，唯一标识',
  `username` varchar(255) default NULL COMMENT '用户名',
  `password` varchar(255) default NULL COMMENT '密码',
  `walletid` varchar(255) default NULL COMMENT '钱包id',
  `firstname` varchar(255) default NULL,
  `lastname` varchar(255) default NULL,
  `sex` varchar(20) default NULL,
  `email` varchar(255) default NULL,
  `phonenumber` varchar(30) default NULL,
  `accouttype` int(11) default NULL COMMENT '0: email, 1: facebook & 2: google',
  `timestamp` timestamp NULL default NULL,
  `flag` int(11) default '1',
  PRIMARY KEY  (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `td_user` */

insert  into `td_user`(`userid`,`username`,`password`,`walletid`,`firstname`,`lastname`,`sex`,`email`,`phonenumber`,`accouttype`,`timestamp`,`flag`) values 
('1904211506550002','kwjia','5aa5ef5a12f017ea68d96800e6c0e23b','1904211506530001',NULL,NULL,NULL,'1138500436@qq.com',NULL,0,NULL,1),
('1904211845090003','J','5aa5ef5a12f017ea68d96800e6c0e23b','1904211845090004',NULL,NULL,NULL,'302589689@qq.com',NULL,0,NULL,1);

/*Table structure for table `td_wallet` */

DROP TABLE IF EXISTS `td_wallet`;

CREATE TABLE `td_wallet` (
  `walletid` varchar(255) NOT NULL COMMENT '主键',
  `address` longtext COMMENT '地址',
  `publicKey` longtext COMMENT '公钥',
  `privateKey` longtext COMMENT '私钥',
  `balance` double default NULL COMMENT '余额',
  `flag` int(11) default '1',
  PRIMARY KEY  (`walletid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `td_wallet` */

insert  into `td_wallet`(`walletid`,`address`,`publicKey`,`privateKey`,`balance`,`flag`) values 
('1904211506530001','2187e984a6c38cc35830e139e9ac2ba29ab32476','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCd0ko/baDps8o1uyd5bL0JTFmyCg/+OfkHEQBP\nakZKabfUYfgR2AQOm25+JLAAv27DRZrbucpCDoKH/YHeSE8vTT6hNZTHW+nN3M0/QKcK6xP8NZDs\n0BbTytzixBn/P/Pqy0wdG6av3xi1/i9G5v1XneoYeATUwXG0q1SnWEA1OQIDAQAB','MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ3SSj9toOmzyjW7J3lsvQlMWbIK\nD/45+QcRAE9qRkppt9Rh+BHYBA6bbn4ksAC/bsNFmtu5ykIOgof9gd5ITy9NPqE1lMdb6c3czT9A\npwrrE/w1kOzQFtPK3OLEGf8/8+rLTB0bpq/fGLX+L0bm/Ved6hh4BNTBcbSrVKdYQDU5AgMBAAEC\ngYBbU2krsyC+nA+TcC2zkj0BuHDfGfuPPbThZfEsA2+ReAOsntSSXtJaMY+K52gBxsUtsoWeSLDF\n8OeQEh6opmdLJ+79bXdxy6gWm3LZDN9J/wrdTan7MSaz2mvshy5esVR7HTU5qBmoIx4ZiFr/GiIQ\n8bIOPZKNA+l1upVzn5+sEQJBAO68OV8GrvsffvUB67oaoH3qI9FzM5j1z1PoFWlDi/YzxkPiLwsI\nX6XaPE9IRBlcbX9eGNHkyWXkFqBABvjZKhsCQQCpPBW5ToMRCuFWRibyvZ6vci37Wwmvby38NaO5\nifhFmRGW7/IGcYsip+JgwcCyPcWPoqc40XrbTpXH02BRcpM7AkEAvlOhCXkyl1IMzRey7rO7r6ay\n15CYWiHeIG6uYzVOqAGu1v/DsXADkR3jZvGeGPnu8dP6xZs4VNr6jtd8t3eqcQJAZq0vuPgriSGC\nyedSJiBpsKSjxUbcv3XC/qxEo4NUAFX4WA1IWCR3eBVHwdCL6xiTTqwurMhbs8cgFNboL0JGlQJA\nKqlWUMKKpB2qC0SghLFji6ZRUrIKhs1HBSqnA3iHZUI56vTn/hxk/CENGASzvBxkn7jJeCYnTLi4\n5P6zPb6pQQ==',0,1),
('1904211845090004','56aa8b2fd3c2109f598ae37ccc3dd19173d60497','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCif/rqUFvYUxqhdw8mxOSrdmGeGC+YI8QgDcRi\nSRF0EwTSEngZEp33syPSOhW5oNhcIBAup7Qb9VSz4eLgZdzNdP8P3q69H7+jSdJjN/M7P1vgYpI9\n6nti+ltJT1V2MXJiHejqQtP8uH7WBS2JGUDDdM5cr+QfxsBPB4Cr4zvJnwIDAQAB','MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKJ/+upQW9hTGqF3DybE5Kt2YZ4Y\nL5gjxCANxGJJEXQTBNISeBkSnfezI9I6Fbmg2FwgEC6ntBv1VLPh4uBl3M10/w/err0fv6NJ0mM3\n8zs/W+Bikj3qe2L6W0lPVXYxcmId6OpC0/y4ftYFLYkZQMN0zlyv5B/GwE8HgKvjO8mfAgMBAAEC\ngYEAmQGANNhtYob9cEM2lP6XgYr5525Ggi5LIRNVcmXydSTjSRny00jgTLb0Z6IhiqOUlUnrcsKd\nqCWcF7P9d+TxvU0/zKsxBBbIr+O/V4FH51VlKlKq0i2NkciWhnCvhkOmvJQS9066l81HfcNkSHrZ\ncWt4/o5n/zKPM2zctKvi9EECQQDyNvIwunnzwGBZfD0l53GMRbSWcddn/xTcFv1vVz/k/5olWciy\nAqVJ2H/J4wRJu+686YjDsVx8LyKAzF3KtasnAkEAq7+YWT/8Lq6ipyAv7McaAq/zk55H+YR0qGV0\nrv+ggWWFd0qh6lpEXa0+eSVl3apy9SZiJ5ygsrxtT1yhkAVYyQJAHGcPDWHAjTHA2p1z+i7ipMVD\nwOSGkt5ZjtlvTJAZoPvMEpctrpoa0cb+bSkexpqwCx0DeZchtjo8vIe6c9vLvwJAb7MBI3Kg0b+U\nW3tsj+MBwKOsl+JGTzpdILQzSilIuz9KqUXpvmAMvJwS2HmBIcRcVaIsDjUosnYn5YiKLv+7OQJA\nA6mZPbZXL3WKCXnNqA48+HOvvlkACJ2fjDFbPUkrUlMhW2JvX00n9ndd3DuuWWvZI1FCxKdFyV6l\nYFqez7xV3A==',0,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
